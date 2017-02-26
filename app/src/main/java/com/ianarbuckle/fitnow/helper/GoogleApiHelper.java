package com.ianarbuckle.fitnow.helper;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 25/02/2017.
 *
 */

public interface GoogleApiHelper {
  boolean checkLocationPermission(Fragment fragment);
  void initMap(GoogleMap googleMap);
  void onRequestPermission();
}
