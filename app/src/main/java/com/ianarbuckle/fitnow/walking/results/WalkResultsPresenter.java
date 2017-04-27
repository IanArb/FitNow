package com.ianarbuckle.fitnow.walking.results;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface WalkResultsPresenter {
  void setView(WalkResultsView view);
  void sendResultsToNetwork(String desc, float rating, String time, float distance, float speed, int steps, int calories, String currentDate);
}
