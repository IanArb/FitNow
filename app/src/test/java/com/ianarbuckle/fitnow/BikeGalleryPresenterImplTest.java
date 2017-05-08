package com.ianarbuckle.fitnow;

import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.activities.bike.gallery.BikeGalleryPresenterImpl;
import com.ianarbuckle.fitnow.activities.bike.gallery.BikeGalleryView;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BikeGalleryPresenterImplTest {

  @Mock
  BikeGalleryPresenterImpl presenter;

  @Mock
  BikeGalleryView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new BikeGalleryPresenterImpl(view, databaseHelper);
  }

  @Test
  public void testRetrieveUploadSuccess() throws Exception {
    presenter.getUploads();
    verify(view).showProgress();
    verify(databaseHelper).receiveUploadsFromFirebase(any(ValueEventListener.class), anyString());
  }
}
