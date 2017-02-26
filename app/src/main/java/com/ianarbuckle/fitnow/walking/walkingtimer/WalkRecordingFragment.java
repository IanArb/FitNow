package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.google.android.gms.fitness.service.FitnessSensorService;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.ActivityRecongisedService;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.utils.PermissionsManager;
import com.ianarbuckle.fitnow.utils.PopupFragment;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageView;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkRecordingFragment extends BaseFragment implements WalkRecordingView, FirebaseStorageView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  @BindView(R.id.tvTimer)
  TextView tvTimer;

  @BindView(R.id.fabPause)
  FloatingActionButton fabPause;

  @BindView(R.id.tvSpeed)
  TextView tvSpeed;

  @BindView(R.id.tvSteps)
  TextView tvSteps;

  @BindView(R.id.tvDistance)
  TextView tvDistance;

  @BindView(R.id.tvCalories)
  TextView tvCalories;

  private WalkRecordingPresenterImpl presenter;

  private static final int REQUEST_OAUTH = 2;
  private static final String AUTH_PENDING = "auth_state_pending";
  private boolean authInProgress = false;

  GoogleApiClient googleApiClient;

  OnDataPointListener listener;

  public static Fragment newInstance() {
    return new WalkRecordingFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timer, container, false);
  }

  @TargetApi(Build.VERSION_CODES.M)
  @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
    initGoogleClient();
    if (googleApiClient != null) {
      googleApiClient.connect();
    }
  }

  private void initGoogleClient() {
    googleApiClient = new GoogleApiClient.Builder(this.getActivity())
        .enableAutoManage(getActivity(), 0, this)
        .addApi(ActivityRecognition.API)
        .addApi(Fitness.SENSORS_API)
        .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
        .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
        .addConnectionCallbacks(this)
        .build();

    googleApiClient.connect();
  }

  @Override
  protected void initPresenter() {
    presenter = new WalkRecordingPresenterImpl(this, this);
  }

  @Override
  public void onResume() {
    super.onResume();
    startTimer();
//    if (googleApiClient != null) {
//      googleApiClient.connect();
//    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    getUserCurrentActivity();
//    requestDataSources();
      getCallback();
  }

  private void getUserCurrentActivity() {
    Intent intent = new Intent(getActivity(), ActivityRecongisedService.class);
    PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 3000, pendingIntent);
  }

  private void requestDataSources() {
    DataSourcesRequest dataSourcesRequest = new DataSourcesRequest.Builder()
        .setDataTypes(DataType.TYPE_STEP_COUNT_DELTA, DataType.TYPE_SPEED, DataType.TYPE_DISTANCE_DELTA, DataType.TYPE_STEP_COUNT_DELTA)
        .setDataSourceTypes(DataSource.TYPE_DERIVED, DataSource.TYPE_RAW)
        .build();

    ResultCallback<DataSourcesResult> dataSourcesResultResultCallback =
        new ResultCallback<DataSourcesResult>() {
          @Override
          public void onResult(@NonNull DataSourcesResult dataSourcesResult) {
            for (DataSource dataSources : dataSourcesResult.getDataSources()) {
              DataType typeStepCountDelta = DataType.TYPE_STEP_COUNT_DELTA;
              DataType typeSpeed = DataType.TYPE_SPEED;
              DataType typeDistanceDelta = DataType.TYPE_DISTANCE_DELTA;
              DataType typeHeartRate = DataType.TYPE_HEART_RATE_BPM;

              DataType dataType = dataSources.getDataType();
              if (dataType.equals(typeStepCountDelta)
                  || dataType.equals(typeSpeed)
                  || dataType.equals(typeDistanceDelta)
                  || dataType.equals(typeHeartRate)) {
//                registerDataListener(dataSources, dataType);
              }
            }
          }
        };

//    getCallback(dataSourcesRequest, dataSourcesResultResultCallback);
  }

  private void getCallback() {
    Fitness.SensorsApi.findDataSources(googleApiClient, new DataSourcesRequest.Builder()
            .setDataTypes(DataType.TYPE_SPEED, DataType.TYPE_DISTANCE_DELTA, DataType.TYPE_STEP_COUNT_DELTA)
            .setDataSourceTypes(DataSource.TYPE_DERIVED, DataSource.TYPE_RAW)
            .build())

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
                Fitness.SensorsApi.add(googleApiClient, new SensorRequest.Builder()
                        .setDataSource(dataSources)
                        .setDataType(dataType)
                        .setSamplingRate(3, TimeUnit.SECONDS)
                        .setFastestRate(1, TimeUnit.SECONDS)
                        .build(),
                    new OnDataPointListener() {
                      @Override
                      public void onDataPoint(DataPoint dataPoint) {
                        for (final Field field : dataPoint.getDataType().getFields()) {
                          final Value value = dataPoint.getValue(field);
                          getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                              if (field.equals(Field.FIELD_SPEED)) {
                                String speedFormat = StringUtils.formatSpeed(value.asFloat());
                                tvSpeed.setText(speedFormat);
//                                Toast.makeText(getContext(), "Speed: " + speedFormat, Toast.LENGTH_SHORT).show();
                              }

                              if (field.equals(Field.FIELD_STEPS)) {
                                tvSteps.setText(value.asString());
//                                Toast.makeText(getContext(), "Steps: " + value.asInt(), Toast.LENGTH_SHORT).show();
                              }

                              if (field.equals(Field.FIELD_DISTANCE)) {
                                String distanceFormat = StringUtils.formatDistance(value.asFloat());
                                tvDistance.setText(distanceFormat);
//                                Toast.makeText(getContext(), "Distance: " + distanceFormat, Toast.LENGTH_SHORT).show();
                              }
                            }
                          });
                        }
                      }
                    })
                    .setResultCallback(new ResultCallback<Status>() {
                      @Override
                      public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                          Toast.makeText(getContext(), "SensorApi successfully added for " + dataType.getName(), Toast.LENGTH_SHORT).show();
                        } else {
                          Toast.makeText(getContext(), "Listener not registered for " + dataType.getName(), Toast.LENGTH_SHORT).show();
                        }
                      }
                    });
              }
            }
          }
        });
  }

  private void registerDataListener(final DataSource dataSource, final DataType dataType) {
    listener = new OnDataPointListener() {
      @Override
      public void onDataPoint(final DataPoint dataPoint) {
        for (final Field field : dataPoint.getDataType().getFields()) {
          final Value value = dataPoint.getValue(field);
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              if (field.equals(Field.FIELD_SPEED)) {
                String speedFormat = StringUtils.formatSpeed(value.asFloat());
                tvSpeed.setText(speedFormat);
                Toast.makeText(getContext(), "Speed: " + speedFormat, Toast.LENGTH_SHORT).show();
              }

              if (field.equals(Field.FIELD_STEPS)) {
                tvSteps.setText(value.asInt());
                Toast.makeText(getContext(), "Steps: " + value.asInt(), Toast.LENGTH_SHORT).show();
              }

              if (field.equals(Field.FIELD_DISTANCE)) {
                String distanceFormat = StringUtils.formatDistance(value.asFloat());
                tvDistance.setText(distanceFormat);
                Toast.makeText(getContext(), "Distance: " + distanceFormat, Toast.LENGTH_SHORT).show();
              }
            }
          });
        }
      }
    };
    addSensorRequest(dataSource, dataType);
  }

  private void addSensorRequest(DataSource dataSource, DataType dataType) {
    SensorRequest request = new SensorRequest.Builder()
        .setDataSource(dataSource)
        .setDataType(dataType)
        .setSamplingRate(1, TimeUnit.SECONDS)
        .setFastestRate(1, TimeUnit.SECONDS)
        .setAccuracyMode(SensorRequest.ACCURACY_MODE_DEFAULT)
        .build();

    Fitness.SensorsApi.add(googleApiClient, new SensorRequest.Builder()
            .setDataSource(dataSource)
            .setDataType(dataType)
            .setSamplingRate(5, TimeUnit.SECONDS)
            .setFastestRate(1, TimeUnit.SECONDS)
            .setAccuracyMode(SensorRequest.ACCURACY_MODE_HIGH)
            .build(),
        new OnDataPointListener() {
          @Override
          public void onDataPoint(DataPoint dataPoint) {
            for (final Field field : dataPoint.getDataType().getFields()) {
              final Value value = dataPoint.getValue(field);
              getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  if (field.equals(Field.FIELD_SPEED)) {
                    String speedFormat = StringUtils.formatSpeed(value.asFloat());
                    tvSpeed.setText(speedFormat);
                    Toast.makeText(getContext(), "Speed: " + speedFormat, Toast.LENGTH_SHORT).show();
                  }

                  if (field.equals(Field.FIELD_STEPS)) {
                    tvSteps.setText(value.asInt());
                    Toast.makeText(getContext(), "Steps: " + value.asInt(), Toast.LENGTH_SHORT).show();
                  }

                  if (field.equals(Field.FIELD_DISTANCE)) {
                    String distanceFormat = StringUtils.formatDistance(value.asFloat());
                    tvDistance.setText(distanceFormat);
                    Toast.makeText(getContext(), "Distance: " + distanceFormat, Toast.LENGTH_SHORT).show();
                  }
                }
              });
            }
          }
        })
        .setResultCallback(new ResultCallback<Status>() {
          @Override
          public void onResult(@NonNull Status status) {
            if (status.isSuccess()) {
              Toast.makeText(getContext(), "SensorApi successfully added ", Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(getContext(), "Listener not registered", Toast.LENGTH_SHORT).show();
            }
          }
        });
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    if (!authInProgress) {
      try {
        authInProgress = true;
        connectionResult.startResolutionForResult(getActivity(), REQUEST_OAUTH);
      } catch (IntentSender.SendIntentException ex) {
        Toast.makeText(getContext(), R.string.error_google_play_services, Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(getContext(), "Auth in progress", Toast.LENGTH_SHORT).show();
    }
  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        presenter.initMap(googleMap);
      }
    });
  }

  @NonNull
  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
    }
    return supportMapFragment;
  }

  private void startTimer() {
    presenter.startTimer();
  }

  @Override
  public void setTimerText(String result) {
    tvTimer.setText(result);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    presenter.onActivityResult(requestCode, resultCode);
    if (requestCode == REQUEST_OAUTH) {
      authInProgress = false;
      if (resultCode == RESULT_OK) {
        if (!googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
          googleApiClient.connect();
        }
      }
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case Constants.PERMISSION_REQUEST_ACCESS_LOCATION: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.onRequestPermission();
        }
      }
      break;
      case Constants.PERMISSION_REQUEST_CAMERA: {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
        }
      }
    }
  }

  @OnClick(R.id.fabPause)
  public void onPauseClick() {
    timerSwitch();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @OnClick(R.id.fabCamera)
  public void onCameraClick() {
    checkCameraPermissions();
    presenter.pauseTimer();
  }

  private void checkCameraPermissions() {
    String accessCamera = android.Manifest.permission.CAMERA;
    String[] permissions = {accessCamera};
    if (PermissionsManager.checkPermission(getContext(), accessCamera)
        && shouldShowRequestPermissionRationale(accessCamera)) {
      PermissionsManager.requestPermissions(this, Constants.PERMISSION_REQUEST_CAMERA, permissions);
    } else if (PermissionsManager.isCameraPermissionGranted(getContext())) {
      startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
    }
  }

  private void timerSwitch() {
    if (presenter.isRunning()) {
      fabPause.setImageResource(R.drawable.ic_play_arrow);
      presenter.pauseTimer();
    } else {
      fabPause.setImageResource(R.drawable.ic_pause);
      presenter.resumeTimer();
    }
  }

  @OnClick(R.id.fabStop)
  public void onStopClick() {
//    showPopupDialog();
  }

  private void showPopupDialog() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = PopupFragment.newInstance(R.string.message_title_finish, R.string.message_subTitle_finish);
    dialogFragment.show(fragmentTransaction, Constants.TAG_STOP_FRAGMENT);
  }

  @NonNull
  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.TAG_STOP_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.stopTimer();
    googleApiClient.disconnect();
  }

  @Override
  public void onPause() {
    super.onPause();
    googleApiClient.disconnect();
  }

  @Override
  public void showLoading() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage(getString(R.string.message_uploading));
    }
    progressDialog.show();
  }

  @Override
  public void dismissLoading() {
    hideProgressDialog();
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_camera_error);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @Override
  public void setSuccessMessage() {
    Toast.makeText(getContext(), R.string.message_upload_success, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showStorageErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_upload_failure);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  private void disconnectActivityService(Intent intent) {
    PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(googleApiClient, pendingIntent);
  }

  @Override
  public void onStop() {
    super.onStop();
    googleApiClient.stopAutoManage(getActivity());
    disconnectClientSensor();
  }

  private void disconnectClientSensor() {
    Fitness.SensorsApi.remove(googleApiClient, listener)
        .setResultCallback(new ResultCallback<Status>() {
          @Override
          public void onResult(@NonNull Status status) {
            if (status.isSuccess()) {
              googleApiClient.disconnect();
            }
          }
        });
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(AUTH_PENDING, authInProgress);
  }
}