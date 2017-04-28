package com.ianarbuckle.fitnow.activities.bike.results;

import android.app.Activity;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeResultsView {
  Activity getActivity();
  void showErrorMessage();
  String getDescText();
  float getRating();
}
