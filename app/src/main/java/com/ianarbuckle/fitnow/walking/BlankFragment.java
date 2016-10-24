package com.ianarbuckle.fitnow.walking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 24/10/2016.
 *
 */

public class BlankFragment extends BaseFragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.activity_blank, container, false);
  }

  @Override
  protected void initPresenter() {

  }
}
