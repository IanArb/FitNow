package com.ianarbuckle.fitnow.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ian Arbuckle on 15/02/2017.
 *
 */

public class ToastUtil implements Runnable {

  private final Context context;
  String message;

  public ToastUtil(Context context, String message) {
    this.context = context;
    this.message = message;
  }

  @Override
  public void run() {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }
}
