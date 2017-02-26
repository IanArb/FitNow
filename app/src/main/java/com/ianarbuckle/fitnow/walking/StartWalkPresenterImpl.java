package com.ianarbuckle.fitnow.walking;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.helper.GoogleApiHelper;
import com.ianarbuckle.fitnow.helper.GoogleApiHelperImpl;
import com.ianarbuckle.fitnow.helper.LocationHelper;
import com.ianarbuckle.fitnow.helper.LocationHelperImpl;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.PermissionsManager;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public class StartWalkPresenterImpl implements StartWalkPresenter {

  StartWalkView view;
  private GoogleApiHelper googleApiHelper;

  public StartWalkPresenterImpl(StartWalkView view) {
    this.view = view;
    googleApiHelper = new GoogleApiHelperImpl(view.getContext());
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission(Fragment fragment) {
    return googleApiHelper.checkLocationPermission(fragment);
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    googleApiHelper.initMap(googleMap);
  }

  @Override
  public void onRequestPermission() {
    googleApiHelper.onRequestPermission();
  }

}
