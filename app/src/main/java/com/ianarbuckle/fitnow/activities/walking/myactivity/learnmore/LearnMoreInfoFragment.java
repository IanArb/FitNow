package com.ianarbuckle.fitnow.activities.walking.myactivity.learnmore;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 02/05/2017.
 *
 */

public class LearnMoreInfoFragment extends BaseFragment {

  GoogleMap map;

  @BindView(R.id.tvDistance)
  TextView tvDistance;

  @BindView(R.id.tvSteps)
  TextView tvSteps;

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
    View view = inflater.inflate(R.layout.fragment_learn_more_runwalk, container, false);
    ButterKnife.bind(this, view);
    initViews();
    return view;
  }

  @Override
  protected void initPresenter() {
    //Stub method
  }

  private void initViews() {
    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    float steps = bundle.getFloat(Constants.STEPS_KEY);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    int time = bundle.getInt(Constants.SECONDS_KEY);
    float calories = bundle.getFloat(Constants.CALORIES_KEY);

    String formatDistance = StringUtils.formatDistance(distance);
    String formatSpeed = StringUtils.formatSpeed(speed);
    String formatSteps = StringUtils.formatFloat(steps);
    Seconds convertSeconds = Seconds.seconds(time);
    Period period = new Period(convertSeconds);
    String formatTime = Constants.FORMAT_HOURS_MINUTES_SECONDS_RESULT.print(period.normalizedStandard(PeriodType.time()));
    String formatCalories = StringUtils.formatFloat(calories);

    tvDistance.setText(formatDistance);
    tvSteps.setText(formatSteps);
    tvSpeed.setText(formatSpeed);
    tvTime.setText(formatTime);
    tvCalories.setText(formatCalories);
  }

  @Override
  public void onStart() {
    super.onStart();
    initMap();
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
