package com.ianarbuckle.fitnow.activities.walking.myactivity.learnmore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.BlankFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.activities.walking.gallery.WalkGalleryFragment;
import com.ianarbuckle.fitnow.utils.Constants;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 02/05/2017.
 *
 */

public class LearnMorePagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  public static Intent newIntent(Context context) {
    return new Intent(context, LearnMorePagerActivity.class);
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
    assert toolbar != null;
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    String username = bundle.getString(Constants.NAME_KEY);
    toolbar.setTitle(username);
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText("Info"));
    tabLayout.addTab(tabLayout.newTab().setText("Gallery"));
  }

  private void initPager() {
    final LearnMorePagerActivityAdapter adapter = new LearnMorePagerActivityAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class LearnMorePagerActivityAdapter extends FragmentPagerAdapter {
    int numOfTabs;

    public LearnMorePagerActivityAdapter(FragmentManager fragmentManager, int numOfTabs) {
      super(fragmentManager);
      this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
        case 0:
          return LearnMoreInfoFragment.newInstance();
        case 1:
          return WalkGalleryFragment.newInstance();
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
