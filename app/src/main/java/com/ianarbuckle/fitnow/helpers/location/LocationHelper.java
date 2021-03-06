package com.ianarbuckle.fitnow.helpers.location;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public interface LocationHelper {
  boolean checkLocationPermission(Fragment fragment);
  void initMap(GoogleMap googleMap);
  void onRequestPermission();
  Bundle getPointsBundle();
}
