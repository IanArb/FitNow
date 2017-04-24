package com.ianarbuckle.fitnow.walking.results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;
import com.ianarbuckle.fitnow.walking.WalkPagerActivity;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class WalkResultsFragment extends BaseFragment implements WalkResultsView {

  @BindView(R.id.tilDesc)
  TextInputLayout tilDesc;

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

  @BindView(R.id.rbWalking)
  AppCompatRatingBar ratingBar;

  GoogleMap map;

  WalkResultsPresenterImpl presenter;

  public static Fragment newInstance() {
    return new WalkResultsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_results, container, false);
  }

  @Override
  protected void initPresenter() {
    DatabaseHelper databaseHelper = FitNowApplication.getAppInstance().getDatabaseHelper();
    AuthenticationHelper authenticationHelper = FitNowApplication.getAppInstance().getAuthenticationHelper();
    presenter = new WalkResultsPresenterImpl(databaseHelper, authenticationHelper);
    presenter.setView(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews();
  }

  @Override
  public void onStart() {
    super.onStart();
    initMap();
  }

  private void initViews() {
    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    String distance = bundle.getString(Constants.DISTANCE_KEY);
    if(distance == null) {
      tvDistance.setText("0");
    }
    tvDistance.setText(distance);
    String steps = bundle.getString(Constants.STEPS_KEY);
    if(steps == null) {
      tvSteps.setText("0");
    }
    tvSteps.setText(steps);
    String time = bundle.getString(Constants.TIME_KEY);
    tvTime.setText(time);
    String speed = bundle.getString(Constants.SPEED_KEY);
    if(speed == null) {
      tvSpeed.setText("0");
    }
    tvSpeed.setText(speed);
    String calories = bundle.getString(Constants.CALORIES_KEY);
    if(calories == null) {
      tvCalories.setText("0");
    }
    tvCalories.setText(calories);
  }

  @OnClick(R.id.btnSave)
  public void onSaveClick() {
    assert tilDesc.getEditText() != null;
    String descText = tilDesc.getEditText().getText().toString();
    if(!StringUtils.isStringEmptyorNull(descText)) {
      String desc = descText.trim();
      Intent intent = getActivity().getIntent();
      Bundle bundle = intent.getExtras();
      String time = bundle.getString(Constants.TIME_KEY);
      String distance = bundle.getString(Constants.DISTANCE_KEY);
      String speed = bundle.getString(Constants.SPEED_KEY);
      String steps = bundle.getString(Constants.STEPS_KEY);
      String calories = bundle.getString(Constants.CALORIES_KEY);
      String currentDate = DateFormat.getDateInstance().format(new Date());
      float rating = ratingBar.getRating();
      presenter.sendResultsToNetwork(desc, rating, time, distance, speed, steps, calories, currentDate);
      startActivity(WalkPagerActivity.newIntent(getContext()));
    } else {
      showErrorMessage();
    }
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

  @Override
  public void showErrorMessage() {
    tilDesc.setErrorEnabled(true);
    tilDesc.setError(getString(R.string.error_message_desc));
  }

}