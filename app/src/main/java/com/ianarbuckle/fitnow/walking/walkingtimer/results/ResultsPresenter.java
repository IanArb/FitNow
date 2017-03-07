package com.ianarbuckle.fitnow.walking.walkingtimer.results;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface ResultsPresenter {
  boolean checkLocationPermission(Fragment fragment);
  void initMap(GoogleMap googleMap);
  void onRequestPermission();
}
