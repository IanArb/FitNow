package com.ianarbuckle.fitnow;

import com.ianarbuckle.fitnow.authentication.AuthLoginView;
import com.ianarbuckle.fitnow.authentication.AuthPresenterImpl;
import com.ianarbuckle.fitnow.authentication.AuthRegisterView;
import com.ianarbuckle.fitnow.network.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.network.firebase.auth.RequestListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by Ian Arbuckle on 30/01/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthPresenterImplTest {

  private AuthPresenterImpl presenter;

  @Mock
  public AuthenticationHelper authenticationHelper;

  @Mock
  public AuthRegisterView registerView;

  @Mock
  public AuthLoginView loginView;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new AuthPresenterImpl(authenticationHelper);
    presenter.setRegisterView(registerView);
  }

  @Test
  public void testRegisterValidUser() throws Exception {
    presenter.registerAccount("email", "test");
    verify(authenticationHelper).logOutUser();
    verify(authenticationHelper).registerUser(anyString(), anyString(), any(RequestListener.class));
  }

  @Test
  public void testAttemptToRegisterTheUserEmailIsEmpty() throws Exception {
    presenter.registerAccount("  ", "test");
    verify(registerView).hideProgress();
    verify(registerView).showInvalidEmailMessage();
  }

  @Test
  public void testUserNameEmailNull() throws Exception {
    presenter.registerAccount(null, "test");
    verify(registerView).hideProgress();
    verify(registerView).showInvalidEmailMessage();
  }

  @Test
  public void testIfPasswordsMatchAreEmpty() throws Exception {
    presenter.validatePassword(" ", " ");
    verify(registerView).showProgress();
    verify(registerView).hideProgress();
    verify(registerView).showErrorMessage();
  }

  @Test
  public void testIfPasswordsMatchAreDifferent() throws Exception {
    presenter.validatePassword("bacon", "tuna");
    verify(registerView).showProgress();
    verify(registerView).showErrorMessage();
    verify(registerView).hideProgress();
  }

  @Test
  public void testLoginUserNameIsNull() throws Exception {
    presenter.logInUser(null, "password");
    verify(loginView).hideProgress();
    verify(loginView).showErrorEmail();
  }

}
