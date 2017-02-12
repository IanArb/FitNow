package com.ianarbuckle.fitnow;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.fitnow.authentication.AuthLoginView;
import com.ianarbuckle.fitnow.authentication.AuthPresenterImpl;
import com.ianarbuckle.fitnow.authentication.AuthRegisterView;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.RequestListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

  @Mock
  public GoogleSignInAccount account;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new AuthPresenterImpl(authenticationHelper);
    presenter.setRegisterView(registerView);
    presenter.setView(loginView);
  }

  @Test
  public void testRegisterValidUser() throws Exception {
    presenter.registerAccount("email", "test");
    verify(authenticationHelper).logOutUser();
    verify(authenticationHelper).registerUser(anyString(), anyString(), any(RequestListener.class));
  }

  @Test
  public void testAttemptToRegisterIfUserEmailIsEmpty() throws Exception {
    presenter.registerAccount("  ", "test");
    verify(registerView).hideProgress();
    verify(registerView).showInvalidEmailMessage();
  }

  @Test
  public void testIfPasswordsMatchAreEmpty() throws Exception {
    presenter.validatePassword(" ", " ");
    verify(registerView).hideProgress();
    verify(registerView).showPasswordEmptyMessage();
  }

  @Test
  public void testIfPasswordsMatchAreDifferent() throws Exception {
    presenter.validatePassword("bacon", "tuna");
    verify(registerView).showErrorMessage();
    verify(registerView).hideProgress();
  }

  @Test
  public void testIfPasswordsMatch() throws Exception {
    presenter.validatePassword("bacon", "bacon");
    verify(registerView).showProgress();
    verify(registerView).registerOnPasswordMatch();
  }

  @Test
  public void testLoginUserIfEmailEmpty() throws Exception {
    presenter.logInUser(" ", "password");
    verify(loginView).showErrorEmail();
    verify(loginView).hideProgress();
  }

  @Test
  public void testLoginUserIfPasswordEmpty() throws Exception {
    presenter.logInUser("email", " ");
    verify(loginView).showErrorPassword();
    verify(loginView).hideProgress();
  }

  @Test
  public void testLoginUser() throws Exception {
    presenter.logInUser("email", "password");
    verify(loginView).showProgress();
    verify(authenticationHelper).logOutUser();
    verify(authenticationHelper).logInUser(anyString(), anyString(), any(RequestListener.class));
    verifyNoMoreInteractions(loginView, authenticationHelper);
  }

  @Test
  public void testGooglelogin() throws Exception {
    presenter.firebaseAuthWithGoogle(account);
  }

}
