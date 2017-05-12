package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.models.RunWalkModel;

/**
 * Created by Ian Arbuckle on 27/01/2017.
 *
 */

public interface DatabaseHelper {
  void sendRunWalkResultsToDatabase(RunWalkModel runWalkModel, String directory);
  void sendBikeResultsToDatabase(BikeModel bikeModel);
  void receiveUploadsFromFirebase(ValueEventListener listener, String directory);
}
