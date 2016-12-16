package com.ianarbuckle.fitnow.authentication;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.fitnow.authentication.firebase.AuthenticationHelper;
import com.ianarbuckle.fitnow.authentication.firebase.RequestListener;
import com.ianarbuckle.fitnow.utility.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public class AuthPresenterImpl implements AuthPresenter {

  private AuthLoginView view;

  private AuthRegisterView registerView;

  private AuthenticationHelper authenticationHelper;

  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public AuthPresenterImpl(AuthenticationHelper authenticationHelper) {
    this.authenticationHelper = authenticationHelper;
  }

  @Override
  public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    authenticationHelper.googleLogin(account, provideLoginCallback());
  }

  @Override
  public void registerAccount(String email, String password) {
    if(StringUtils.isStringEmptyorNull(email) && validateEmail(email)) {
      registerView.showInvalidEmailMessage();
      registerView.hideProgress();
    } else {
      authenticationHelper.logOutUser();
      authenticationHelper.registerUser(email, password, provideRegisterCallback());
    }

  }

  private boolean validateEmail(String email) {
    Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  @Override
  public String getUserDisplayName() {
    return authenticationHelper.getUserDisplayName();
  }

  @Override
  public String getUserEmail() {
    return authenticationHelper.getUserEmail();
  }

  @Override
  public String getUserPhoto() {
    return authenticationHelper.getUserPhoto();
  }

  @Override
  public void logInUser(String email, String password) {
    view.showProgress();
    if(StringUtils.isStringEmptyorNull(email)) {
      view.showErrorEmail();
      view.hideProgress();
    }
    else if(StringUtils.isStringEmptyorNull(password)) {
      view.showErrorPassword();
      view.hideProgress();
    } else {
      authenticationHelper.logOutUser();
      authenticationHelper.logInUser(email, password, provideLoginCallback());
    }
  }

  private RequestListener provideLoginCallback() {
    return new RequestListener() {
      @Override
      public void onSucessRequest() {
        view.onSuccess();
        view.onLogin();
        view.hideProgress();
      }

      @Override
      public void onFailureRequest() {
        view.hideProgress();
        view.onFailure();
      }
    };
  }

  private RequestListener provideRegisterCallback() {
    return new RequestListener() {
      @Override
      public void onSucessRequest() {
        registerView.hideProgress();
        registerView.onSuccess();
        registerView.onLogin();
      }

      @Override
      public void onFailureRequest() {
        registerView.hideProgress();
        registerView.onFailure();
      }
    };
  }

  @Override
  public void validatePassword(String password, String confirmPassword) {
    registerView.showProgress();
    if (!StringUtils.isStringEmptyorNull(password, confirmPassword) && password.equals(confirmPassword)) {
      registerView.registerOnPasswordMatch();
    } else {
      registerView.hideProgress();
      registerView.showErrorMessage();
    }
  }

  public void setView(AuthLoginView view) {
    this.view = view;
  }

  public void setRegisterView(AuthRegisterView view) {
    this.registerView = view;
  }

}
