package com.ianarbuckle.fitnow.activities.walking.leaderboard;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public interface WalkLeadersPresenter {
  void setEmptyState();
  void setStepsQuery(int layout);
  void setSpeedQuery(int layout);
  void setDistanceQuery(int layout);
  void setTimeQuery(int layout);
}
