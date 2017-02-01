package com.ianarbuckle.fitnow.utils.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

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
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.PermissionsChecker;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class LocationHelperImpl implements LocationHelper, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

  private Activity activity;
  private Context context;
  private ContextCompat contextCompat;
  private Fragment fragment;
  private GoogleMap map;
  private GoogleApiClient googleApiClient;
  LocationRequest locationRequest;
  Location lastLocation;
  private Marker currentLocation;

  public LocationHelperImpl(Activity activity, Context context) {
    this.activity = activity;
    this.context = context;
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    map = googleMap;

    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    if (isLocationPermissionGranted()) {
      buildGoogleApiClient();
      map.setMyLocationEnabled(true);
    } else {
      buildGoogleApiClient();
      map.setMyLocationEnabled(true);
    }
  }

  protected synchronized void buildGoogleApiClient() {
    googleApiClient = new GoogleApiClient.Builder(context.getApplicationContext())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

    googleApiClient.connect();
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    locationRequest = new LocationRequest();
    locationRequest.setInterval(1000);
    locationRequest.setFastestInterval(1000);
    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    if (isLocationPermissionGranted()) {
      LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }
  }

  private boolean isLocationPermissionGranted() {
    return PermissionsChecker.isDeviceLocationGranted(context.getApplicationContext());
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission() {
    String accessFineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    if (ContextCompat.checkSelfPermission(context, accessFineLocation) != PackageManager.PERMISSION_GRANTED) {
      activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_ACCESS_LOCATION);
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void onConnectionSuspended(int iterator) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override
  public void onLocationChanged(Location location) {
    lastLocation = location;
    if (currentLocation != null) {
      currentLocation.remove();
    }

    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng);
    currentLocation = map.addMarker(markerOptions);

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    map.animateCamera(CameraUpdateFactory.zoomTo(16));

    if (googleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
  }

  @Override
  public void onRequestPermission() {
    if (googleApiClient == null) {
      buildGoogleApiClient();
    }
    if (isLocationPermissionGranted()) {
      map.setMyLocationEnabled(true);
    }
  }
}
