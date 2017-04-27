package com.ianarbuckle.fitnow.bike.leaderboard;

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
import com.ianarbuckle.fitnow.models.BikeModel;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class BikeLeadersFragment extends BaseFragment implements BikeLeadersView {

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.btnSpeed)
  Button btnSpeed;

  @BindView(R.id.btnSteps)
  Button btnSteps;

  @BindView(R.id.btnDistance)
  Button btnDistance;

  LinearLayoutManager linearLayoutManager;

  BikeLeadersPresenterImpl presenter;

  public static Fragment newInstance() {
    return new BikeLeadersFragment();
  }

  @Override
  protected void initPresenter() {
    presenter = new BikeLeadersPresenterImpl(this);
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.setSpeedQuery();
    attachRecyclerView();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_leaders, container, false);
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
  }

  @OnClick(R.id.btnSpeed)
  public void onSpeedClick() {
    presenter.setSpeedQuery();
  }

  @OnClick(R.id.btnDistance)
  public void onDistanceClick() {
    presenter.setDistanceQuery();
  }

  @OnClick(R.id.btnSteps)
  public void onPedalClick() {
    presenter.setPedalQuery();
  }

  @Override
  public void setAdapter(FirebaseRecyclerAdapter<BikeModel, BikeLeadersViewHolder> adapter) {
    recyclerView.setAdapter(adapter);
  }
}
