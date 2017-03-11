package com.ianarbuckle.fitnow;

import android.app.Activity;

import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageHelperImpl;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Ian Arbuckle on 10/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class FirebaseStorageImplTest {

  private FirebaseStorageHelperImpl firebaseStorageHelper;

  @Mock
  Activity activity;

  @Mock
  public FirebaseStorageView view;

  @Before
  public void setup() throws Exception {
    firebaseStorageHelper = new FirebaseStorageHelperImpl(view, activity);
  }

  @Test
  public void testUploadSuccess() throws Exception {
    firebaseStorageHelper.uploadToStorage(0, 0);
  }

  @Test
  public void testCreateImageFile() throws Exception {
    firebaseStorageHelper.createImageFile();
  }
}
