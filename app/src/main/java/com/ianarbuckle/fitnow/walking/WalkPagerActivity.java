package com.ianarbuckle.fitnow.walking;

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
import com.ianarbuckle.fitnow.walking.leaderboard.WalkLeadersFragment;
import com.ianarbuckle.fitnow.walking.myactivity.MyActivityFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/10/2016.
 *
 */

public class WalkPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, WalkPagerActivity.class);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_pager);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initToolbar();

    initTabLayout();

    initPager();
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.my_activity_title));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.leaderboard_title));
  }

  private void initPager() {
    final StartWalkAdapter adapter = new StartWalkAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
      toolbar.setTitle(R.string.walking_title);
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

  private class StartWalkAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public StartWalkAdapter(FragmentManager fragmentManager, int numOftabs) {
      super(fragmentManager);
      this.numOfTabs = numOftabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0 :
          return MyActivityFragment.newInstance();
        case 1:
          return WalkLeadersFragment.newInstance();
        default:
          return BlankFragment.newInstance();
      }
    }

    @Override
    public int getCount() {
      return numOfTabs;
    }
  }


}
