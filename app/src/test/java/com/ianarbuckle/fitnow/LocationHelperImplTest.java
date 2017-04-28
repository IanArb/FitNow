package com.ianarbuckle.fitnow;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.google.android.gms.location.LocationRequest;
import com.ianarbuckle.fitnow.utils.PermissionsManager;
import com.ianarbuckle.fitnow.helpers.location.LocationHelperImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by Ian Arbuckle on 10/02/2017.
 *
 */
@RunWith(RobolectricTestRunner.class)
public class  LocationHelperImplTest {

  private LocationHelperImpl helper;

  @Mock
  Context context;

  @Mock
  PermissionsManager permissionsManager;

  @Mock
  Fragment fragment;

  @Mock
  Bundle bundle;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    helper = new LocationHelperImpl(context);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Test
  public void testShowRequestPermission() throws Exception {
    helper.checkLocationPermission(fragment);
  }

  @Test
  public void testLocationRequest() {
    LocationRequest locationRequest = new LocationRequest();
    locationRequest.setInterval(1000);
    locationRequest.setInterval(1000);
    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
  }

  @Test
  public void testLocation() {
    Location location = new Location("gps");
    location.setAccuracy(25F);
    location.setTime(5123L);
  }

}
