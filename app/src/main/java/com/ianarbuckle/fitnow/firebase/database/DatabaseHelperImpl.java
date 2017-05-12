package com.ianarbuckle.fitnow.firebase.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.models.BikeModel;
import com.ianarbuckle.fitnow.models.LatLngModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.models.RunWalkModel;

import java.util.List;

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
  public void sendRunWalkResultsToDatabase(RunWalkModel runWalkModel, String directory) {
    float rating = runWalkModel.getRating();
    int calories = runWalkModel.getCalories();
    String date = runWalkModel.getCurrentDate();
    float distance = runWalkModel.getDistance();
    String desc = runWalkModel.getDesc();
    float speed = runWalkModel.getSpeed();
    int steps = runWalkModel.getSteps();
    String username = runWalkModel.getUsername();
    int time = runWalkModel.getTime();
    List<LatLngModel> latLngModels = runWalkModel.getLatLngModels();

    runWalkModel = new RunWalkModel(username, desc, rating, time, distance, speed, steps, calories, date, latLngModels);
    firebaseDatabase.getReference(directory).push().setValue(runWalkModel);
  }

  @Override
  public void sendBikeResultsToDatabase(BikeModel bikeModel) {
    int calories = bikeModel.getCalories();
    String date = bikeModel.getDate();
    float distance = bikeModel.getDistance();
    String desc = bikeModel.getDesc();
    float pedalSpeed = bikeModel.getPedalSpeed();
    float rating = bikeModel.getRating();
    int time = bikeModel.getTime();
    float speed = bikeModel.getSpeed();
    String username = bikeModel.getUsername();
    List<LatLngModel> latLngModels = bikeModel.getLatLngModels();

    bikeModel = new BikeModel(username, desc, rating, time, distance, speed, pedalSpeed, calories, date, latLngModels);
    firebaseDatabase.getReference(Constants.RESULTS_CYCLING_REFERENCE).push().setValue(bikeModel);
  }

  @Override
  public void receiveUploadsFromFirebase(ValueEventListener listener, String directory) {
    firebaseDatabase.getReference(directory).addValueEventListener(listener);
  }
}
