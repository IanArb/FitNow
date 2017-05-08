package com.ianarbuckle.fitnowwatch.home;

import android.content.Context;
import android.content.Intent;

import com.ianarbuckle.fitnowwatch.BaseActivity;
import com.ianarbuckle.fitnowwatch.R;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public class HomeActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }
}
