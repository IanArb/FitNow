package com.ianarbuckle.fitnow.walking.walkingtimer.results;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.utils.StringUtils;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class ResultsPresenterImpl implements ResultsPresenter {

  private ResultsView view;
  private final DatabaseHelper databaseHelper;
  private AuthenticationHelper authenticationHelper;

  public ResultsPresenterImpl(DatabaseHelper databaseHelper, AuthenticationHelper authenticationHelper) {
    this.databaseHelper = databaseHelper;
    this.authenticationHelper = authenticationHelper;
  }

  public void setView(ResultsView view) {
    this.view = view;
  }

  @Override
  public void sendResultsToNetwork(final String desc, final float rating, final String time, final String distance, final String speed, final String steps, final String calories, final String currentDate) {
    String username = authenticationHelper.getUserDisplayName();
    if(!StringUtils.isStringEmptyorNull(desc)) {
      databaseHelper.sendWalkingResultsToFirebase(username, desc, rating, time, distance, speed,
          steps, calories, currentDate);
    } else {
      view.showErrorMessage();
    }

  }

}
