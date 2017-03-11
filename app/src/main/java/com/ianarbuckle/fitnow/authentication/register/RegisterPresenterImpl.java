package com.ianarbuckle.fitnow.authentication.register;

import android.support.annotation.VisibleForTesting;

import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.RequestListener;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.util.regex.Matcher;

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */

public class RegisterPresenterImpl implements RegisterPresenter {

  private RegisterView view;

  private AuthenticationHelper authenticationHelper;

  public RegisterPresenterImpl(AuthenticationHelper authenticationHelper) {
    this.authenticationHelper = authenticationHelper;
  }

  public void setView(RegisterView view) {
    this.view = view;
  }

  @Override
  public void registerAccount(String email, String password) {
    if(StringUtils.isStringEmptyorNull(email) && !emailMatcher(email)) {
      view.showInvalidEmailMessage();
      view.hideProgress();
    }
    else {
      authenticationHelper.logOutUser();
      authenticationHelper.registerUser(email, password, provideCallback());
    }
  }

  private boolean emailMatcher(String email) {
    Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  @VisibleForTesting
  private RequestListener provideCallback() {
    return new RequestListener() {
      @Override
      public void onSucessRequest() {
        view.hideProgress();
        view.onSuccess();
        view.onLogin();
      }

      @Override
      public void onFailureRequest() {
        view.hideProgress();
        view.onFailure();
      }
    };
  }

  @Override
  public void validateEmail(String email) {
    view.showProgress();
    if(StringUtils.isStringEmptyorNull(email)) {
      view.hideProgress();
      view.showEmailEmptyMessage();
    }
    if(emailMatcher(email)) {
      view.hideProgress();
      view.showInvalidEmailMessage();
    }
  }

  @Override
  public void validateUsername(String username) {
    view.showProgress();
    if(StringUtils.isStringEmptyorNull(username)) {
      view.hideProgress();
      view.showUsernameEmptyMessage();
    }
    if(username.length() >= 2) {
      view.showInvalidUsernameMessage();
    }
  }

  @Override
  public void validatePassword(String password, String confirmPassword) {
    if (!StringUtils.isStringEmptyorNull(password, confirmPassword) && password.equals(confirmPassword)) {
      view.showProgress();
      view.registerOnPasswordMatch();
    }

    if(StringUtils.isStringEmptyorNull(password, confirmPassword)) {
      view.hideProgress();
      view.showPasswordEmptyMessage();
    }
    else {
      view.hideProgress();
      view.showErrorMessage();
    }
  }
}
