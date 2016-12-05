package com.ianarbuckle.fitnow.authentication;

import android.content.Context;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public interface AuthLoginView {

  Context getContext();

  void onSuccess();

  void onFailure();

  void onLogin();

  void hideProgress();

  void showProgress();

  void showErrorEmail();

  void showErrorPassword();

}
