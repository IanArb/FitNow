package com.ianarbuckle.fitnow.authentication.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */

public interface LoginPresenter {
  void firebaseAuthWithGoogle(GoogleSignInAccount account);
  void logInUser(String email, String password);
  void setSharedPreferences();
  String getUserDisplayName();
}
