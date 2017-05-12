package com.ianarbuckle.fitnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ianarbuckle.fitnow.authentication.AuthPagerActivity;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = new Intent(this, AuthPagerActivity.class);
    startActivity(intent);
    finish();
  }
}
