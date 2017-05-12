package com.ianarbuckle.fitnow.authentication.register;

/**
 * Created by Ian Arbuckle on 01/12/2016.
 *
 */

public interface RegisterView {
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
  void showUsernameEmptyMessage();
  void showInvalidUsernameMessage();
}
