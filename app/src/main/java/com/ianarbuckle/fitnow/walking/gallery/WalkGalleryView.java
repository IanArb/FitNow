package com.ianarbuckle.fitnow.walking.gallery;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public interface WalkGalleryView {
  Activity getActivity();
  Context getContext();
  void showProgress();
  void hideProgress();
  void setAdapter(WalkGalleryAdapter adapter);
  void showErrorMessage();
}
