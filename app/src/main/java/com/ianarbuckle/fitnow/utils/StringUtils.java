package com.ianarbuckle.fitnow.utils;

import java.util.Locale;

/**
 * Created by Ian Arbuckle on 04/12/2016.
 *
 */

public class StringUtils {

  public static boolean isStringEmptyorNull(String... strings) {
    for(String current : strings) {
      if(current == null || current.isEmpty() || current.trim().isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static String formatDistance(double distance) {
    String unit = " km";
    if (distance < 1) {
      distance *= 1000;
      unit = " mm";
    } else if (distance > 1000) {
      distance /= 1000;
      unit = " km";
    }

    return String.format(Locale.ENGLISH, "%4.3f%s", distance, unit);
  }

  public static String formatSpeed(float speed) {
    String unit = " m/s";
    return String.format(Locale.ENGLISH, "%.2f%s", speed, unit);
  }

}
