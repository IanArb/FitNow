package com.ianarbuckle.fitnow.activities.running.runningtimer;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public interface RunRecordingView {
  Activity getActivity();
  Context getContext();
  void setTimerText(String result);
  void setTextSteps(String value);
  void setTextSpeed(String value);
  void setTextDistance(String value);
  void setCaloriesText(String value);
  void showErrorMessage();
  void dismissLoading();
  void showLoading();
  void showSuccessMessage();
  void showStorageErrorMessage();
}
