package com.ianarbuckle.fitnow.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class HomeActivity extends BaseActivity {

  public static final String TAG_HOME_FRAGMENT = "homeFragment";

  public static Intent newIntent(Context context) {
    return new Intent(context, HomeActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initFragment();
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(TAG_HOME_FRAGMENT) != null) {
      return;
    }

    BaseFragment.switchFragment(getSupportFragmentManager(), HomeFragment.newInstance(), TAG_HOME_FRAGMENT, false);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_nav_drawer_home);
  }

}
