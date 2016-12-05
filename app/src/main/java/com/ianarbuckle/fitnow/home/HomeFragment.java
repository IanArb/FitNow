package com.ianarbuckle.fitnow.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.BlankActivity;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.authentication.AuthPagerActivity;
import com.ianarbuckle.fitnow.authentication.AuthPresenterImpl;
import com.ianarbuckle.fitnow.walking.StartWalkPagerActivity;

import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class HomeFragment extends BaseFragment {

  FirebaseUser firebaseUser;
  FirebaseAuth firebaseAuth;

  AuthPresenterImpl presenter;

  String userName;
  String photoUrl;

  public static HomeFragment newInstance() {
    return new HomeFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_home, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = firebaseAuth.getCurrentUser();
    if(firebaseUser == null) {
      startActivity(AuthPagerActivity.newIntent(getContext()));
      getActivity().finish();
      return;
    } else {
      userName = firebaseUser.getDisplayName();
      if(firebaseUser.getPhotoUrl() != null) {
        photoUrl = firebaseUser.getPhotoUrl().toString();
      }
    }

  }

  @Override
  protected void initPresenter() {
    presenter = new AuthPresenterImpl(FitNowApplication.getAppInstance().getAuthenticationHelper());
  }

  @OnClick(R.id.runBtn)
  public void onRunClick() {
    startActivity(BlankActivity.newIntent(getContext()));
  }

  @OnClick(R.id.cycleBtn)
  public void onCycleClick() {
    startActivity(BlankActivity.newIntent(getContext()));
  }

  @OnClick(R.id.walkBtn)
  public void onWalkClick() {
    startActivity(StartWalkPagerActivity.newIntent(getContext()));
  }

}
