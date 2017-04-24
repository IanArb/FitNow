package com.ianarbuckle.fitnow.bike.biketimer;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeRecordingView {
  Activity getActivity();
  Context getContext();
  void setTimerText(String result);
  void setTextDistance(String value);
  void setCaloriesText(String value);
  void setPedallingText(String value);
  void setSpeedText(String value);
  void showErrorMessage();
  void dismissLoading();
  void showLoading();
  void showSuccessMessage();
  void showStorageErrorMessage();
}
