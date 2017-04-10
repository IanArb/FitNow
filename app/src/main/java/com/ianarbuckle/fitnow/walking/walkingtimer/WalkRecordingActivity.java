package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkRecordingActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, WalkRecordingActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFragment();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(Constants.WALK_TIMER_FRAGMENT) != null) {
      return;
    }
    BaseFragment.switchFragment(fragmentManager, WalkRecordingFragment.newInstance(), Constants.WALK_TIMER_FRAGMENT, false);
  }
}
