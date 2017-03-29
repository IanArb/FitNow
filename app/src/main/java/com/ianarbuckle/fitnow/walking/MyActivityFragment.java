package com.ianarbuckle.fitnow.walking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.walking.walkingtimer.WalkRecordingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Ian Arbuckle on 24/10/2016.
 *
 */

public class MyActivityFragment extends BaseFragment implements MyActivityWalkView {

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  private MyActivityPresenterImpl presenter;
  MyActivityAdapter adapter;
  LinearLayoutManager linearLayoutManager;

  @Override
  protected void initPresenter() {
    presenter = new MyActivityPresenterImpl(this, FitNowApplication.getAppInstance().getDatabaseHelper());
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.retrieveWalkingResults();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_activity, container, false);
    ButterKnife.bind(this, view);
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(adapter);
    return view;
  }

  @OnClick(R.id.fab)
  public void onFabClick() {
    startActivity(WalkRecordingActivity.newIntent(getContext()));
  }

  @Override
  public boolean onBackPressed() {
    return true;
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment errorDialog = ErrorDialogFragment.newInstance(R.string.common_network_error);
    errorDialog.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.TAG_STOP_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void setAdapter(MyActivityAdapter adapter) {
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showEmptyMessage() {
    recyclerView.setVisibility(View.GONE);
    rlEmptyMessage.setVisibility(View.VISIBLE);
  }
}
