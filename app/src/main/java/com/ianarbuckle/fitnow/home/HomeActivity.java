package com.ianarbuckle.fitnow.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class HomeActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initFragment(HomeFragment.newInstance());
  }

  private void initFragment(Fragment fragment) {
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.vgContentFrame, fragment)
        .commit();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_nav_drawer_home);
  }

}
