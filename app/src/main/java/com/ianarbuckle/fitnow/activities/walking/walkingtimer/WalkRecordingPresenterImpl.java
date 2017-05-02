package com.ianarbuckle.fitnow.activities.walking.walkingtimer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.helpers.googlefit.GoogleFitHelper;
import com.ianarbuckle.fitnow.helpers.googlefit.GoogleFitHelperView;
import com.ianarbuckle.fitnow.helpers.location.LocationHelper;
import com.ianarbuckle.fitnow.helpers.location.LocationHelperImpl;
import com.ianarbuckle.fitnow.models.GalleryModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelperImpl;
import com.ianarbuckle.fitnow.helpers.TimerHelper;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class WalkRecordingPresenterImpl implements WalkRecordingPresenter, TimerHelper.TimerHelperView, GoogleFitHelperView {

  private WalkRecordingView view;
  private LocationHelper locationHelper;
  private FirebaseStorageHelper firebaseStorageHelper;
  private AuthenticationHelper authenticationHelper;

  private Bundle bundle;

  private TimerHelper timerHelper;
  private GoogleFitHelper googleFitHelper;

  public WalkRecordingPresenterImpl(final WalkRecordingView view, AuthenticationHelper authenticationHelper) {
    this.view = view;
    bundle = new Bundle();
    timerHelper = new TimerHelper(view.getActivity(), this);
    this.googleFitHelper = new GoogleFitHelper(view.getActivity(), view.getContext(), this);
    this.locationHelper = new LocationHelperImpl(view.getContext());
    this.firebaseStorageHelper = new FirebaseStorageHelperImpl(view.getActivity());
    this.authenticationHelper = authenticationHelper;
  }

  @Override
  public void startTimer() {
    timerHelper.startTimer();
  }

  @Override
  public void stopTimer() {
    timerHelper.stopTimer();
  }

  @Override
  public void pauseTimer() {
    timerHelper.pauseTimer();
  }

  public boolean isRunning() {
    return timerHelper.isRunning();
  }

  @Override
  public void resumeTimer() {
    timerHelper.resumeTimer();
  }

  @Override
  public void setResult(String result) {
    view.setTimerText(result);
    bundle.putString(Constants.TIME_KEY, result);
  }

  @Override
  public void initMap(GoogleMap googleMap) {
    locationHelper.initMap(googleMap);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public boolean checkLocationPermission(Fragment fragment) {
    return locationHelper.checkLocationPermission(fragment);
  }

  @Override
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
    return firebaseStorageHelper.createImageFile();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode) {
    firebaseStorageHelper.uploadToStorage(provideSuccessCallback(), provideFailureCallback(), provideProgressCallback());
    googleFitHelper.requestOAuth(requestCode, resultCode);
  }

  private OnSuccessListener<UploadTask.TaskSnapshot> provideSuccessCallback() {
    return new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @SuppressWarnings("VisibleForTests")
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        view.dismissLoading();
        view.showSuccessMessage();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_DATABASE_UPLOAD_WALKING);
        String date = DateFormat.getDateInstance().format(new Date());
        String displayName = authenticationHelper.getUserDisplayName();
        bundle.putString(Constants.DATE_KEY, date);
        Uri downloadUrl = taskSnapshot.getDownloadUrl();
        assert downloadUrl != null;
        GalleryModel model = new GalleryModel(downloadUrl.toString(), displayName, date);
        String uploadId = databaseReference.push().getKey();
        databaseReference.child(uploadId).setValue(model);
      }
    };
  }

  private OnFailureListener provideFailureCallback() {
    return new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        view.showStorageErrorMessage();
      }
    };
  }

  private OnProgressListener<UploadTask.TaskSnapshot> provideProgressCallback() {
    return new OnProgressListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        view.showLoading();
      }
    };
  }

  private void requestOAuth(int requestCode, int resultCode) {
    googleFitHelper.requestOAuth(requestCode, resultCode);
  }

  @Override
  public void initGoogleClient() {
    googleFitHelper.initGoogleClient();
  }


  @Override
  public void disconnectGoogleClient() {
    googleFitHelper.disconnectGoogleClient();
  }

  @Override
  public Bundle setBundle() {
    return googleFitHelper.setBundle();
  }

  @Override
  public Bundle setTimeBundle() {
    return timerHelper.getBundle();
  }

  @Override
  public Bundle setDateBundle() {
    return bundle;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    googleFitHelper.onSaveInstanceState(outState);
  }

  @Override
  public void setTextSteps(String value) {
    view.setTextSteps(value);
  }

  @Override
  public void setTextSpeed(String value) {
    view.setTextSpeed(value);
  }

  @Override
  public void setTextDistance(String value) {
    view.setTextDistance(value);
  }

  @Override
  public void setCaloriesText(String value) {
    view.setCaloriesText(value);
  }

  @Override
  public void setPedallingText(String value) {
    //Stub method
  }
}
