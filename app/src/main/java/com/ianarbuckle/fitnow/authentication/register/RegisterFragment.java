package com.ianarbuckle.fitnow.authentication.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class RegisterFragment extends BaseFragment implements RegisterView {

  @BindView(R.id.tilEmail)
  TextInputLayout tilEmail;

  @BindView(R.id.tilUsername)
  TextInputLayout tilUsername;

  @BindView(R.id.tilPassword)
  TextInputLayout tilPassword;

  @BindView(R.id.tilConfirmPassword)
  TextInputLayout tilConfirmPassword;

  private RegisterPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RegisterFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_register, container, false);
  }

  @Override
  protected void initPresenter() {
    presenter = new RegisterPresenterImpl(FitNowApplication.getAppInstance().getAuthenticationHelper());
    presenter.setView(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    hideProgressDialog();
  }

  @OnClick(R.id.btnRegister)
  public void onRegisterClick() {
    if(tilEmail.getEditText() != null && tilUsername.getEditText() != null &&
        tilPassword.getEditText() != null && tilConfirmPassword.getEditText() != null) {
      String email = tilEmail.getEditText().getText().toString().trim();
      String username = tilUsername.getEditText().getText().toString().trim();
      String password = tilPassword.getEditText().getText().toString().trim();
      String confirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();
      presenter.validateEmail(email);
      presenter.validateUsername(username);
      presenter.validatePassword(password, confirmPassword);
    }
  }

  @Override
  public void showErrorMessage() {
    hideProgressDialog();
    tilConfirmPassword.setErrorEnabled(true);
    tilPassword.setErrorEnabled(true);
    tilConfirmPassword.setError(getString(R.string.common_invalid_password));
  }

  @Override
  public void showInvalidEmailMessage() {
    hideProgress();
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.common_email_error_invalid));
  }

  @Override
  public void onFailure() {
    hideProgress();
    showMessageDialog(R.string.message_unsuccess);
  }

  private void showMessageDialog(int message) {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if(fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);

    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @Override
  public void onSuccess() {
    Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onLogin() {
    startActivity(HomeActivity.newIntent(getContext()));
  }

  @Override
  public void hideProgress() {
    hideProgressDialog();
  }

  @Override
  public void showProgress() {
    showProgressDialog();
  }

  @Override
  public void registerOnPasswordMatch() {
    if(tilEmail.getEditText() != null && tilPassword.getEditText() != null) {
      String email = tilEmail.getEditText().getText().toString();
      String password = tilPassword.getEditText().getText().toString();
      presenter.registerAccount(email, password);
    }
  }

  @Override
  public void showEmailEmptyMessage() {
    tilEmail.setErrorEnabled(true);
    tilEmail.setError(getString(R.string.error_empty_message));
  }

  @Override
  public void showPasswordEmptyMessage() {
    tilPassword.setErrorEnabled(true);
    tilPassword.setError(getString(R.string.error_empty_message));
    tilConfirmPassword.setErrorEnabled(true);
    tilConfirmPassword.setError(getString(R.string.error_empty_message));
  }

  @Override
  public void showUsernameEmptyMessage() {
    tilUsername.setErrorEnabled(true);
    tilUsername.setError(getString(R.string.error_empty_message));
  }

  @Override
  public void showInvalidUsernameMessage() {
    tilUsername.setErrorEnabled(true);
    tilUsername.setError(getString(R.string.common_username_invalid));
  }
}
