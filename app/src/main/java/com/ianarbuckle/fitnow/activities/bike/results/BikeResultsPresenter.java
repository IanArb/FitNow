package com.ianarbuckle.fitnow.activities.bike.results;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface BikeResultsPresenter {
  void setView(BikeResultsView view);
  void sendResultsToNetwork();
}
