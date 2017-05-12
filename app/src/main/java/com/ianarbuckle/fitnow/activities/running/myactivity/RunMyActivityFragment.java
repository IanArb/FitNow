package com.ianarbuckle.fitnow.activities.running.myactivity;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.activities.running.runningtimer.RunRecordingActivity;
import com.ianarbuckle.fitnow.models.RunWalkModel;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class RunMyActivityFragment extends BaseFragment implements RunMyActivityView {

  @BindView(R.id.recyclerView)
  RecyclerView recyclerView;

  @BindView(R.id.rlEmptyMessage)
  RelativeLayout rlEmptyMessage;

  RunMyActivityPresenterImpl presenter;


  LinearLayoutManager linearLayoutManager;

  DatabaseReference databaseReference;


  public static Fragment newInstance() {
    return new RunMyActivityFragment();
  }

  @Override
  public void onStart() {
    super.onStart();
    presenter.setMyActivityAdapter();
    presenter.setEmptyState();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_activity, container, false);
    ButterKnife.bind(this, view);
    attachRecyclerView();
    return view;
  }

  @Override
  protected void initPresenter() {
    presenter = new RunMyActivityPresenterImpl(this);
  }

  @OnClick(R.id.fab)
  public void onFabClick() {
    startActivity(RunRecordingActivity.newIntent(getContext()));
  }

  private void attachRecyclerView() {
    recyclerView.setHasFixedSize(true);
    linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    databaseReference = FirebaseDatabase.getInstance().getReference();
  }

  @Override
  public void setEmptyView() {
    rlEmptyMessage.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.GONE);
  }

  @Override
  public void setListView() {
    rlEmptyMessage.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void setAdapter(FirebaseRecyclerAdapter<RunWalkModel, RunMyActivityCardView> adapter) {
    recyclerView.setAdapter(adapter);
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
}
