package com.ianarbuckle.fitnow.activities.running.results;

import android.app.Activity;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface RunResultsView {
  Activity getActivity();
  void showErrorMessage();
  String getDesc();
  float getRating();
}
