package com.ianarbuckle.fitnow;

import com.google.firebase.database.ChildEventListener;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.walking.MyActivityPresenterImpl;
import com.ianarbuckle.fitnow.walking.MyActivityWalkView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 21/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MyActivityPresenterImplTest {

  @Mock
  MyActivityPresenterImpl presenter;

  @Mock
  MyActivityWalkView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new MyActivityPresenterImpl(view, databaseHelper);
  }

  @Test
  public void testRetrieveResultsListenerIsNotNull() throws Exception {
    presenter.retrieveWalkingResults();
    verify(databaseHelper).receiveWalkingResultsFromFirebase(any(ChildEventListener.class));
  }

}
