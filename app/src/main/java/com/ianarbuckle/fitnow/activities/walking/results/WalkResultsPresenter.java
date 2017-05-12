package com.ianarbuckle.fitnow.activities.walking.results;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface WalkResultsPresenter {
  void setView(WalkResultsView view);
  void sendResultsToNetwork();
}
