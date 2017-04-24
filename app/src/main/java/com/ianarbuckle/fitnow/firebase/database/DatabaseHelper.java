package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;
/**
 * Created by Ian Arbuckle on 27/01/2017.
 *
 */

public interface DatabaseHelper {
  void sendWalkingResultsToFirebase(String desc, String username, float rating, String time, String distance,
                                    String speed, String steps, String calories,
                                    String currentDate);
  void sendRunningResultsToFirebase(String username, String desc, float rating, String time, String distance,
                                    String speed, String steps, String calories, String date);
  void receiveWalkingResultsFromFirebase(ChildEventListener listener);
  void receiveUploadsFromFirebase(ValueEventListener listener, String directory);
}
