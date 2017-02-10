package com.ianarbuckle.fitnow;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ianarbuckle.fitnow.walking.walkingtimer.WalkRecordingPresenterImpl;
import com.ianarbuckle.fitnow.walking.walkingtimer.WalkRecordingView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Ian Arbuckle on 25/01/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WalkRecordingPresenterImplTest {

  private WalkRecordingPresenterImpl presenter;

  @Mock
  public WalkRecordingView view;

  @Mock
  public StorageReference storageReference;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new WalkRecordingPresenterImpl(view);
    storageReference = FirebaseStorage.getInstance().getReference();
  }

  @Test
  public void testTimerIsRunning() throws Exception {
    presenter.isRunning();
  }

}
