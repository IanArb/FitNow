package com.ianarbuckle.fitnow.activities.bike.myactivity.learnmore;

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
import android.widget.TextView;

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
import com.ianarbuckle.fitnow.models.LatLngModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 02/05/2017.
 *
 */

public class LearnMoreInfoFragment extends BaseFragment {

  GoogleMap map;
  Marker marker;

  @BindView(R.id.tvDistance)
  TextView tvDistance;

  @BindView(R.id.tvPedalSpeed)
  TextView tvPedalSpeed;

  @BindView(R.id.tvSpeed)
  TextView tvSpeed;

  @BindView(R.id.tvTime)
  TextView tvTime;

  @BindView(R.id.tvCalories)
  TextView tvCalories;

  public static Fragment newInstance() {
    return new LearnMoreInfoFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_learn_more_bike, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  private void initViews() {
    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    float pedal = bundle.getFloat(Constants.PEDAL_KEY);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    int time = bundle.getInt(Constants.SECONDS_KEY);
    float calories = bundle.getFloat(Constants.CALORIES_KEY);

    String formatDistance = StringUtils.formatDistance(distance);
    String formatSpeed = StringUtils.formatSpeed(speed);
    String formatPedal = StringUtils.formatSpeed(pedal);
    Seconds convertSeconds = Seconds.seconds(time);
    Period period = new Period(convertSeconds);
    String formatTime = Constants.FORMAT_HOURS_MINUTES_SECONDS_RESULT.print(period.normalizedStandard(PeriodType.time()));
    String formatCalories = StringUtils.formatFloat(calories);

    tvDistance.setText(formatDistance);
    tvPedalSpeed.setText(formatPedal);
    tvSpeed.setText(formatSpeed);
    tvTime.setText(formatTime);
    tvCalories.setText(formatCalories);
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
    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    final List<LatLngModel> latLngModels = bundle.getParcelableArrayList(Constants.POINTS_KEY);

    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(latLngModels != null) {
          double longitude = latLngModels.get(0).getLongitude();
          double latitude = latLngModels.get(0).getLatitude();
          LatLng latLng = new LatLng(latitude, longitude);
          map.addPolyline(new PolylineOptions()
              .add(latLng)
              .color(Color.BLUE))
              .setGeodesic(true);

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
