package com.ianarbuckle.fitnow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();
    ButterKnife.bind(this);
    butterKnifeUnBinder();
    initToolbar();
  }

  protected abstract void initLayout();

  private void butterKnifeUnBinder() {

  }

  protected void initToolbar() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
