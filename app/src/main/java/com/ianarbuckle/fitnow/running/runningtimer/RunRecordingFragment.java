package com.ianarbuckle.fitnow.running.runningtimer;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.FitNowApplication;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.running.results.RunResultsPagerActivity;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.utils.PermissionsManager;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class RunRecordingFragment extends BaseFragment implements RunRecordingView {

  @BindView(R.id.tvTimer)
  TextView tvTimer;

  @BindView(R.id.fabPause)
  FloatingActionButton fabPause;

  @BindView(R.id.tvSpeed)
  TextView tvSpeed;

  @BindView(R.id.tvSteps)
  TextView tvSteps;

  @BindView(R.id.tvDistance)
  TextView tvDistance;

  @BindView(R.id.tvCalories)
  TextView tvCalories;

  @BindView(R.id.startButton)
  TextView tvStart;

  @BindView(R.id.controls)
  RelativeLayout rlControls;

  @BindView(R.id.fabLock)
  FloatingActionButton fabLock;

  @BindView(R.id.fabLockOpen)
  FloatingActionButton fabLockOpen;

  @BindView(R.id.fabCamera)
  FloatingActionButton fabCamera;

  @BindView(R.id.fabStop)
  FloatingActionButton fabStop;

  @BindView(R.id.startRl)
  RelativeLayout rlStart;

  @BindView(R.id.sensorsLayout)
  RelativeLayout sensorsLayout;

  @BindView(R.id.rlTimer)
  RelativeLayout rlTimer;

  private RunRecordingPresenterImpl presenter;

  public static Fragment newInstance() {
    return new RunRecordingFragment();
  }

  @TargetApi(Build.VERSION_CODES.M)
  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timer, container, false);
  }

  @Override
  protected void initPresenter() {
    AuthenticationHelper authenticationHelper = FitNowApplication.getAppInstance().getAuthenticationHelper();
    presenter = new RunRecordingPresenterImpl(this, authenticationHelper);
  }

  @OnClick(R.id.startRl)
  public void onClickStart() {
    int millisInFuture = getContext().getResources().getInteger(R.integer.millisInFuture);
    final int countInterval = getContext().getResources().getInteger(R.integer.countInterval);
    new CountDownTimer(millisInFuture, countInterval) {
      public void onTick(long millisUntilFinished) {
        tvStart.setText(String.valueOf(millisUntilFinished / countInterval));
      }

      public void onFinish() {
        presenter.initGoogleClient();
        presenter.startTimer();
        rlTimer.setVisibility(View.VISIBLE);
        rlControls.setVisibility(View.VISIBLE);
        sensorsLayout.setVisibility(View.VISIBLE);
        rlStart.setVisibility(View.GONE);
      }
    }.start();
  }

  private void initMap() {
    SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
        .findFragmentById(R.id.fragment_map);

    supportMapFragment = initFragment(supportMapFragment);

    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
        presenter.initMap(googleMap);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
      }
    });
  }

  @NonNull
  private SupportMapFragment initFragment(SupportMapFragment supportMapFragment) {
    if (supportMapFragment == null) {
      FragmentManager fragmentManager = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      supportMapFragment = SupportMapFragment.newInstance();
      fragmentTransaction.replace(R.id.fragment_map, supportMapFragment).commit();
    }
    return supportMapFragment;
  }

  @Override
  public void setTimerText(String result) {
    tvTimer.setText(result);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == Constants.PERMISSION_REQUEST_CAMERA && resultCode == RESULT_OK) {
      presenter.onActivityResult(requestCode, resultCode);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case Constants.PERMISSION_REQUEST_ACCESS_LOCATION: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          presenter.onRequestPermission();
        }
      }
      break;
      case Constants.PERMISSION_REQUEST_CAMERA: {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
        }
      }
    }
  }

  @OnClick(R.id.fabPause)
  public void onPauseClick() {
    timerSwitch();
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @OnClick(R.id.fabCamera)
  public void onCameraClick() {
    checkCameraPermissions();
    presenter.pauseTimer();
  }

  @OnClick(R.id.fabLock)
  public void onLockClick() {
    fabLockOpen.setVisibility(View.VISIBLE);
    fabLock.setVisibility(View.GONE);
    fabPause.setVisibility(View.GONE);
    fabCamera.setVisibility(View.GONE);
    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  @OnClick(R.id.fabLockOpen)
  public void onOpenLockClick() {
    fabLockOpen.setVisibility(View.GONE);
    fabLock.setVisibility(View.VISIBLE);
    fabPause.setVisibility(View.VISIBLE);
    fabCamera.setVisibility(View.VISIBLE);
    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  private void checkCameraPermissions() {
    String accessCamera = android.Manifest.permission.CAMERA;
    String[] permissions = {accessCamera};
    if (PermissionsManager.checkPermission(getContext(), accessCamera)) {
      PermissionsManager.requestPermissions(this, Constants.PERMISSION_REQUEST_CAMERA, permissions);
    } else if (PermissionsManager.isCameraPermissionGranted(getContext())) {
      startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
    }
  }

  private void timerSwitch() {
    if (presenter.isRunning()) {
      fabPause.setImageResource(R.drawable.ic_play_arrow);
      presenter.pauseTimer();
      fabLock.setVisibility(View.GONE);
      fabStop.setVisibility(View.VISIBLE);
    } else {
      fabPause.setImageResource(R.drawable.ic_pause);
      fabLock.setVisibility(View.VISIBLE);
      fabStop.setVisibility(View.GONE);
      presenter.resumeTimer();
    }
  }

  @OnClick(R.id.fabStop)
  public void onStopClick() {
    Intent intent = RunResultsPagerActivity.newIntent(getContext());
    intent.putExtras(presenter.setBundle());
    intent.putExtras(presenter.setTimeBundle());
    startActivity(intent);
  }

  @NonNull
  private FragmentTransaction initFragmentManager() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    Fragment fragment = getFragmentManager().findFragmentByTag(Constants.TAG_STOP_FRAGMENT);

    if (fragment != null) {
      fragmentTransaction.remove(fragment);
    }

    fragmentTransaction.addToBackStack(null);
    return fragmentTransaction;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    presenter.stopTimer();
    presenter.disconnectGoogleClient();
  }

  @Override
  public void onPause() {
    super.onPause();
    presenter.disconnectGoogleClient();
  }

  @Override
  public void showLoading() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage(getString(R.string.message_uploading));
    }
    progressDialog.show();
  }

  @Override
  public void dismissLoading() {
    hideProgressDialog();
  }

  @Override
  public void showErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_camera_error);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @Override
  public void showSuccessMessage() {
    Toast.makeText(getContext(), R.string.message_upload_success, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void showStorageErrorMessage() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = ErrorDialogFragment.newInstance(R.string.message_upload_failure);
    dialogFragment.show(fragmentTransaction, Constants.ERROR_DIALOG_FRAGMENT);
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.disconnectGoogleClient();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    presenter.onSaveInstanceState(outState);
  }

  @Override
  public void setTextSteps(String value) {
    assert value != null;
    tvSteps.setText(value);
  }

  @Override
  public void setTextSpeed(String value) {
    assert value != null;
    tvSpeed.setText(value);
  }

  @Override
  public void setTextDistance(String value) {
    assert value != null;
    tvDistance.setText(value);
  }

  @Override
  public void setCaloriesText(String value) {
    assert value != null;
    tvCalories.setText(value);
  }
}
