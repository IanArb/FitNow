package com.ianarbuckle.fitnow.walking;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public interface StartWalkPresenter {
  void initMap(GoogleMap googleMap);
  boolean checkLocationPermission(Fragment fragment);
  void onRequestPermission();
}
