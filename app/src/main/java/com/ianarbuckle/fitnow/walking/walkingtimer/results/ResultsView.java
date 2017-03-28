package com.ianarbuckle.fitnow.walking.walkingtimer.results;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface ResultsView {
  Activity getActivity();
  Context getContext();
  void showErrorMessage();
}
