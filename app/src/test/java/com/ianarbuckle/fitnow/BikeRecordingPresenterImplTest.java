package com.ianarbuckle.fitnow;

import com.ianarbuckle.fitnow.activities.bike.biketimer.BikeRecordingPresenterImpl;
import com.ianarbuckle.fitnow.activities.bike.biketimer.BikeRecordingView;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelper;
import com.ianarbuckle.fitnow.helpers.TimerHelper;
import com.ianarbuckle.fitnow.helpers.googlefit.GoogleFitHelper;

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
public class BikeRecordingPresenterImplTest {

  @Mock
  BikeRecordingPresenterImpl presenter;

  @Mock
  AuthenticationHelper authenticationHelper;

  @Mock
  FirebaseStorageHelper storageHelper;

  @Mock
  BikeRecordingView view;

  @Mock
  TimerHelper timerHelper;

  @Mock
  GoogleFitHelper googleFitHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new BikeRecordingPresenterImpl(view, authenticationHelper);
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
