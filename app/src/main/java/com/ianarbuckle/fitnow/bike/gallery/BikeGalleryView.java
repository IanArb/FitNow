package com.ianarbuckle.fitnow.bike.gallery;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeGalleryView {
  Activity getActivity();
  Context getContext();
  void showProgress();
  void hideProgress();
  void setAdapter(BikeGalleryAdapter adapter);
  void showErrorMessage();
}
