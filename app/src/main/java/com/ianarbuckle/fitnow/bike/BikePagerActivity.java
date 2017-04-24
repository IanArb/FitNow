package com.ianarbuckle.fitnow.bike;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BlankFragment;
import com.ianarbuckle.fitnow.R;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikePagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, BikePagerActivity.class);
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

  @Override
  protected void initToolbar() {
    super.initToolbar();
    assert toolbar != null;
    toolbar.setTitle("Cycling");
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.my_activity_title));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.leaderboard_title));
  }

  private void initPager() {
    final BikeMyActivityAdapter adapter = new BikeMyActivityAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class BikeMyActivityAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public BikeMyActivityAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return BikeMyActivityFragment.newInstance();
        case 1:
          return BlankFragment.newInstance();
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
