package com.ianarbuckle.fitnow.authentication.register;

/**
 * Created by Ian Arbuckle on 11/03/2017.
 *
 */

public interface RegisterPresenter {
  void registerAccount(String email, String password);
  void validateEmail(String email);
  void validateUsername(String username);
  void validatePassword(String password, String confirmPassword);
}
