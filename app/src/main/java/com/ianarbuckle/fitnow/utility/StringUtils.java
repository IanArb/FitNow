package com.ianarbuckle.fitnow.utility;

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
}
