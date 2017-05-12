package com.ianarbuckle.fitnow;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class BlankActivity extends BaseActivity {


  public static Intent newIntent(Context context) {
    return new Intent(context, BlankActivity.class);
  }

  @Override
  protected void initLayout() {
    setContentView(R.layout.activity_nav_drawer);
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
}
