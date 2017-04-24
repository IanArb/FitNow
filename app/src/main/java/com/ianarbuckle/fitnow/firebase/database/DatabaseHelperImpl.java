package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 27/01/2017.
 *
 */

public class DatabaseHelperImpl implements DatabaseHelper {

  private final FirebaseDatabase firebaseDatabase;

  public DatabaseHelperImpl(FirebaseDatabase firebaseDatabase) {
    this.firebaseDatabase = firebaseDatabase;
  }

  @Override
  public void sendWalkingResultsToFirebase(String username, String desc, float rating, String time, String distance,
                                           String speed, String steps, String calories,
                                           String currentDate) {
    RunWalkModel runWalkModel = new RunWalkModel(username, desc, rating, time, distance, speed, steps, calories, currentDate);
    firebaseDatabase.getReference(Constants.RESULTS_WALKING_REFERENCE).push().setValue(runWalkModel);
  }

  @Override
  public void sendRunningResultsToFirebase(String username, String desc, float rating, String time, String distance,
                                            String speed, String steps, String calories, String date) {
    RunWalkModel runWalkModel = new RunWalkModel(username, desc, rating, time, distance, speed, steps, calories, date);
    firebaseDatabase.getReference(Constants.RESULTS_RUNNING_REFERENCE).push().setValue(runWalkModel);
  }

  @Override
  public void sendCyclingResultsToFirebase(String username, String desc, float rating, String time, String distance, String speed, String pedalSpeed, String calories, String date) {
    BikeModel bikeModel = new BikeModel(username, desc, rating, time, distance, speed, pedalSpeed, calories, date);
    firebaseDatabase.getReference(Constants.RESULTS_CYCLING_REFERENCE).push().setValue(bikeModel);
  }

  @Override
  public void receiveUploadsFromFirebase(ValueEventListener listener, String directory) {
    firebaseDatabase.getReference(directory).addValueEventListener(listener);
  }
}
