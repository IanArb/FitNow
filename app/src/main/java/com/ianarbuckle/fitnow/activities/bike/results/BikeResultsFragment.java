package com.ianarbuckle.fitnow.activities.bike.results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.activities.bike.BikePagerActivity;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeResultsFragment extends BaseFragment implements BikeResultsView {

  @BindView(R.id.tilDesc)
  TextInputLayout tilDesc;

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

  @BindView(R.id.rbWalking)
  AppCompatRatingBar ratingBar;

  private BikeResultsPresenterImpl presenter;

  public static Fragment newInstance() {
    return new BikeResultsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_results_bike, container, false);
  }

  @Override
  protected void initPresenter() {
    AuthenticationHelper authenticationHelper = FitNowApplication.getAppInstance().getAuthenticationHelper();
    DatabaseHelper databaseHelper = FitNowApplication.getAppInstance().getDatabaseHelper();
    presenter = new BikeResultsPresenterImpl(authenticationHelper, databaseHelper);
    presenter.setView(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initViews();
  }

  private void initViews() {
    Intent intent = getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    String formatDistance = StringUtils.formatDistance(distance);
    tvDistance.setText(formatDistance);
    float pedalSpeed = bundle.getFloat(Constants.PEDAL_KEY);
    String formatPedal = StringUtils.formatFloat(pedalSpeed);
    tvPedalSpeed.setText(formatPedal);
    int time = bundle.getInt(Constants.SECONDS_KEY);
    Seconds convertSeconds = Seconds.seconds(time);
    Period period = new Period(convertSeconds);
    String formatTime = Constants.FORMAT_HOURS_MINUTES_SECONDS_RESULT.print(period.normalizedStandard(PeriodType.time()));
    tvTime.setText(formatTime);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    String formatSpeed = StringUtils.formatSpeed(speed);
    tvSpeed.setText(formatSpeed);
    int calories = bundle.getInt(Constants.CALORIES_KEY);
    String formatCalories = StringUtils.formatInt(calories);
    tvCalories.setText(formatCalories);
    bundle.getParcelableArrayList("points");
  }

  @OnClick(R.id.btnSave)
  public void onSaveClick() {
    assert tilDesc.getEditText() != null;
    String text = tilDesc.getEditText().getText().toString();
    if(!StringUtils.isStringEmptyorNull(text)) {
      presenter.sendResultsToNetwork();
      startActivity(BikePagerActivity.newIntent(getContext()));
    } else {
      showErrorMessage();
    }

  }

  @Override
  public String getDescText() {
    assert tilDesc.getEditText() != null;
    return tilDesc.getEditText().getText().toString();
  }

  @Override
  public float getRating() {
    return ratingBar.getRating();
  }

  @Override
  public void showErrorMessage() {
    tilDesc.setErrorEnabled(true);
    tilDesc.setError(getString(R.string.error_message_desc));
  }
}
