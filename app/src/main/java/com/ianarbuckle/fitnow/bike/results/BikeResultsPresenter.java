package com.ianarbuckle.fitnow.bike.results;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeResultsPresenter {
  void setView(BikeResultsView view);
  void sendResultsToNetwork(final String desc, final float rating, final String time, final float distance, final float speed, final float pedalSpeed, final int calories, final String date);
}
