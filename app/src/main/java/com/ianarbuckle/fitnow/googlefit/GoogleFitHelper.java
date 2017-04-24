package com.ianarbuckle.fitnow.googlefit;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class GoogleFitHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

  private GoogleApiClient googleApiClient;
  private boolean authInProgress = false;
  private Context context;
  private Activity activity;
  private Bundle bundle;
  private GoogleFitHelperView view;

  public GoogleFitHelper(Activity activity, Context context, GoogleFitHelperView googleFitHelperView) {
    this.activity = activity;
    this.context = context;
    this.view = googleFitHelperView;
    bundle = new Bundle();
  }

  public void requestOAuth(int requestCode, int resultCode) {
    if (requestCode == Constants.REQUEST_OAUTH) {
      authInProgress = false;
      if (resultCode == RESULT_OK && !googleApiClient.isConnecting() && !googleApiClient.isConnected()) {
        googleApiClient.connect();
      }
    }
  }

  public void initGoogleClient() {
    googleApiClient = new GoogleApiClient.Builder(context)
        .addApi(Fitness.SENSORS_API)
        .addApi(Fitness.RECORDING_API)
        .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
        .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();

    googleApiClient.connect();
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    getSensorCallback();
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
            .setSamplingRate(1, TimeUnit.SECONDS)
            .build(),
        new OnDataPointListener() {
          @Override
          public void onDataPoint(DataPoint dataPoint) {
            for (final Field field : dataPoint.getDataType().getFields()) {
              final Value value = dataPoint.getValue(field);
              activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  switch (field.getName()) {
                    case Constants.SPEED_TYPE:
                      String speedFormat = StringUtils.formatSpeed(value.asFloat());
                      view.setTextSpeed(speedFormat);
                      bundle.putString(Constants.SPEED_KEY, speedFormat);
                      break;
                    case Constants.DISTANCE_TYPE:
                      String formatDistance = StringUtils.formatDistance(value.asFloat());
                      view.setTextDistance(formatDistance);
                      bundle.putString(Constants.DISTANCE_KEY, formatDistance);
                      break;
                    case Constants.STEPS_TYPE:
                      view.setTextSteps(String.valueOf(value));
                      bundle.putString(Constants.STEPS_KEY, value.toString());
                      break;
                    case Constants.CALORIES_TYPE:
                      view.setCaloriesText(String.valueOf(value));
                      bundle.putString(Constants.CALORIES_KEY, value.toString());
                  }
                }
              });
            }
          }
        });
  }

  public void disconnectGoogleClient() {
    if (googleApiClient != null && googleApiClient.isConnected()) {
      googleApiClient.disconnect();
    }
  }

  public Bundle setBundle() {
    return bundle;
  }

  @Override
  public void onConnectionSuspended(int interval) {
    //Stub method
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    if (!authInProgress) {
      try {
        authInProgress = true;
        connectionResult.startResolutionForResult(activity, Constants.REQUEST_OAUTH);
      } catch (IntentSender.SendIntentException ex) {
        Toast.makeText(activity, R.string.error_google_play_services, Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(activity, "Auth in progress", Toast.LENGTH_SHORT).show();
    }
  }

  public void onSaveInstanceState(Bundle outState) {
    outState.putBoolean(Constants.AUTH_PENDING, authInProgress);
  }

}
