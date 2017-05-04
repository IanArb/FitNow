package com.ianarbuckle.fitnow.activities.bike.leaderboard;

/**
 * Created by Ian Arbuckle on 27/04/2017.
 *
 */

public interface BikeLeadersPresenter {
  void setEmptyState();
  void setSpeedQuery(int layout);
  void setDistanceQuery(int layout);
  void setPedalQuery(int layout);
  void setTimeQuery(int layout);
}
