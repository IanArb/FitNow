package com.ianarbuckle.fitnow.running.results;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.StringUtils;

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
  public void sendResultsToNetwork(final String desc, final float rating, final String time, final float distance, final float speed, final int steps, final int calories, final String currentDate) {
    String username = authenticationHelper.getUserDisplayName();
    if (!StringUtils.isStringEmptyorNull(desc)) {
      databaseHelper.sendRunningResultsToFirebase(username, desc, rating, time, distance, speed,
          steps, calories, currentDate);
    } else {
      view.showErrorMessage();
    }
  }
}
