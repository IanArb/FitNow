package com.ianarbuckle.fitnowwatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public abstract class BaseActivity extends Activity {

  Unbinder unbinder;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initLayout();

    ButterKnife.bind(this);

    butterKnifeUnBinder();

    initPresenter();
  }


  private void butterKnifeUnBinder() {
    unbinder = ButterKnife.bind(this);
  }

  protected abstract void initLayout();

  protected abstract void initPresenter();


}
