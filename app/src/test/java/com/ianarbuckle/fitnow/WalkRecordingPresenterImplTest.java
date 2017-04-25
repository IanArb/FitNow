package com.ianarbuckle.fitnow;

import android.app.Activity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.location.LocationHelper;
import com.ianarbuckle.fitnow.walking.walkingtimer.WalkRecordingPresenterImpl;
import com.ianarbuckle.fitnow.walking.walkingtimer.WalkRecordingView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.TimerTask;

import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 10/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WalkRecordingPresenterImplTest {

  @Mock
  WalkRecordingPresenterImpl presenter;

  @Mock
  FirebaseStorageHelper firebaseStorageHelper;

  @Mock
  AuthenticationHelper authenticationHelper;

  @Mock
  WalkRecordingView view;

  @Mock
  Activity activity;

  @Mock
  TimerTask timerTask;



  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new WalkRecordingPresenterImpl(view, authenticationHelper);
  }

  @Test
  public void testImageFileCreated() throws Exception {
    firebaseStorageHelper.createImageFile();
    verify(firebaseStorageHelper).createImageFile();
  }

  @Test
  public void testStopTimer() throws Exception {
    presenter.stopTimer();
  }

  @Test
  public void testPauseTimer() throws Exception {
    presenter.pauseTimer();
  }

}
