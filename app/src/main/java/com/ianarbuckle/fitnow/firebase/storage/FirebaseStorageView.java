package com.ianarbuckle.fitnow.firebase.storage;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 01/02/2017.
 *
 */

public interface FirebaseStorageView {
  Activity getActivity();
  Context getContext();
  void showLoading();
  void dismissLoading();
  void showStorageErrorMessage();
  void setSuccessMessage();
}
