package com.ianarbuckle.fitnow.walking;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.helper.LocationHelper;
import com.ianarbuckle.fitnow.helper.LocationHelperImpl;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class StartWalkPresenterImpl implements StartWalkPresenter {

  StartWalkView view;
  private LocationHelper locationHelper;

  public StartWalkPresenterImpl(StartWalkView view) {
    this.view = view;
    locationHelper = new LocationHelperImpl(view.getContext());
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @Override
  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

}
