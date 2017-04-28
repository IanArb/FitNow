package com.ianarbuckle.fitnow.activities.running.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.RunWalkModel;

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

  @Override
  public void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, RunLeadersViewHolder> adapter) {
    recyclerView.setAdapter(adapter);
  }
}
