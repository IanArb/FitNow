package com.ianarbuckle.fitnowwatch.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnowwatch.BaseFragment;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public class HomeFragment extends BaseFragment {

  public static Fragment newInstance() {
    return new HomeFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  protected void initPresenter() {

  }
}
