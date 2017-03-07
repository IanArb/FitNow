package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;
import com.google.android.gms.fitness.result.SessionReadResult;
import com.google.android.gms.fitness.result.SessionStopResult;
import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.helper.LocationHelper;
import com.ianarbuckle.fitnow.helper.LocationHelperImpl;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelperImpl;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageView;
import com.ianarbuckle.fitnow.utils.StringUtils;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsActivity;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class WalkRecordingPresenterImpl implements WalkRecordingPresenter {

  private WalkRecordingView view;
  FirebaseStorageView storageView;
  private LocationHelper locationHelper;
  private FirebaseStorageHelper firebaseStorageHelper;
  private Timer timer;
  TimerTask timerTask;
  private final Handler handler;

  private int seconds;
  String result;
  private boolean running;

  private GoogleApiClient googleApiClient;

  private boolean authInProgress = false;

  private Bundle bundle;

  public WalkRecordingPresenterImpl(WalkRecordingView view, FirebaseStorageView storageView) {
    this.view = view;
    handler = new Handler();
    running = false;
    bundle = new Bundle();
    this.storageView = storageView;
    this.locationHelper = new LocationHelperImpl(view.getContext());
    this.firebaseStorageHelper = new FirebaseStorageHelperImpl(storageView, view.getActivity());
  }

  @Override
  public void startTimer() {
    if (isRunning()) {
      return;
    }

    if (timer == null) {
      running = true;
      initTimerTask();
    }
  }

  private void initTimerTask() {
    timer = new Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          @Override
          public void run() {
            updateTextInUiThread();
          }
        });
      }
    };
    Resources resources = view.getContext().getResources();
    int delay = resources.getInteger(R.integer.timer_delay);
    int period = resources.getInteger(R.integer.timer_period);
    timer.schedule(timerTask, delay, period);
  }

  private void updateTextInUiThread() {
    view.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        updateTimerText();
      }
    });
  }

  private void updateTimerText() {
    seconds += 1;
    result = getTimeFormat(seconds);
    view.setTimerText(result);
    bundle.putString("time", result);
  }

  @Override
  public void stopTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  @Override
  public void pauseTimer() {
    if (!isRunning()) {
      return;
    }
    this.stopTimer();
    running = false;
  }

  public boolean isRunning() {
    return running;
  }

  @Override
  public void resumeTimer() {
    this.startTimer();
  }

  private String getTimeFormat(int seconds) {
    Seconds convertSeconds = Seconds.seconds(seconds);
    Period period = new Period(convertSeconds);
    return Constants.FORMAT_HOURS_MINUTES_SECONDS.print(period.normalizedStandard(PeriodType.time()));
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

  @Override
  public Intent takePicture() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(view.getActivity().getPackageManager()) != null) {
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ex) {
        view.showErrorMessage();
      }
      if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(view.getContext(),
            Constants.PROVIDER_DIR,
            photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
      }
    }
    return takePictureIntent;
  }

  private File createImageFile() throws IOException {
    return firebaseStorageHelper.createImageFile();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode) {
    firebaseStorageHelper.uploadToStorage(requestCode, resultCode);
    if (requestCode == Constants.REQUEST_OAUTH) {
      authInProgress = false;
      if (resultCode == RESULT_OK && !googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
        googleApiClient.connect();
      }
    }
  }

  public void initGoogleClient() {
    googleApiClient = new GoogleApiClient.Builder(view.getContext())
        .addApi(Fitness.SENSORS_API)
        .addApi(Fitness.RECORDING_API)
        .addApi(Fitness.SESSIONS_API)
        .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
        .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
          @Override
          public void onConnected(@Nullable Bundle bundle) {
            getSensorCallback();
          }

          @Override
          public void onConnectionSuspended(int interval) {

          }
        })
        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
          @Override
          public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            if (!authInProgress) {
              try {
                authInProgress = true;
                connectionResult.startResolutionForResult(view.getActivity(), Constants.REQUEST_OAUTH);
              } catch (IntentSender.SendIntentException ex) {
                Toast.makeText(view.getContext(), R.string.error_google_play_services, Toast.LENGTH_SHORT).show();
              }
            } else {
              Toast.makeText(view.getContext(), "Auth in progress", Toast.LENGTH_SHORT).show();
            }
          }
        })
        .build();

    googleApiClient.connect();
  }

  public void disconnectGoogleClient() {
    if (googleApiClient != null && googleApiClient.isConnected()) {
      googleApiClient.disconnect();
    }
  }

  private void getSensorCallback() {
    findDataSources()
        .setResultCallback(new ResultCallback<DataSourcesResult>() {
          @Override
          public void onResult(@NonNull DataSourcesResult dataSourcesResult) {
            for (DataSource dataSources : dataSourcesResult.getDataSources()) {
              DataType typeStepCountDelta = DataType.TYPE_STEP_COUNT_DELTA;
              DataType typeSpeed = DataType.TYPE_SPEED;
              DataType typeDistanceDelta = DataType.TYPE_DISTANCE_DELTA;

              final DataType dataType = dataSources.getDataType();
              if (dataType.equals(typeStepCountDelta)
                  || dataType.equals(typeSpeed)
                  || dataType.equals(typeDistanceDelta)) {
                addSensor(dataSources)
                    .setResultCallback(new ResultCallback<Status>() {
                      @Override
                      public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                          Log.d(Constants.LOGGER, dataType.getName());

                        } else {
                          Log.d(Constants.LOGGER, "Listener not registered");
                        }
                      }
                    });
              }
            }
          }
        });
  }

  private PendingResult<DataSourcesResult> findDataSources() {
    return Fitness.SensorsApi.findDataSources(googleApiClient, new DataSourcesRequest.Builder()
        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA, DataType.TYPE_SPEED, DataType.TYPE_DISTANCE_DELTA)
        .setDataSourceTypes(DataSource.TYPE_RAW, DataSource.TYPE_DERIVED)
        .build());
  }

  private PendingResult<Status> addSensor(DataSource dataSources) {
    return Fitness.SensorsApi.add(googleApiClient, new SensorRequest.Builder()
            .setDataSource(dataSources)
            .setDataType(dataSources.getDataType())
            .setSamplingRate(5, TimeUnit.SECONDS)
            .build(),
        new OnDataPointListener() {
          @Override
          public void onDataPoint(DataPoint dataPoint) {
            for (final Field field : dataPoint.getDataType().getFields()) {
              final Value value = dataPoint.getValue(field);
              view.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  switch (field.getName()) {
                    case Constants.SPEED_TYPE:
                      String speedFormat = StringUtils.formatSpeed(value.asFloat());
                      view.setTextSpeed(speedFormat);
                      bundle.putString("speed", speedFormat);
                      break;
                    case Constants.DISTANCE_TYPE:
                      String formatDistance = StringUtils.formatDistance(value.asFloat());
                      view.setTextDistance(formatDistance);
                      bundle.putString("distance", formatDistance);
                      break;
                    case Constants.STEPS_TYPE:
                      view.setTextSteps(String.valueOf(value));
                      bundle.putString("steps", value.toString());
                      break;
                  }
                }
              });
            }
          }
        });
  }

  @Override
  public Bundle setBundle() {
    return bundle;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putBoolean(Constants.AUTH_PENDING, authInProgress);
  }
}
