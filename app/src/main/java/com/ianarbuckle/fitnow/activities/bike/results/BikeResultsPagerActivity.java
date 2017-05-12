package com.ianarbuckle.fitnow.activities.bike.results;

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
import com.ianarbuckle.fitnow.activities.bike.gallery.BikeGalleryFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class BikeResultsPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, BikeResultsPagerActivity.class);
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
    if(toolbar != null) {
      toolbar.setTitle(R.string.running_title);
    }
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.results_tab_title));
    tabLayout.addTab(tabLayout.newTab().setText("Map"));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.gallery_tab_title));
  }

  private void initPager() {
    final BikeResultsAdapter adapter = new BikeResultsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class BikeResultsAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public BikeResultsAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return BikeResultsFragment.newInstance();
        case 1:
          return BikeResultsMapFragment.newInstance();
        case 2:
          return BikeGalleryFragment.newInstance();
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
