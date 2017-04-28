package com.ianarbuckle.fitnow.activities.running.runningtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public interface RunRecordingPresenter {
  void startTimer();
  void stopTimer();
  void pauseTimer();
  void resumeTimer();
  boolean isRunning();
  void initMap(GoogleMap googleMap);
  Intent takePicture();
  void onActivityResult(int requestCode, int resultCode);
  void onRequestPermission();
  void initGoogleClient();
  void disconnectGoogleClient();
  void onSaveInstanceState(Bundle bundle);
  Bundle setBundle();
  Bundle setTimeBundle();
  boolean checkLocationPermission(Fragment fragment);
}
