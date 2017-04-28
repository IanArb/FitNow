package com.ianarbuckle.fitnow.activities.walking.walkingtimer;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public interface WalkRecordingView {
  Activity getActivity();
  Context getContext();
  void setTimerText(String result);
  void showErrorMessage();
  void setTextSteps(String value);
  void setTextSpeed(String value);
  void setTextDistance(String value);
  void setCaloriesText(String value);
  void dismissLoading();
  void showLoading();
  void showSuccessMessage();
  void showStorageErrorMessage();
}
