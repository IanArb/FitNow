package com.ianarbuckle.fitnow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public abstract class BaseFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

  Unbinder unbinder;

  protected ProgressDialog progressDialog;

  protected GoogleApiClient googleApiClient;

  protected static final int RC_SIGN_IN = 9001;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ButterKnife.bind(this, view);

    butterKnifeUnbinder(view);

    initPresenter();
  }


  private void butterKnifeUnbinder(View view) {
    unbinder = ButterKnife.bind(this, view);
  }

  protected abstract void initPresenter();

  public static void switchFragment(FragmentManager fragmentManager, Fragment fragment, String tag, boolean addToBackStack) {
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.vgContentFrame, fragment);
    if(addToBackStack) {
      fragmentTransaction.addToBackStack(tag);
    }
    fragmentTransaction.commit();
  }

  public void showProgressDialog() {
    if(progressDialog == null) {
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage(getString(R.string.common_loading));
    }
    progressDialog.show();
  }

  public void hideProgressDialog() {
    if(progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  protected void initGoogleSignIn() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    googleApiClient = new GoogleApiClient.Builder(getActivity())
        .enableAutoManage(getActivity(), this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  protected void signIn() {
    showProgressDialog();
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(getContext(), "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
    Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @Override
  public void onStop() {
    super.onStop();
    hideProgressDialog();
  }

  public boolean onBackPressed() {
    return false;
  }
}
