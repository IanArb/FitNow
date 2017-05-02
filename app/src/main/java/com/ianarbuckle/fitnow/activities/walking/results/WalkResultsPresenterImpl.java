package com.ianarbuckle.fitnow.activities.walking.results;

import android.content.Intent;
import android.os.Bundle;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class WalkResultsPresenterImpl implements WalkResultsPresenter {

  private WalkResultsView view;
  private final DatabaseHelper databaseHelper;
  private AuthenticationHelper authenticationHelper;

  public WalkResultsPresenterImpl(DatabaseHelper databaseHelper, AuthenticationHelper authenticationHelper) {
    this.databaseHelper = databaseHelper;
    this.authenticationHelper = authenticationHelper;
  }

  public void setView(WalkResultsView view) {
    this.view = view;
  }

  @Override
  public void sendResultsToNetwork() {
    String desc = view.getDesc();
    Intent intent = view.getActivity().getIntent();
    Bundle bundle = intent.getExtras();
    int time = bundle.getInt(Constants.SECONDS_KEY);
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    int steps = bundle.getInt(Constants.STEPS_KEY);
    int calories = bundle.getInt(Constants.CALORIES_KEY);
    String currentDate = DateFormat.getDateInstance().format(new Date());
    float rating = view.getRating();


    RunWalkModel runWalkModel = new RunWalkModel();
    runWalkModel.setDesc(desc);
    runWalkModel.setTime(time);
    runWalkModel.setDistance(distance);
    runWalkModel.setSpeed(speed);
    runWalkModel.setSteps(steps);
    runWalkModel.setCalories(calories);
    runWalkModel.setCurrentDate(currentDate);
    runWalkModel.setRating(rating);
    runWalkModel.setUsername(authenticationHelper.getUserDisplayName());

    if(!StringUtils.isStringEmptyorNull(desc)) {
      databaseHelper.sendRunWalkResultsToDatabase(runWalkModel, Constants.RESULTS_WALKING_REFERENCE);
    } else {
      view.showErrorMessage();
    }

  }

}
