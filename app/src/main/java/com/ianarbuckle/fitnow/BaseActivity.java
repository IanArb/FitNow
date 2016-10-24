package com.ianarbuckle.fitnow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ianarbuckle.fitnow.home.HomeActivity;
import com.ianarbuckle.fitnow.utility.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  @Nullable
  @BindView(R.id.toolbar)
  protected Toolbar toolbar;

  @BindView(R.id.nav_view)
  NavigationView navigationView;

  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  Unbinder unbinder;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initToolbar();

    navigationView.setNavigationItemSelectedListener(this);
  }

  protected abstract void initLayout();

  private void butterKnifeUnBinder() {
    unbinder = ButterKnife.bind(this);
  }

  protected void initToolbar() {
    if(toolbar != null) {
      UiUtils.customiseToolbar(toolbar);
      UiUtils.colourAndStyleActionBar(toolbar);
      setSupportActionBar(toolbar);
      if(getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(UiUtils.colourAndStyleActionBar(toolbar));
      }
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    switch(itemId) {
      case R.id.nav_home:
        startActivity(HomeActivity.newIntent(this));
        finish();
        break;
      case R.id.nav_running:
        startActivity(BlankActivity.newIntent(this));
        finish();
        break;
      case R.id.nav_bike:
        startActivity(BlankActivity.newIntent(this));
        finish();
        break;
      case R.id.nav_walk:
        startActivity(BlankActivity.newIntent(this));
        finish();
        break;
      case R.id.nav_help:
        startActivity(BlankActivity.newIntent(this));
        finish();
        break;
    }
    drawerLayout.closeDrawer(GravityCompat.START);

    return true;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }
}
