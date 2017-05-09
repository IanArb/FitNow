package com.ianarbuckle.fitnowwatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ianarbuckle.fitnowwatch.authentication.LoginActivity;

/**
 * Created by Ian Arbuckle on 09/05/2017.
 *
 */

public class SplashActivity extends Activity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }
}
