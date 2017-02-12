package com.ianarbuckle.fitnow.authentication;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public interface AuthLoginView {

  Activity getActivity();

  Context getContext();

  void onSuccess();

  void onFailure();

  void onLogin();

  void hideProgress();

  void showProgress();

  void showErrorEmail();

  void showErrorPassword();

}
