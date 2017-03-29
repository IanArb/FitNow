package com.ianarbuckle.fitnow;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Ian Arbuckle on 27/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthHelperImplTest {

  @Mock
  FirebaseAuth firebaseAuth;

  @Mock
  FitNowApplication fitNowApplication;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

}
