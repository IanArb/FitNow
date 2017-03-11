package com.ianarbuckle.fitnow.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.home.HomeActivity;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public class LoginFragment extends BaseFragment implements LoginView, GoogleApiClient.OnConnectionFailedListener {

  @BindView(R.id.tilEmail)
  TextInputLayout tilEmail;

  @BindView(R.id.tilPassword)
  TextInputLayout tilPassword;

  protected static final int RC_SIGN_IN = 9001;

  private GoogleApiClient googleApiClient;

  LoginPresenterImpl presenter;

  public static Fragment newInstance() {
    return new LoginFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initGoogleSignIn();
  }

  @Override
  protected void initPresenter() {
    presenter = new LoginPresenterImpl(FitNowApplication.getAppInstance().getAuthenticationHelper());
    presenter.setView(this);
  }

  protected synchronized void initGoogleSignIn() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    googleApiClient = new GoogleApiClient.Builder(getActivity())
        .enableAutoManage(getActivity(), this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInIntent(requestCode, data);
  }

  private void signInIntent(int requestCode, Intent data) {
    if(requestCode == RC_SIGN_IN) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      if(result.isSuccess()) {
        GoogleSignInAccount account = result.getSignInAccount();
        firebaseAuthWithGoogle(account);
      } else {
        hideProgressDialog();
        FragmentTransaction fragmentTransaction = initFragmentManager();
        DialogFragment errorDialogFragment = ErrorDialogFragment.newInstance(R.string.google_sign_in_failed);
        errorDialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
      }
    }
  }

  @NonNull
  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  private void signIn() {
    showProgressDialog();
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
    presenter.firebaseAuthWithGoogle(account);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(getContext(), "onConnectionFailed" + connectionResult, Toast.LENGTH_SHORT).show();
    Toast.makeText(getContext(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSuccess() {
    String userName = presenter.getUserDisplayName();
    Toast.makeText(getContext(), "Welcome " + userName + "!", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onFailure() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_unsuccess);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @Override
  public void onLogin() {
    presenter.setSharedPreferences();
    startActivity(HomeActivity.newIntent(getContext()));
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @OnClick(R.id.tvAnnoymous)
  public void onGuestClick() {
    startActivity(HomeActivity.newIntent(getContext()));
  }

  @OnClick(R.id.ibGoogle)
  public void signInGoogleClick() {
    signIn();
  }

  @OnClick(R.id.btnLogin)
  public void signInClick() {
    if(tilEmail.getEditText() != null && tilPassword.getEditText() != null) {
      String email = tilEmail.getEditText().getText().toString();
      String password = tilPassword.getEditText().getText().toString();
      presenter.logInUser(email, password);
    }
  }

  @Override
  public void showErrorEmail() {
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.common_email_error_invalid));
  }

  @Override
  public void showErrorPassword() {
    tilPassword.setErrorEnabled(true);
    tilPassword.setError(getString(R.string.common_invalid_password));
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

}
