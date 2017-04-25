package com.ianarbuckle.fitnow.walking.leaderboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ian Arbuckle on 25/04/2017.
 *
 */

public class WalkLeadersFragment extends BaseFragment {

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  LinearLayoutManager linearLayoutManager;

  DatabaseReference databaseReference;
  DatabaseReference childRef;

  FirebaseRecyclerAdapter<RunWalkModel, WalkLeadersViewHolder> adapter;

  public static Fragment newInstance() {
    return new WalkLeadersFragment();
  }

  @Override
  protected void initPresenter() {

  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_leaders, container, false);
    ButterKnife.bind(this, view);
    attachRecyclerView();
    return view;
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    databaseReference = FirebaseDatabase.getInstance().getReference();
    childRef = databaseReference.child(Constants.RESULTS_WALKING_REFERENCE);
    adapter = new WalkLeadersAdapter(RunWalkModel.class, R.layout.layout_leaders, WalkLeadersViewHolder.class, childRef, getContext());
    recyclerView.setAdapter(adapter);
  }
}
