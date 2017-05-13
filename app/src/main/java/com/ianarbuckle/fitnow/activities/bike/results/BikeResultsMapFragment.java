package com.ianarbuckle.fitnow.activities.bike.results;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Ian Arbuckle on 01/05/2017.
 *
 */

public class BikeResultsMapFragment extends BaseFragment {

  GoogleMap map;

  Marker marker;

  public static Fragment newInstance() {
    return new BikeResultsMapFragment();
  }

  @Override
  protected void initPresenter() {

  }

  @Override
  public void onStart() {
    super.onStart();
    initMap();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    final ArrayList<LatLng> points = bundle.getParcelableArrayList(Constants.POINTS_KEY);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(points != null) {
          map.addPolyline(new PolylineOptions()
              .addAll(points)
              .color(Color.BLUE))
              .setGeodesic(true);
          double longitude = points.get(0).longitude;
          double latitude = points.get(0).latitude;
          LatLng latLng = new LatLng(latitude, longitude);
          map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
          MarkerOptions markerOptions = new MarkerOptions();
          markerOptions.position(latLng);
          map.addMarker(markerOptions);
        }

      }
    });
  }

  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
    }
    return supportMapFragment;
  }

}
