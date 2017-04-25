package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.ValueEventListener;
/**
 * Created by Ian Arbuckle on 27/01/2017.
 *
 */

public interface DatabaseHelper {
  void sendWalkingResultsToFirebase(String desc, String username, float rating, String time, float distance,
                                    float speed, int steps, int calories,
                                    String currentDate);
  void sendRunningResultsToFirebase(String username, String desc, float rating, String time, float distance,
                                    float speed, int steps, int calories, String date);
  void sendCyclingResultsToFirebase(String username, String desc, float rating, String time, float distance,
                                    float speed, float pedalSpeed, int calories, String date);
  void receiveUploadsFromFirebase(ValueEventListener listener, String directory);
}
