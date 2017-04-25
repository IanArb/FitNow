package com.ianarbuckle.fitnow.running.results;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public interface RunResultsPresenter {
  void sendResultsToNetwork(final String desc, final float rating, final String time, final float distance, final float speed, final int steps, final int calories, final String currentDate);
}
