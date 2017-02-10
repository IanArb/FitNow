package com.ianarbuckle.fitnow;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.Marker;
import com.ianarbuckle.fitnow.utils.location.LocationHelperImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by Ian Arbuckle on 10/02/2017.
 *
 */
@RunWith(RobolectricTestRunner.class)
public class LocationHelperImplTest {

  private LocationHelperImpl helper;

  @Mock
  public Context context;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    helper = new LocationHelperImpl(context);
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
