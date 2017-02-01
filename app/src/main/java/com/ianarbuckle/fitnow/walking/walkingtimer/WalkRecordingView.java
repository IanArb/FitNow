package com.ianarbuckle.fitnow.walking.walkingtimer;

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
  void showLoading();
  void dismissLoading();
  void showErrorMessage();
  void setSuccessMessage();
}
