package com.ianarbuckle.fitnow.walking;

import android.Manifest;
import android.content.pm.PackageManager;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.location.LocationHelper;
import com.ianarbuckle.fitnow.utils.location.LocationHelperImpl;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class StartWalkPresenterImpl implements StartWalkPresenter {

  StartWalkView view;
  private LocationHelper locationHelper;

  public StartWalkPresenterImpl(StartWalkView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getActivity(), view.getContext());
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @Override
  public boolean checkLocationPermission() {
    return locationHelper.checkLocationPermission();
  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }
}
