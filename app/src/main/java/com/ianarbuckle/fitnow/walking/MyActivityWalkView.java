package com.ianarbuckle.fitnow.walking;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Ian Arbuckle on 26/01/2017.
 *
 */

public interface MyActivityWalkView {
  Activity getActivity();
  Context getContext();
  void setAdapter(MyActivityAdapter adapter);
  void showErrorMessage();
}
