package com.ianarbuckle.fitnow.walking.walkingtimer.gallery;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public interface GalleryView {
  Activity getActivity();
  Context getContext();
  void showProgress();
  void hideProgress();
  void setAdapter(GalleryAdapter adapter);
  void showErrorMessage();
}
