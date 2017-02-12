package com.ianarbuckle.fitnow.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.RequestListener;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

import java.util.regex.Matcher;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public class AuthPresenterImpl implements AuthPresenter {

  private AuthLoginView view;

  private AuthRegisterView registerView;

  private AuthenticationHelper authenticationHelper;

  public AuthPresenterImpl(AuthenticationHelper authenticationHelper) {
    this.authenticationHelper = authenticationHelper;
  }

  @Override
  public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    authenticationHelper.googleLogin(account, provideLoginCallback());
  }

  @Override
  public void registerAccount(String email, String password) {
    if(StringUtils.isStringEmptyorNull(email) && !emailMatcher(email)) {
      registerView.showInvalidEmailMessage();
      registerView.hideProgress();
    }
    else {
      authenticationHelper.logOutUser();
      authenticationHelper.registerUser(email, password, provideRegisterCallback());
    }
  }

  private boolean emailMatcher(String email) {
    Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
    return matcher.find();
  }

  @Override
  public String getUserDisplayName() {
    return authenticationHelper.getUserDisplayName();
  }

  @Override
  public void logInUser(String email, String password) {
    if(StringUtils.isStringEmptyorNull(email)) {
      view.showErrorEmail();
      view.hideProgress();
    }
    else if(StringUtils.isStringEmptyorNull(password)) {
      view.showErrorPassword();
      view.hideProgress();
    }
    else {
      view.showProgress();
      authenticationHelper.logOutUser();
      authenticationHelper.logInUser(email, password, provideLoginCallback());
    }
  }

  @VisibleForTesting
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

  @VisibleForTesting
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

  public void validateEmail(String email) {
    registerView.showProgress();
    if(StringUtils.isStringEmptyorNull(email)) {
      registerView.hideProgress();
      registerView.showEmailEmptyMessage();
    } else if(emailMatcher(email)) {
      registerView.hideProgress();
      registerView.showInvalidEmailMessage();
    }
  }

  @Override
  public void validatePassword(String password, String confirmPassword) {
    if (!StringUtils.isStringEmptyorNull(password, confirmPassword) && password.equals(confirmPassword)) {
      registerView.showProgress();
      registerView.registerOnPasswordMatch();
    }
    else if(StringUtils.isStringEmptyorNull(password, confirmPassword)) {
      registerView.hideProgress();
      registerView.showPasswordEmptyMessage();
    }
    else {
      registerView.hideProgress();
      registerView.showErrorMessage();
    }
  }

  @Override
  public void setSharedPreferences() {
    String username = authenticationHelper.getUserDisplayName();
    String email = authenticationHelper.getUserEmail();
    String photoUrl = authenticationHelper.getUserPhoto();
    SharedPreferences sharedPreferences = view.getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(Constants.NAME_KEY, username);
    editor.putString(Constants.EMAIL_KEY, email);
    editor.putString(Constants.PHOTO_KEY, photoUrl);
    editor.apply();
  }

  public void setView(AuthLoginView view) {
    this.view = view;
  }

  public void setRegisterView(AuthRegisterView view) {
    this.registerView = view;
  }

}
