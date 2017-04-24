package com.ianarbuckle.fitnow.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.BlankActivity;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.running.RunningPagerActivity;
import com.ianarbuckle.fitnow.walking.WalkPagerActivity;

import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class HomeFragment extends BaseFragment {

  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  protected void initPresenter() {
  }

  @OnClick(R.id.runBtn)
  public void onRunClick() {
    startActivity(RunningPagerActivity.newIntent(getContext()));
  }

  @OnClick(R.id.cycleBtn)
  public void onCycleClick() {
    startActivity(BlankActivity.newIntent(getContext()));
  }

  @OnClick(R.id.walkBtn)
  public void onWalkClick() {
    startActivity(WalkPagerActivity.newIntent(getContext()));
  }

  @Override
  public boolean onBackPressed() {
    return true;
  }
}
