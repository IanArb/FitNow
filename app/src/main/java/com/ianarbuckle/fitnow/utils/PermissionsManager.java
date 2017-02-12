package com.ianarbuckle.fitnow.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


public class PermissionsManager {

  public static boolean checkPermission(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isLocationPermissionGranted(Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isCameraPermissionGranted(Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean shouldShowRationale(Fragment fragment, String permission) {
      return fragment.shouldShowRequestPermissionRationale(permission);
  }

  public static void requestPermissions(Fragment fragment, int requestCode, String... permissions) {
    fragment.requestPermissions(permissions, requestCode);
  }

}
