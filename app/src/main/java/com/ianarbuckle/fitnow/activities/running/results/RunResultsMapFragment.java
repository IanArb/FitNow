package com.ianarbuckle.fitnow.activities.running.results;

import android.content.Intent;
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
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Ian Arbuckle on 03/05/2017.
 *
 */

public class RunResultsMapFragment extends BaseFragment {

  GoogleMap map;
  Marker marker;

  public static Fragment newInstance() {
    return new RunResultsMapFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_map, container, false);
  }

  @Override
  protected void initPresenter() {
    //Stub method
  }

  @Override
  public void onStart() {
    super.onStart();
    initMap();
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
          for(LatLng latLng : points) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            marker = map.addMarker(markerOptions);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            map.animateCamera(CameraUpdateFactory.zoomTo(16));
          }
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
