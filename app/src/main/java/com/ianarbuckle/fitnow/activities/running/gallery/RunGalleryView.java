package com.ianarbuckle.fitnow.activities.running.gallery;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public interface RunGalleryView {
  Activity getActivity();
  Context getContext();
  void showProgress();
  void hideProgress();
  void setAdapter(RunGalleryAdapter adapter);
  void showErrorMessage();
}
