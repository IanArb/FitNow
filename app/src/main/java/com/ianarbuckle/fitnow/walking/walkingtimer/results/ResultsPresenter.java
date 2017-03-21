package com.ianarbuckle.fitnow.walking.walkingtimer.results;

/**
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public interface ResultsPresenter {
  void setView(ResultsView view);
  void sendResultsToNetwork(String desc, float rating, String time, String distance, String speed, String steps, String calories, String currentDate);
}
