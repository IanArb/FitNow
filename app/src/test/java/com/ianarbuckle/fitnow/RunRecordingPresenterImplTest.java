package com.ianarbuckle.fitnow;

import android.app.Activity;

import com.ianarbuckle.fitnow.activities.running.runningtimer.RunRecordingPresenterImpl;
import com.ianarbuckle.fitnow.activities.running.runningtimer.RunRecordingView;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RunRecordingPresenterImplTest {

  @Mock
  RunRecordingPresenterImpl presenter;

  @Mock
  RunRecordingView view;

  @Mock
  Activity activity;

  @Mock
  AuthenticationHelper authenticationHelper;

  @Mock
  FirebaseStorageHelper storageHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new RunRecordingPresenterImpl(view, authenticationHelper);
  }

  @Test
  public void testImageFileCreated() throws Exception {
    storageHelper.createImageFile();
    verify(storageHelper).createImageFile();
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
