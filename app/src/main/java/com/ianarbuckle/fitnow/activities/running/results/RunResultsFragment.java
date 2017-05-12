package com.ianarbuckle.fitnow.activities.running.results;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.activities.running.RunningPagerActivity;
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

public class RunResultsFragment extends BaseFragment implements RunResultsView {

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

  RunResultsPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RunResultsFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_results, container, false);
  }

  @Override
  protected void initPresenter() {
    AuthenticationHelper authenticationHelper = FitNowApplication.getAppInstance().getAuthenticationHelper();
    DatabaseHelper databaseHelper = FitNowApplication.getAppInstance().getDatabaseHelper();
    presenter = new RunResultsPresenterImpl(authenticationHelper, databaseHelper);
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
    int steps = bundle.getInt(Constants.STEPS_KEY);
    String formatSteps = StringUtils.formatInt(steps);
    tvSteps.setText(formatSteps);
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
  }

  @OnClick(R.id.btnSave)
  public void onSaveClick() {
    assert tilDesc.getEditText() != null;
    String text = tilDesc.getEditText().getText().toString();
    if(!StringUtils.isStringEmptyorNull(text)) {
      presenter.sendResultsToNetwork();
      startActivity(RunningPagerActivity.newIntent(getContext()));
    } else {
      showErrorMessage();
    }
  }

  @Override
  public String getDesc() {
    EditText editText = tilDesc.getEditText();
    assert editText != null;
    return editText.getText().toString();
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
