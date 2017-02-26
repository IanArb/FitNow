package com.ianarbuckle.fitnow.helper;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ianarbuckle.fitnow.helper.LocationHelper;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.PermissionsManager;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class LocationHelperImpl implements LocationHelper {
  private GoogleMap map;
  private GoogleApiClient googleApiClient;
  private LocationRequest locationRequest;
  Location lastLocation;
  private LocationListener locationListener;
  private Marker currentLocation;

  public LocationHelperImpl(LocationListener locationListener) {
    this.locationListener = locationListener;
    buildLocationRequest();
  }


  @Override
  public LocationRequest buildLocationRequest() {
    locationRequest = LocationRequest.create()
        .setInterval(1000)
        .setFastestInterval(1000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    return locationRequest;
  }

  @Override
  public void startLocationUpdates(GoogleApiClient googleApiClient) {
    if(googleApiClient != null && googleApiClient.isConnected()) {
      LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
    }
  }

  @Override
  public void stopLocationUpdates(GoogleApiClient googleApiClient) {
    if(googleApiClient != null && googleApiClient.isConnected()) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, locationListener);
    }
  }

}
