package com.ianarbuckle.fitnow.activities.running.results;

import android.content.Intent;
import android.os.Bundle;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.models.LatLngModel;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class RunResultsPresenterImpl implements RunResultsPresenter {

  private RunResultsView view;

  private AuthenticationHelper authenticationHelper;
  private DatabaseHelper databaseHelper;


  public RunResultsPresenterImpl(AuthenticationHelper authenticationHelper, DatabaseHelper databaseHelper) {
    this.authenticationHelper = authenticationHelper;
    this.databaseHelper = databaseHelper;
  }

  public void setView(RunResultsView view) {
    this.view = view;
  }

  @Override
  public void sendResultsToNetwork() {

    Intent intent = view.getActivity().getIntent();
    Bundle bundle = intent.getExtras();

    String desc = view.getDesc();
    int time = bundle.getInt(Constants.SECONDS_KEY);
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    int steps = bundle.getInt(Constants.STEPS_KEY);
    int calories = bundle.getInt(Constants.CALORIES_KEY);
    String currentDate = DateFormat.getDateInstance().format(new Date());
    float rating = view.getRating();
    List<LatLngModel> latLngModels = bundle.getParcelableArrayList(Constants.POINTS_KEY);

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
    runWalkModel.setLatLngModels(latLngModels);

    if (!StringUtils.isStringEmptyorNull(desc)) {
      databaseHelper.sendRunWalkResultsToDatabase(runWalkModel, Constants.RESULTS_RUNNING_REFERENCE);
    } else {
      view.showErrorMessage();
    }
  }
}
