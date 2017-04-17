package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsModel;

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
    ResultsModel resultsModel = new ResultsModel(username, desc, rating, time, distance, speed, steps, calories, currentDate);
    firebaseDatabase.getReference(Constants.RESULTS_WALKING_REFERENCE).push().setValue(resultsModel);
  }

  @Override
  public void receiveWalkingResultsFromFirebase(ChildEventListener listener) {
    firebaseDatabase.getReference(Constants.RESULTS_WALKING_REFERENCE).addChildEventListener(listener);
  }

  @Override
  public void receiveUploadsFromFirebase(ValueEventListener listener, String directory) {
    firebaseDatabase.getReference(directory).addValueEventListener(listener);
  }


}
