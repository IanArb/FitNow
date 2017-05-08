package com.ianarbuckle.fitnow;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ianarbuckle.fitnow.home.HomeActivity;
import com.ianarbuckle.fitnow.utils.CircleTransform;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.UiUtils;
import com.ianarbuckle.fitnow.activities.walking.walkingtimer.WalkRecordingActivity;

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

  @Nullable
  @BindView(R.id.nav_view)
  protected NavigationView navigationView;

  @Nullable
  @BindView(R.id.drawer_layout)
  protected DrawerLayout drawerLayout;


  Unbinder unbinder;

  ProgressDialog progressDialog;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initToolbar();

    initNav();
  }

  private void initNav() {
    if (navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
      View headerView = navigationView.getHeaderView(0);

      TextView nameTv = (TextView) headerView.findViewById(R.id.displayName);
      TextView emailTv = (TextView) headerView.findViewById(R.id.email);
      ImageView imageView = (ImageView) headerView.findViewById(R.id.img_profile);

      setHeaderValues(headerView, nameTv, emailTv, imageView);

    }
  }

  private void setHeaderValues(View headerView, TextView nameTv, TextView emailTv, ImageView imageView) {
    SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
    if(sharedPreferences != null) {
      String name = sharedPreferences.getString(Constants.NAME_KEY, Constants.DEFAULT_KEY);
      String email = sharedPreferences.getString(Constants.EMAIL_KEY, Constants.DEFAULT_KEY);
      String photo = sharedPreferences.getString(Constants.PHOTO_KEY, Constants.DEFAULT_KEY);

      nameTv.setText(name);
      emailTv.setText(email);

      Glide.with(getApplicationContext()).load(photo)
          .crossFade()
          .thumbnail(0.5f)
          .bitmapTransform(new CircleTransform(getApplicationContext()))
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(imageView);
    }

    ImageView bgImage = (ImageView) headerView.findViewById(R.id.img_header_bg);

    Glide.with(getApplicationContext()).load(Constants.HEADER_URL)
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(bgImage);
  }

  protected abstract void initLayout();

  private void butterKnifeUnBinder() {
    unbinder = ButterKnife.bind(this);
  }

  protected void initToolbar() {
    if (toolbar != null) {
      UiUtils.customiseToolbar(toolbar);
      UiUtils.colourAndStyleActionBar(toolbar);
      setSupportActionBar(toolbar);
      if (getSupportActionBar() != null) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(UiUtils.colourAndStyleActionBar(toolbar));
      }
    }
  }

  public void hideProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();

    switch (itemId) {
      case R.id.nav_home:
        startActivity(HomeActivity.newIntent(getApplicationContext()));
        break;
      case R.id.nav_running:
        startActivity(BlankActivity.newIntent(getApplicationContext()));
        finish();
        break;
      case R.id.nav_bike:
        startActivity(BlankActivity.newIntent(getApplicationContext()));
        break;
      case R.id.nav_walk:
        startActivity(WalkRecordingActivity.newIntent(getApplicationContext()));
        break;
      case R.id.nav_help:
        startActivity(BlankActivity.newIntent(getApplicationContext()));
        break;
    }

    if (drawerLayout != null) {
      drawerLayout.closeDrawer(GravityCompat.START);
    }

    if (item.isChecked()) {
      item.setChecked(false);
    } else {
      item.setChecked(true);
    }
    item.setChecked(true);

    return true;
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    if(drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawers();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @Override
  protected void onStop() {
    super.onStop();
    hideProgressDialog();
  }
}
