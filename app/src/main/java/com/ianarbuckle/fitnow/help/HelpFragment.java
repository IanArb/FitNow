package com.ianarbuckle.fitnow.help;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;

/**
 * Created by Ian Arbuckle on 08/05/2017.
 *
 */

public class HelpFragment extends BaseFragment {

  public Fragment newInstance() {
    return new HelpFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.help_fragment, container, false);
  }

  @Override
  protected void initPresenter() {

  }
}
