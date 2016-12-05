package com.ianarbuckle.fitnow.authentication.api;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by Ian Arbuckle on 30/11/2016.
 * Reference - https://github.com/filbabic/FirebaseChatApp
 */

public interface AuthenticationHelper {

  void googleLogin(GoogleSignInAccount account, RequestListener listener);

  void registerUser(String email, String password, RequestListener listener);

  String getUserDisplayName();

  void logInUser(String email, String password, RequestListener listener);

  void logOutUser();

  boolean isUserLoggedOut();
}
