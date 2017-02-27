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

  public static String formatDistance(double meters) {
    String unit = " km";

    double kilometers;

    kilometers = meters * 0.001;

    return String.format(Locale.ENGLISH, "%4.3f%s", kilometers, unit);
  }

  public static String formatSpeed(float speed) {
    String unit = " m/s";
    return String.format(Locale.ENGLISH, "%.2f%s", speed, unit);
  }

}
