package com.ianarbuckle.fitnow.activities.walking.walkingtimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public interface WalkRecordingPresenter {
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
  Bundle setDateBundle();
  boolean checkLocationPermission(Fragment fragment);
}
