package com.ianarbuckle.fitnow.walking.walkingtimer.results;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.helper.LocationHelper;
import com.ianarbuckle.fitnow.helper.LocationHelperImpl;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class ResultsPresenterImpl implements ResultsPresenter {

  ResultsView view;

  private LocationHelper locationHelper;

  public ResultsPresenterImpl(ResultsView view) {
    this.view = view;
    this.locationHelper = new LocationHelperImpl(view.getContext());
  }

  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }


}
