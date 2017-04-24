package com.ianarbuckle.fitnow;

import com.google.firebase.database.ValueEventListener;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.gallery.GalleryPresenterImpl;
import com.ianarbuckle.fitnow.gallery.GalleryView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 20/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GalleryPresenterImplTest {

  @Mock
  GalleryPresenterImpl presenter;

  @Mock
  GalleryView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new GalleryPresenterImpl(view, databaseHelper);
  }

  @Test
  public void testRetrieveUploadSuccess() throws Exception {
    presenter.getUploads();
    verify(view).showProgress();
    verify(databaseHelper).receiveUploadsFromFirebase(any(ValueEventListener.class));
  }

}
