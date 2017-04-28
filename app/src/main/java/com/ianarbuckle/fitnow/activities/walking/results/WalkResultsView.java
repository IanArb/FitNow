package com.ianarbuckle.fitnow.activities.walking.results;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface WalkResultsView {
  Activity getActivity();
  Context getContext();
  void showErrorMessage();
  String getDesc();
  float getRating();
}
