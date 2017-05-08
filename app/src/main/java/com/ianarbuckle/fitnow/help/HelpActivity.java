package com.ianarbuckle.fitnow.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.ianarbuckle.fitnow.BaseActivity;
import com.ianarbuckle.fitnow.home.HomeActivity;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public class HelpActivity extends BaseActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void initLayout() {

  }

  @Override
  protected void initToolbar() {
    super.initToolbar();
    if(toolbar != null) {
      toolbar.setTitle("Help");
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
}
