package com.ianarbuckle.fitnow.helper;

import android.location.Location;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public interface LocationHelper {
  LocationRequest buildLocationRequest();
  void stopLocationUpdates(GoogleApiClient googleApiClient);
  void startLocationUpdates(GoogleApiClient googleApiClient);
}
