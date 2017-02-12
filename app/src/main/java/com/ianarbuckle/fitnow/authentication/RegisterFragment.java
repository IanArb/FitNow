package com.ianarbuckle.fitnow.authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class RegisterFragment extends BaseFragment implements AuthRegisterView {

  @BindView(R.id.etEmail)
  EditText etEmail;

  @BindView(R.id.tlEmail)
  TextInputLayout tlEmail;

  @BindView(R.id.tlConfirmPassword)
  TextInputLayout tlConfirmPassword;

  @BindView(R.id.tlPassword)
  TextInputLayout tlPassword;

  private AuthPresenterImpl presenter;

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
    presenter = new AuthPresenterImpl(FitNowApplication.getAppInstance().getAuthenticationHelper());
    presenter.setRegisterView(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    hideProgressDialog();
  }

  @OnClick(R.id.continueBtn)
  public void onRegisterClick() {
    if(tlEmail.getEditText() != null && tlPassword.getEditText() != null && tlConfirmPassword.getEditText() != null) {
      String email = tlPassword.getEditText().getText().toString().trim();
      String password = tlPassword.getEditText().getText().toString().trim();
      String confirmPassword = tlConfirmPassword.getEditText().getText().toString().trim();
      presenter.validateEmail(email);
      presenter.validatePassword(password, confirmPassword);
    }
  }

  @Override
  public void showErrorMessage() {
    hideProgressDialog();
    tlConfirmPassword.setErrorEnabled(true);
    tlPassword.setErrorEnabled(true);
    tlConfirmPassword.setError(getString(R.string.common_invalid_password));
  }

  @Override
  public void showInvalidEmailMessage() {
    hideProgress();
    tlEmail.setErrorEnabled(true);
    tlEmail.setError(getString(R.string.common_email_error_invalid));
  }

  @Override
  public void onFailure() {
    hideProgress();
    showErrorMessageDialog();
  }

  private void showErrorMessageDialog() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.ERROR_DIALOG_FRAGMENT);

    if(fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);

    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_unsuccess);
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
    if(tlEmail.getEditText() != null && tlPassword.getEditText() != null) {
      String email = tlEmail.getEditText().getText().toString();
      String password = tlPassword.getEditText().getText().toString();
      presenter.registerAccount(email, password);
    }
  }

  @Override
  public void showEmailEmptyMessage() {
    tlEmail.setErrorEnabled(true);
    tlEmail.setError(getString(R.string.error_empty_message));
  }

  @Override
  public void showPasswordEmptyMessage() {
    tlPassword.setErrorEnabled(true);
    tlPassword.setError(getString(R.string.error_empty_message));
    tlConfirmPassword.setErrorEnabled(true);
    tlConfirmPassword.setError(getString(R.string.error_empty_message));
  }
}
