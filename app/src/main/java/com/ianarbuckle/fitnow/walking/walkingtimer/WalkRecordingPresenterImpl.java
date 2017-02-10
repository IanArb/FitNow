package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.google.android.gms.maps.GoogleMap;
import com.ianarbuckle.fitnow.utils.PermissionsManager;
import com.ianarbuckle.fitnow.utils.location.LocationHelper;
import com.ianarbuckle.fitnow.utils.location.LocationHelperImpl;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelperImpl;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageView;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class WalkRecordingPresenterImpl implements WalkRecordingPresenter {

  private WalkRecordingView view;
  FirebaseStorageView storageView;
  private LocationHelper locationHelper;
  private FirebaseStorageHelper firebaseStorageHelper;
  private Timer timer;
  TimerTask timerTask;
  private final Handler handler;

  private int seconds;
  String result;
  private boolean running;

  public WalkRecordingPresenterImpl(WalkRecordingView view, FirebaseStorageView storageView) {
    handler = new Handler();
    running = false;
    this.view = view;
    this.storageView = storageView;
    this.locationHelper = new LocationHelperImpl(view.getContext());
    this.firebaseStorageHelper = new FirebaseStorageHelperImpl(storageView, view.getActivity(), view.getContext());
  }

  @Override
  public void startTimer() {
    if(isRunning()) {
      return;
    }

    if(timer == null) {
      running = true;
      initTimerTask();
    }
  }

  private void initTimerTask() {
    timer = new Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          @Override
          public void run() {
            updateTextInUiThread();
          }
        });
      }
    };
    timer.schedule(timerTask, 1, 2000);
  }

  private void updateTextInUiThread() {
    view.getActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        updateTimerText();
      }
    });
  }

  private void updateTimerText() {
    seconds += 1;
    result = getTimeFormat(seconds);
    view.setTimerText(result);
  }

  @Override
  public void stopTimer() {
    if(timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  @Override
  public void pauseTimer() {
    if(!isRunning()) {
      return;
    }
    this.stopTimer();
    running = false;
  }

  public boolean isRunning() {
    return running;
  }

  @Override
  public void resumeTimer() {
    this.startTimer();
  }

  private String getTimeFormat(int seconds) {
    Seconds convertSeconds = Seconds.seconds(seconds);
    Period period = new Period(convertSeconds);
    return Constants.FORMAT_HOURS_MINUTES_SECONDS.print(period.normalizedStandard(PeriodType.time()));
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  public void onRequestPermission() {
    locationHelper.onRequestPermission();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public void checkCameraPermission(Fragment fragment) {
    String accessCamera = android.Manifest.permission.CAMERA;
    String[] permissions = {accessCamera};
    if (PermissionsManager.checkPermission(view.getContext(), accessCamera)
        && view.getActivity().shouldShowRequestPermissionRationale(accessCamera)) {
      PermissionsManager.requestPermissions(fragment, Constants.PERMISSION_REQUEST_CAMERA, permissions);
    }
    else if (PermissionsManager.isCameraPermissionGranted(view.getContext())) {
      view.getActivity().startActivityForResult(takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
    }
  }

  @Override
  public Intent takePicture() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(view.getActivity().getPackageManager()) != null) {
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ex) {
        view.showErrorMessage();
      }
      if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(view.getContext(),
            Constants.PROVIDER_DIR,
            photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
      }
    }
    return takePictureIntent;
  }

  private File createImageFile() throws IOException {
    return firebaseStorageHelper.createImageFile();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode) {
    if (requestCode == Constants.PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {
      firebaseStorageHelper.uploadToStorage();
    }
  }

}
