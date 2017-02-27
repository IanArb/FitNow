package com.ianarbuckle.fitnow.walking.walkingtimer;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.utils.PermissionsManager;
import com.ianarbuckle.fitnow.utils.PopupFragment;
import com.ianarbuckle.fitnow.firebase.storage.FirebaseStorageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkRecordingFragment extends BaseFragment implements WalkRecordingView, FirebaseStorageView {

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

  @BindView(R.id.startButton)
  TextView tvStart;

  @BindView(R.id.controls)
  RelativeLayout rlControls;

  @BindView(R.id.startRl)
  RelativeLayout rlStart;

  @BindView(R.id.sensorsLayout)
  LinearLayout sensorsLayout;

  @BindView(R.id.rlTimer)
  RelativeLayout rlTimer;

  private WalkRecordingPresenterImpl presenter;

  public static Fragment newInstance() {
    return new WalkRecordingFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timer, container, false);
  }

  @TargetApi(Build.VERSION_CODES.M)
  @Override
  public void onStart() {
    super.onStart();
    presenter.checkLocationPermission(this);
    initMap();
  }

  @Override
  protected void initPresenter() {
    presenter = new WalkRecordingPresenterImpl(this, this);
  }

  @OnClick(R.id.startRl)
  public void onClickStart() {
    new CountDownTimer(3100, 1000) {
      public void onTick(long millisUntilFinished) {
        tvStart.setText(String.valueOf(millisUntilFinished / 1000));
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
    presenter.onActivityResult(requestCode, resultCode);
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

  private void checkCameraPermissions() {
    String accessCamera = android.Manifest.permission.CAMERA;
    String[] permissions = {accessCamera};
    if (PermissionsManager.checkPermission(getContext(), accessCamera)
        && shouldShowRequestPermissionRationale(accessCamera)) {
      PermissionsManager.requestPermissions(this, Constants.PERMISSION_REQUEST_CAMERA, permissions);
    } else if (PermissionsManager.isCameraPermissionGranted(getContext())) {
      startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
    }
  }

  private void timerSwitch() {
    if (presenter.isRunning()) {
      fabPause.setImageResource(R.drawable.ic_play_arrow);
      presenter.pauseTimer();
    } else {
      fabPause.setImageResource(R.drawable.ic_pause);
      presenter.resumeTimer();
    }
  }

  @OnClick(R.id.fabStop)
  public void onStopClick() {
    showPopupDialog();
  }

  private void showPopupDialog() {
    FragmentTransaction fragmentTransaction = initFragmentManager();
    DialogFragment dialogFragment = PopupFragment.newInstance(R.string.message_title_finish, R.string.message_subTitle_finish);
    dialogFragment.show(fragmentTransaction, Constants.TAG_STOP_FRAGMENT);
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
  public void setSuccessMessage() {
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
    tvSteps.setText(value);
  }

  @Override
  public void setTextSpeed(String value) {
    tvSpeed.setText(value);
  }

  @Override
  public void setTextDistance(String value) {
    tvDistance.setText(value);
  }

}