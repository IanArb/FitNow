package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ianarbuckle.fitnow.network.NetworkClient;
import com.ianarbuckle.fitnow.utils.PermissionsChecker;
import com.ianarbuckle.fitnow.utils.location.LocationHelper;
import com.ianarbuckle.fitnow.utils.location.LocationHelperImpl;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.walking.walkingtimer.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.walking.walkingtimer.storage.FirebaseStorageHelperImpl;
import com.ianarbuckle.fitnow.walking.walkingtimer.storage.FirebaseStorageView;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class WalkRecordingPresenterImpl implements WalkRecordingPresenter {

  private WalkRecordingView view;
  private LocationHelper locationHelper;
  private FirebaseStorageHelper firebaseStorageHelper;
  private Timer timer;
  TimerTask timerTask;
  private final Handler handler;
  private StorageReference storageReference;

  private int seconds;
  String result;
  private String currentPhotoPath;
  private boolean running;

  public WalkRecordingPresenterImpl(WalkRecordingView view) {
    this.view = view;
    handler = new Handler();
    running = false;
    storageReference = FirebaseStorage.getInstance().getReference();
    this.locationHelper = new LocationHelperImpl(view.getActivity(), view.getContext());
//    this.firebaseStorageHelper = new FirebaseStorageHelperImpl(view.getActivity(), view.getContext());
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
  public boolean checkLocationPermission() {
    return locationHelper.checkLocationPermission();
  }

  public void onRequestPermission() {
    locationHelper.onRequestPermission();
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
    String timeStamp = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH).format(new Date());
    String imageFileName = Constants.JPEG_PREFIX + timeStamp + "_";
    File storageDir = view.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    File image = File.createTempFile(
        imageFileName,
        Constants.JPG_SUFFIX,
        storageDir
    );
    currentPhotoPath = image.getAbsolutePath();
    return image;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode) {
    if (requestCode == Constants.PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {
      File file = new File(currentPhotoPath);
      Uri uri = Uri.fromFile(file);

      if(NetworkClient.isConnectedOrConnecting(view.getContext())) {
        StorageReference filePath = storageReference.child(Constants.FIREBASE_STORAGE_DIR).child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            view.dismissLoading();
            view.setSuccessMessage();
          }
        }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception exception) {
            view.dismissLoading();
            view.showErrorMessage();
          }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            view.showLoading();
          }
        });
      } else {
        view.showErrorMessage();
      }
    }
  }

}
