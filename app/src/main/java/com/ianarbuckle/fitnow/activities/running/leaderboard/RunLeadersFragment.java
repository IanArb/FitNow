package com.ianarbuckle.fitnow.activities.running.leaderboard;

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
import android.widget.Button;
import android.widget.RelativeLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class RunLeadersFragment extends BaseFragment implements RunLeadersView {

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.btnSpeed)
  Button btnSpeed;

  @BindView(R.id.btnSteps)
  Button btnSteps;

  @BindView(R.id.btnDistance)
  Button btnDistance;

  @BindView(R.id.btnContainer)
  RelativeLayout rlBtnContainer;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  LinearLayoutManager linearLayoutManager;

  RunLeadersPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RunLeadersFragment();
  }

  @Override
  protected void initPresenter() {
    presenter = new RunLeadersPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    attachRecyclerView();
    presenter.setSpeedQuery(R.layout.layout_leaders);
    presenter.setEmptyState();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_leaders_runwalk, container, false);
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
  }

  @OnClick(R.id.btnSpeed)
  public void onSpeedClick() {
    presenter.setSpeedQuery(R.layout.layout_leaders);
  }

  @OnClick(R.id.btnDistance)
  public void onDistance() {
    presenter.setDistanceQuery(R.layout.layout_leaders);
  }

  @OnClick(R.id.btnSteps)
  public void onSteps() {
    presenter.setStepsQuery(R.layout.layout_leaders);
  }

  @OnClick(R.id.btnTime)
  public void onTimeClick() {
    presenter.setTimeQuery(R.layout.layout_leaders);
  }

  @Override
  public void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, RunLeadersViewHolder> adapter) {
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void showListView() {
    rlBtnContainer.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.VISIBLE);
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
  public void showEmptyView() {
    rlBtnContainer.setVisibility(View.GONE);
    recyclerView.setVisibility(View.GONE);
    rlEmptyMessage.setVisibility(View.VISIBLE);
  }
}
