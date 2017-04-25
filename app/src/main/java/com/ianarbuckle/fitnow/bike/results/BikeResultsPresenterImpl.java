package com.ianarbuckle.fitnow.bike.results;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.StringUtils;

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
  public void sendResultsToNetwork(final String desc, final float rating, final String time, final float distance, final float speed, final float pedalSpeed, final int calories, final String date) {
    String username = authenticationHelper.getUserDisplayName();
    if (!StringUtils.isStringEmptyorNull(desc)) {
      databaseHelper.sendCyclingResultsToFirebase(username, desc, rating, time, distance, speed,
          pedalSpeed, calories, date);
    } else {
      view.showErrorMessage();
    }
  }
}
