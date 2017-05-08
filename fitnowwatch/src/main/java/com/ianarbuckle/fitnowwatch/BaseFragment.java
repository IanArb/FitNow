package com.ianarbuckle.fitnowwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public abstract class BaseFragment extends Fragment {

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  protected abstract void initPresenter();
}
