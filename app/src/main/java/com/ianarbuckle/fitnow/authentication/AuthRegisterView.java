package com.ianarbuckle.fitnow.authentication;

/**
 * Created by Ian Arbuckle on 01/12/2016.
 *
 */

public interface AuthRegisterView {
  void showErrorMessage();
  void showInvalidEmailMessage();
  void onFailure();
  void onSuccess();
  void onLogin();
  void hideProgress();
  void showProgress();
  void registerOnPasswordMatch();
  void showEmailEmptyMessage();
  void showPasswordEmptyMessage();
}
