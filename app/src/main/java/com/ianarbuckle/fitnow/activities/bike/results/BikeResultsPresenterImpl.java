package com.ianarbuckle.fitnow.activities.bike.results;

import android.content.Intent;
import android.os.Bundle;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeResultsPresenterImpl implements BikeResultsPresenter {

  private AuthenticationHelper authenticationHelper;
  private DatabaseHelper databaseHelper;

  private BikeResultsView view;

  public BikeResultsPresenterImpl(AuthenticationHelper authenticationHelper, DatabaseHelper databaseHelper) {
    this.databaseHelper = databaseHelper;
    this.authenticationHelper = authenticationHelper;
  }

  @Override
  public void setView(BikeResultsView view) {
    this.view = view;
  }

  @Override
  public void sendResultsToNetwork() {
    String desc = view.getDescText();

    Intent intent = view.getActivity().getIntent();
    Bundle bundle = intent.getExtras();

    String time = bundle.getString(Constants.TIME_KEY);
    float distance = bundle.getFloat(Constants.DISTANCE_KEY);
    float speed = bundle.getFloat(Constants.SPEED_KEY);
    float pedalSpeed = bundle.getFloat(Constants.PEDAL_KEY);
    int calories = bundle.getInt(Constants.CALORIES_KEY);
    String currentDate = DateFormat.getDateInstance().format(new Date());
    float rating = view.getRating();

    BikeModel bikeModel = new BikeModel();
    bikeModel.setUsername(authenticationHelper.getUserDisplayName());
    bikeModel.setRating(rating);
    bikeModel.setDate(currentDate);
    bikeModel.setTime(time);
    bikeModel.setCalories(calories);
    bikeModel.setDesc(desc);
    bikeModel.setSpeed(speed);
    bikeModel.setPedalSpeed(pedalSpeed);
    bikeModel.setDistance(distance);

    if (!StringUtils.isStringEmptyorNull(bikeModel.getDesc())) {
      databaseHelper.sendBikeResultsToDatabase(bikeModel);
    } else {
      view.showErrorMessage();
    }
  }
}
