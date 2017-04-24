package com.ianarbuckle.fitnow.running.results;

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
import android.view.View;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BlankFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.gallery.GalleryFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 24/04/2017.
 *
 */

public class RunResultsPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, RunResultsPagerActivity.class);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initToolbar();

    initTabLayout();

    initPager();

    if(navigationView != null) {
      navigationView.setVisibility(View.GONE);
    }
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
    tabLayout.addTab(tabLayout.newTab().setText(R.string.gallery_tab_title));
  }

  private void initPager() {
    final RunResultsAdapter adapter = new RunResultsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private class RunResultsAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public RunResultsAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return RunResultsFragment.newInstance();
        case 1:
          return GalleryFragment.newInstance();
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
