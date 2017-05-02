package com.ianarbuckle.fitnow.authentication.login;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public interface LoginView {
  Activity getActivity();
  Context getContext();
  void onFailure();
  void onLogin();
  void hideProgress();
  void showProgress();
  void showErrorEmail();
  void showErrorPassword();
}
