package com.ianarbuckle.fitnow.walking.walkingtimer.results;

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
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.walking.walkingtimer.results.gallery.GalleryFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 14/03/2017.
 *
 */

public class ResultsPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, ResultsPagerActivity.class);
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

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Results"));
    tabLayout.addTab(tabLayout.newTab().setText("Media"));
  }

  private void initPager() {
    final ResultsAdapter adapter = new ResultsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class ResultsAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public ResultsAdapter(FragmentManager fragmentManager, int numOftabs) {
      super(fragmentManager);
      this.numOfTabs = numOftabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0 :
          return new ResultsFragment();
        case 1:
          return new GalleryFragment();
        default:
          return null;
      }
    }

    @Override
    public int getCount() {
      return numOfTabs;
    }
  }

}
