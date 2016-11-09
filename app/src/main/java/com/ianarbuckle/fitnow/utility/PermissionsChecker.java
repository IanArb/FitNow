package com.ianarbuckle.fitnow.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public abstract class PermissionsChecker {

  public static boolean checkPermissions(Context context, String... permissions) {
    for(String permission : permissions) {
      if(!checkPermission(context, permission)) {
        return false;
      }
    }
    return true;
  }

  public static boolean checkPermission(Context context, String permission) {
    return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
  }

  public static boolean isDeviceLocationGranted(Context context) {
    return checkPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION);
  }

  public static void requestPermissions(String[] permissions, int permissionId) {
    requestPermissions(permissions, permissionId);
  }
}
