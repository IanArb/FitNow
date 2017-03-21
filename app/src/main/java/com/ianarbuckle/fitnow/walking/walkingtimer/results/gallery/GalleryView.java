package com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/03/2017.
 *
 */

public interface GalleryView {
  Context getContext();
  void showProgress();
  void hideProgress();
  void setAdapter(GalleryAdapter adapter);
}
