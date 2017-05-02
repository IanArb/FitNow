package com.ianarbuckle.fitnow.activities.running.leaderboard;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public interface RunLeadersPresenter {
  void setEmptyState();
  void setSpeedQuery(int layout);
  void setDistanceQuery(int layout);
  void setStepsQuery(int layout);
}
