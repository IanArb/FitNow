package com.ianarbuckle.fitnow.running;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BlankFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.home.HomeActivity;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class RunningPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, RunningPagerActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initToolbar();
    initTabLayout();
    initPager();
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_pager);
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.my_activity_title));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.leaderboard_title));
  }

  private void initPager() {
    final StartRunningAdapter adapter = new StartRunningAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    if(toolbar != null) {
      toolbar.setTitle("Running");
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    startActivity(HomeActivity.newIntent(getApplicationContext()));
  }

  private class StartRunningAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public StartRunningAdapter(FragmentManager fragmentManager, int numTabs) {
      super(fragmentManager);
      this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return MyActivityFragment.newInstance();
        case 1:
          return BlankFragment.newInstance();
        default:
          return BlankFragment.newInstance();
      }
    }

    @Override
    public int getCount() {
      return numTabs;
    }
  }

}
