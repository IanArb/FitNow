package com.ianarbuckle.fitnow.authentication.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.RequestListener;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.StringUtils;

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */

public class LoginPresenterImpl implements LoginPresenter {

  private LoginView view;

  private AuthenticationHelper authenticationHelper;

  public LoginPresenterImpl(AuthenticationHelper authenticationHelper) {
    this.authenticationHelper = authenticationHelper;
  }

  public void setView(LoginView view) {
    this.view = view;
  }

  @Override
  public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    authenticationHelper.googleLogin(account, provideCallback());
  }

  @Override
  public void annoymouslyLogin() {
    authenticationHelper.anonymouslyLogin(provideCallback());
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
      authenticationHelper.logInUser(email, password, provideCallback());
    }
  }

  @VisibleForTesting
  private RequestListener provideCallback() {
    return new RequestListener() {
      @Override
      public void onSucessRequest() {
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

  @Override
  public String getUserDisplayName() {
    return authenticationHelper.getUserDisplayName();
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


}
