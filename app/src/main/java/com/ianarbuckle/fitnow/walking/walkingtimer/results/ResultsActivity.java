package com.ianarbuckle.fitnow.walking.walkingtimer.results;

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
 * Created by Ian Arbuckle on 06/03/2017.
 *
 */

public class ResultsActivity extends BaseActivity {

  public static Intent newIntent(Context context) {
    return new Intent(context, ResultsActivity.class);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_container);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initFragment();
    if(toolbar != null) {
      toolbar.setTitle("Walking - Results");
    }
  }

  private void initFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if(fragmentManager.findFragmentByTag(Constants.TAG_RESULTS_FRAGMENT) != null) {
      return;
    }
    BaseFragment.switchFragment(fragmentManager, ResultsFragment.newInstance(), Constants.TAG_RESULTS_FRAGMENT, false);
  }
}
