package com.ianarbuckle.fitnow.authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.authentication.login.LoginFragment;
import com.ianarbuckle.fitnow.authentication.register.RegisterFragment;

import butterknife.BindView;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public class AuthPagerActivity extends BaseActivity {

  @BindView(R.id.viewpager)
  ViewPager viewPager;

  @BindView(R.id.tabs)
  TabLayout tabLayout;

  @Override
  protected void initLayout() {
    setContentView(R.layout.layout_tabs);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initTabLayout();

    initPager();
  }

  private void initTabLayout() {
    tabLayout.addTab(tabLayout.newTab().setText(R.string.login_tab_title));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.register_tab_title));
  }

  private void initPager() {
    final AuthPagerAdapter adapter = new AuthPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

  private class AuthPagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public AuthPagerAdapter(FragmentManager fragmentManager, int numTabs) {
      super(fragmentManager);
      this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
      switch(position) {
        case 0 :
          return LoginFragment.newInstance();
        case 1 :
          return RegisterFragment.newInstance();
        default :
          return null;
      }
    }

    @Override
    public int getCount() {
      return numTabs;
    }

  }
}
