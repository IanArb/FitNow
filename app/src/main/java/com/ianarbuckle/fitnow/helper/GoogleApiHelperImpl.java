package com.ianarbuckle.fitnow.helper;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.PermissionsManager;

/**
 * Created by Ian Arbuckle on 25/02/2017.
 *
 */

public class GoogleApiHelperImpl implements GoogleApiHelper, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

  private Context context;

  private GoogleMap map;

  private GoogleApiClient googleApiClient;

  private LocationHelper locationHelper;

  Location lastLocation;
  private Marker currentLocation;


  public GoogleApiHelperImpl(Context context) {
    this.context = context;
    buildGoogleApiClient();
    locationHelper = new LocationHelperImpl(this);
  }

  public void connect() {
    if(googleApiClient != null) {
      googleApiClient.connect();
    }
  }

  public void disconnect() {
    if(googleApiClient != null) {
      googleApiClient.disconnect();
    }
  }

  public boolean isConnected() {
    return googleApiClient != null && googleApiClient.isConnected();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission(Fragment fragment) {
    String[] accessPermissions = {Manifest.permission.ACCESS_FINE_LOCATION};
    if(PermissionsManager.checkPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
      PermissionsManager.requestPermissions(fragment, Constants.PERMISSION_REQUEST_ACCESS_LOCATION, accessPermissions);
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    map = googleMap;

    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    if (PermissionsManager.isLocationPermissionGranted(context)) {
      buildGoogleApiClient();
      map.setMyLocationEnabled(true);
    }
  }

  private void buildGoogleApiClient() {
    googleApiClient = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .addApi(ActivityRecognition.API)
        .addApi(Fitness.SENSORS_API)
        .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
        .addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE))
        .build();

    connect();
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if(PermissionsManager.isLocationPermissionGranted(context)) {
      locationHelper.startLocationUpdates(googleApiClient);
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

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

    locationHelper.stopLocationUpdates(googleApiClient);
  }

  @Override
  public void onRequestPermission() {
    if (googleApiClient == null) {
      buildGoogleApiClient();
    }
    if (PermissionsManager.isLocationPermissionGranted(context)) {
      locationHelper.startLocationUpdates(googleApiClient);
      map.setMyLocationEnabled(true);
    }
  }
}
