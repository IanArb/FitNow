package com.ianarbuckle.fitnow.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ianarbuckle.fitnow.utility.ErrorDialogFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 16/11/2016.
 *
 */

public class LoginFragment extends BaseFragment implements AuthLoginView, GoogleApiClient.OnConnectionFailedListener {

  @BindView(R.id.tilEmail)
  TextInputLayout tilEmail;

  @BindView(R.id.tilPassword)
  TextInputLayout tilPassword;

  private static final String TAG_ERROR_DIALOG = "ErrorDialog";
  protected static final int RC_SIGN_IN = 9001;

  private GoogleApiClient googleApiClient;

  AuthPresenterImpl presenter;

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
    presenter = new AuthPresenterImpl(FitNowApplication.getAppInstance().getAuthenticationHelper());
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
        Toast.makeText(getContext(), "Google Sign In Failed.", Toast.LENGTH_SHORT).show();
      }
    }
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
    showErrorMessageDialog();
  }

  private void showErrorMessageDialog() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(TAG_ERROR_DIALOG);

    if(fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);

    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_unsuccess);
    dialogFragment.show(fragmentTransaction, TAG_ERROR_DIALOG);
  }

  @Override
  public void onLogin() {
    String username = presenter.getUserDisplayName();
    String email = presenter.getUserEmail();
    String photoUrl = presenter.getUserPhoto();
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("name", username);
    editor.putString("email", email);
    editor.putString("photoUrl", photoUrl);
    editor.apply();
//    Intent intent = HomeActivity.newIntent(getContext());
//    intent.putExtra("username", username);
//    intent.putExtra("email", email);
//    intent.putExtra("photoUrl", photoUrl);
    startActivity(HomeActivity.newIntent(getContext()));
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @OnClick(R.id.guestBtn)
  public void onGuestClick() {
    startActivity(HomeActivity.newIntent(getContext()));
  }

  @OnClick(R.id.googleSignin)
  public void signInGoogleClick() {
    signIn();
  }

  @OnClick(R.id.loginBtn)
  public void signInClick() {
    String email = tilEmail.getEditText().getText().toString();
    String password = tilPassword.getEditText().getText().toString();
    presenter.logInUser(email, password);
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
