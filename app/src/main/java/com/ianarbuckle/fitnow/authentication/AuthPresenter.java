package com.ianarbuckle.fitnow.authentication;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public interface AuthPresenter {

  void firebaseAuthWithGoogle(GoogleSignInAccount account);

  void registerAccount(String email, String password);

  void validatePassword(String password, String confirmPassword);

  void logInUser(String email, String password);

  void setSharedPreferences();

  String getUserDisplayName();

}
