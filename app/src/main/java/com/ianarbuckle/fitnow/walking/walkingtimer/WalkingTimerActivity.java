package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkingTimerActivity extends BaseActivity {

  public static final String WALK_TIMER_FRAGMENT = "timerFragment";

  public static Intent newIntent(Context context) {
    return new Intent(context, WalkingTimerActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initFragment();
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(WALK_TIMER_FRAGMENT) != null) {
      return;
    }
    BaseFragment.switchFragment(fragmentManager, WalkingTimerFragment.newiInstance(), WALK_TIMER_FRAGMENT, false);

  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }
}
