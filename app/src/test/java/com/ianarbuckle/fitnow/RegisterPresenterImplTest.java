package com.ianarbuckle.fitnow;

import com.ianarbuckle.fitnow.authentication.register.RegisterPresenterImpl;
import com.ianarbuckle.fitnow.authentication.register.RegisterView;
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

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterPresenterImplTest {

  private RegisterPresenterImpl presenter;

  @Mock
  AuthenticationHelper authenticationHelper;

  @Mock
  RegisterView view;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new RegisterPresenterImpl(authenticationHelper);
    presenter.setView(view);
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
    verify(view).hideProgress();
    verify(view).showInvalidEmailMessage();
  }

  @Test
  public void testIfPasswordsMatchAreEmpty() throws Exception {
    presenter.validatePassword(" ", " ");
    verify(view).hideProgress();
    verify(view).showPasswordEmptyMessage();
  }

  @Test
  public void testIfPasswordsMatchAreDifferent() throws Exception {
    presenter.validatePassword("bacon", "tuna");
    verify(view).showErrorMessage();
    verify(view).hideProgress();
  }

  @Test
  public void testIfPasswordsMatch() throws Exception {
    presenter.validatePassword("bacon", "bacon");
    verify(view).showProgress();
    verify(view).registerOnPasswordMatch();
  }
}
