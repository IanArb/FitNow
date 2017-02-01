package com.ianarbuckle.fitnow.walking.walkingtimer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.ianarbuckle.fitnow.BaseFragment;
import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;
import com.ianarbuckle.fitnow.utils.ErrorDialogFragment;
import com.ianarbuckle.fitnow.utils.PermissionsChecker;
import com.ianarbuckle.fitnow.utils.PopupFragment;
import com.ianarbuckle.fitnow.walking.walkingtimer.storage.FirebaseStorageHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ian Arbuckle on 03/11/2016.
 *
 */

public class WalkRecordingFragment extends BaseFragment implements WalkRecordingView {

  @BindView(R.id.tvTimer)
  TextView tvTimer;

  @BindView(R.id.fabPause)
  FloatingActionButton fabPause;

  private WalkRecordingPresenterImpl presenter;

  private FirebaseStorageHelper storageHelper;

  public static Fragment newInstance() {
    return new WalkRecordingFragment();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_timer, container, false);
  }

  @Override
  public void onStart() {
    super.onStart();
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      presenter.checkLocationPermission();
    }
    initMap();
  }

  @Override
  protected void initPresenter() {
    presenter = new WalkRecordingPresenterImpl(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    startTimer();
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

  private void startTimer() {
    presenter.startTimer();
  }

  @Override
  public void setTimerText(String result) {
    tvTimer.setText(result);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void checkCameraPermission() {
    String accessCamera = android.Manifest.permission.CAMERA;
    String[] permissions = {accessCamera};
    if (PermissionsChecker.checkPermission(getContext(), accessCamera) && shouldShowRequestPermissionRationale(accessCamera)) {
      PermissionsChecker.requestPermissions(permissions, Constants.PERMISSION_REQUEST_CAMERA);
    } else {
      presenter.takePicture();
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void takePicture() {
    startActivityForResult(presenter.takePicture(), Constants.PERMISSION_REQUEST_CAMERA);
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
          takePicture();
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
    checkCameraPermission();
    if (presenter.isRunning()) {
      presenter.pauseTimer();
    } else {
      presenter.resumeTimer();
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
  }

  @Override
  public void showLoading() {
    if(progressDialog == null) {
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
}