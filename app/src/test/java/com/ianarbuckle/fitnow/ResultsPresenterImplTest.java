package com.ianarbuckle.fitnow;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsPresenterImpl;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.ResultsView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 15/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ResultsPresenterImplTest {

  @Mock
  ResultsPresenterImpl presenter;

  @Mock
  ResultsView view;

  @Mock
  DatabaseHelper databaseHelper;

  @Mock
  AuthenticationHelper authenticationHelper;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new ResultsPresenterImpl(databaseHelper, authenticationHelper);
    presenter.setView(view);
  }

  @Test
  public void testSendResultsIsEmpty() throws Exception {
    presenter.sendResultsToNetwork("", 0, "", "", "", "", "", "");
    verify(view).showErrorMessage();
  }

  @Test
  public void testSendResultsIsNull() throws Exception {
    presenter.sendResultsToNetwork(null, 0, null, null, null, null, null, null);
    verify(view).showErrorMessage();
  }

}
