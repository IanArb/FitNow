package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utility.PermissionsChecker;

import butterknife.OnClick;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkingTimerFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

  private static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;
  private static final int PERMISSION_REQUEST_CAMERA = 1;
  private GoogleMap map;
  private GoogleApiClient googleApiClient;
  LocationRequest locationRequest;
  Location lastLocation;
  Marker currentLocation;

  public static Fragment newiInstance() {
    return new WalkingTimerFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timer, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkPermission();
    }
    initMap();
  }

  @Override
  protected void initPresenter() {

  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          if (isLocationPermissionGranted()) {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
          }
        } else {
          buildGoogleApiClient();
          map.setMyLocationEnabled(true);
        }
      }
    });

  }

  @NonNull
  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
    }
    return supportMapFragment;
  }

  protected synchronized void buildGoogleApiClient() {
    googleApiClient = new GoogleApiClient.Builder(this.getContext())
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

    googleApiClient.connect();
  }

  @Override
  public void onConnected(Bundle bundle) {
    locationRequest = new LocationRequest();
    locationRequest.setInterval(1000);
    locationRequest.setFastestInterval(1000);
    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    if(isLocationPermissionGranted()) {
      LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }
  }

  @SuppressWarnings("PMD")
  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {

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
    markerOptions.title("Current Position");
    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
    currentLocation = map.addMarker(markerOptions);

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
    map.animateCamera(CameraUpdateFactory.zoomTo(16));

    if (googleApiClient != null) {
      LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }
  }

  private boolean isLocationPermissionGranted() {
    return PermissionsChecker.isDeviceLocationGranted(getContext());
  }

  private boolean checkPermission() {
    String accessFineLocation = Manifest.permission.ACCESS_FINE_LOCATION;
    if (PermissionsChecker.checkPermission(getContext(), accessFineLocation)) {
      if (shouldShowRequestPermissionRationale(accessFineLocation)) {
        String[] permissions = {accessFineLocation};
        PermissionsChecker.requestPermissions(permissions, PERMISSION_REQUEST_ACCESS_LOCATION);
      }
      return false;
    } else {
      return true;
    }
  }

  private void checkCameraPermission() {
    String permission = Manifest.permission.CAMERA;
    if (checkSelfPermission(getContext(), permission)
        != PackageManager.PERMISSION_GRANTED) {
      requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
    } else {
      // we have permission
      takePicture();
    }
  }

  public void takePicture() {
      Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, PERMISSION_REQUEST_CAMERA);
      }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case PERMISSION_REQUEST_ACCESS_LOCATION: {
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

          if (googleApiClient == null) {
            buildGoogleApiClient();
          }
          map.setMyLocationEnabled(true);
        }
      }
      break;
      case PERMISSION_REQUEST_CAMERA : {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          takePicture();
        }
      }
    }
  }

  @OnClick(R.id.fabCamera)
  public void onCameraClick() {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      checkCameraPermission();
    }
  }
}
