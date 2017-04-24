package com.ianarbuckle.fitnow.bike.biketimer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeRecordingActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, BikeRecordingActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFragment();
    assert toolbar != null;
    toolbar.setVisibility(View.GONE);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(Constants.RUN_TIMER_FRAGMENT) != null) {
      return;
    }

    BaseFragment.switchFragment(fragmentManager, BikeRecordingFragment.newInstance(), Constants.RUN_TIMER_FRAGMENT, false);
  }


}
