package com.ianarbuckle.fitnow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ianarbuckle.fitnow.authentication.AuthPagerActivity;
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

  @Nullable
  @BindView(R.id.nav_view)
  NavigationView navigationView;

  @Nullable
  @BindView(R.id.drawer_layout)
  DrawerLayout drawerLayout;

  @Nullable
  @BindView(R.id.profileImage)
  ImageView profileIv;

  @Nullable
  @BindView(R.id.displayName)
  TextView displayNameTv;

  Unbinder unbinder;

  ProgressDialog progressDialog;

  FirebaseUser firebaseUser;
  FirebaseAuth firebaseAuth;

  String userName;
  String photoUrl;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initToolbar();

    if (initUserProfile()) {
      return;
    }

    if(navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
    }
  }

  private boolean initUserProfile() {
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser == null) {
      startActivity(AuthPagerActivity.newIntent(this));
      finish();
      return true;
    } else {
      userName = firebaseUser.getDisplayName();
      if(displayNameTv != null) {
        displayNameTv.setText(userName);
      }
      if(firebaseUser.getPhotoUrl() != null) {
        photoUrl = firebaseUser.getPhotoUrl().toString();
        if(photoUrl != null && profileIv != null) {
          Glide.with(this).load(photoUrl).into(profileIv);
        }
      }
    }
    return false;
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

  public void showProgressDialog() {
    if(progressDialog == null) {
      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage(getString(R.string.app_name));
      progressDialog.setIndeterminate(true);
    }
    progressDialog.show();
  }

  public void hideProgressDialog() {
    if(progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
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

    if(drawerLayout != null) {
      drawerLayout.closeDrawer(GravityCompat.START);
    }

    return true;
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
