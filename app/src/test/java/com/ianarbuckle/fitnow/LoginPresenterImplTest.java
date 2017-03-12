package com.ianarbuckle.fitnow;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.fitnow.authentication.login.LoginPresenterImpl;
import com.ianarbuckle.fitnow.authentication.login.LoginView;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.RequestListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterImplTest {

  private LoginPresenterImpl presenter;

  @Mock
  public AuthenticationHelper authenticationHelper;

  @Mock
  LoginView view;

  @Mock
  GoogleSignInAccount account;


  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new LoginPresenterImpl(authenticationHelper);
    presenter.setView(view);
  }

  @Test
  public void testGooglelogin() throws Exception {
    presenter.firebaseAuthWithGoogle(account);
    verify(authenticationHelper).googleLogin(any(GoogleSignInAccount.class), any(RequestListener.class));
  }

  @Test
  public void testLoginUserIfEmailEmpty() throws Exception {
    presenter.logInUser(" ", "password");
    verify(view).showErrorEmail();
    verify(view).hideProgress();
  }

  @Test
  public void testLoginUserIfPasswordEmpty() throws Exception {
    presenter.logInUser("email", " ");
    verify(view).showErrorPassword();
    verify(view).hideProgress();
  }

  @Test
  public void testLoginUser() throws Exception {
    presenter.logInUser("email", "password");
    verify(view).showProgress();
    verify(authenticationHelper).logOutUser();
    verify(authenticationHelper).logInUser(anyString(), anyString(), any(RequestListener.class));
    verifyNoMoreInteractions(view, authenticationHelper);
  }

}
