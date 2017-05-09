package com.ianarbuckle.fitnow;

import android.app.Activity;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.activities.walking.walkingtimer.WalkRecordingPresenterImpl;
import com.ianarbuckle.fitnow.activities.walking.walkingtimer.WalkRecordingView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.TimerTask;

import static org.mockito.ArgumentMatchers.anyString;
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
  public void testTimeIsNotNull() throws Exception {
    presenter.setResult("time");
    verify(view).setTimerText(anyString());
  }

  @Test
  public void testTimerIsNull() throws Exception {
    presenter.setResult(null);
    verify(view).setTimerText(null);
  }

}
