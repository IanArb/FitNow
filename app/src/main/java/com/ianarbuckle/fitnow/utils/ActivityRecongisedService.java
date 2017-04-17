package com.ianarbuckle.fitnow.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

/**
 * Created by Ian Arbuckle on 14/02/2017.
 * @author Paul Trebilcox-Ruiz
 * reference https://code.tutsplus.com/tutorials/how-to-recognize-user-activity-with-activity-recognition--cms-25851
 */

public class ActivityRecongisedService extends IntentService {

  Handler handler;

  public ActivityRecongisedService() {
    super("ActivityRecognizedService");
    handler = new Handler();
  }

  public ActivityRecongisedService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    if(ActivityRecognitionResult.hasResult(intent)) {
      ActivityRecognitionResult recognitionResult = ActivityRecognitionResult.extractResult(intent);
      handleDetectedActivities(recognitionResult.getProbableActivities());
    }
  }

  private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
    for(DetectedActivity activity : probableActivities) {
      switch (activity.getType()) {
        case DetectedActivity.ON_BICYCLE: {
          if(activity.getConfidence() >= 75) {
            handler.post(new ToastUtil(this, "You are cycling"));
          }
          break;
        }
        case DetectedActivity.WALKING: {
          if(activity.getConfidence() >= 75) {
            handler.post(new ToastUtil(this, "You are walking"));
          }
          break;
        }
        case DetectedActivity.RUNNING: {
          if(activity.getConfidence() >=75) {
            handler.post(new ToastUtil(this, "You are running"));
          }
          break;
        }
        case DetectedActivity.ON_FOOT: {
          if(activity.getConfidence() >= 75) {
            handler.post(new ToastUtil(this, "You are on foot "));
          }
          break;
        }
        case DetectedActivity.UNKNOWN: {
          if(activity.getConfidence() >= 75) {
            handler.post(new ToastUtil(this, "We do not recognise your exercise activity"));
          }
          break;
        }
      }
    }
  }
}
