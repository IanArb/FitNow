package com.ianarbuckle.fitnow.utils;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.regex.Pattern;

/**
 * Created by Ian Arbuckle on 23/01/2017.
 *
 */

public class Constants {
  public static final int PERMISSION_REQUEST_ACCESS_LOCATION = 99;
  public static final int PERMISSION_REQUEST_CAMERA = 1;
  public static final int REQUEST_OAUTH = 2;

  public static final String RESULTS_WALKING_REFERENCE = "/results-walking";
  public static final String RESULTS_RUNNING_REFERENCE = "/results-running";
  public static final String RESULTS_CYCLING_REFERENCE = "/results-cycling";
  public static final String FIREBASE_STORAGE_DIR = "images";
  public static final String FIREBASE_DATABASE_UPLOAD_WALKING = "uploads-walking";
  public static final String FIREBASE_DATABASE_UPLOAD_RUNNING = "uploads-running";
  public static final String FIREBASE_DATABASE_UPLOAD_CYCLING = "uploading-cycling";
  public static final String FIREBASE_DATABASE_CHILD_SPEED = "speed";
  public static final String FIREBASE_DATABASE_CHILD_DISTANCE = "distance";
  public static final String FIREBASE_DATABASE_CHILD_STEPS = "steps";
  public static final String FIREBASE_DATABASE_CHILD_PEDAL = "pedalSpeed";

  public static final String WALK_TIMER_FRAGMENT = "timerFragment";
  public static final String TAG_STOP_FRAGMENT = "popupFragment";
  public static final String TAG_RESULTS_FRAGMENT = "resultsFragment";
  public static final String TAG_GALLERY_FULLSCREEN_FRAGMENT = "galleryfullscreenFragment";
  public static final String RUN_TIMER_FRAGMENT = "runTimerFragment";
  public static final String ERROR_DIALOG_FRAGMENT = "errorFragment";
  public static final String HOME_FRAGMENT = "homeFragment";

  public static final String HEADER_URL = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
  public static final String JPEG_PREFIX = "JPEG_";
  public static final String JPG_SUFFIX = ".jpg";
  public static final String PROVIDER_DIR = "com.ianarbuckle.fitnow.fileprovider";
  public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";

  public static final String SHARED_PREFERENCES = "profile";
  public static final String NAME_KEY = "name";
  public static final String EMAIL_KEY = "email";
  public static final String PHOTO_KEY = "photoUrl";
  public static final String DEFAULT_KEY = "";
  public static final String AUTH_PENDING = "auth_state_pending";
  public static final String SPEED_TYPE = "speed";
  public static final String DISTANCE_TYPE = "distance";
  public static final String STEPS_TYPE = "steps";
  public static final String CALORIES_TYPE = "calories";
  public static final String RPM_TYPE = "rpm";
  public static final String LOGGER = "logger";
  public static final String DISTANCE_KEY = "distance";
  public static final String TIME_KEY = "time";
  public static final String STEPS_KEY = "steps";
  public static final String SPEED_KEY = "speed";
  public static final String PEDAL_KEY = "pedal";
  public static final String CALORIES_KEY = "calories";
  public static final String POSITION_KEY = "position";
  public static final String IMAGES_KEY = "images";
  public static final String MESSAGE_KEY = "message";
  public static final String DATE_KEY = "date";
  public static final String SECONDS_KEY = "seconds";


  public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  public static final PeriodFormatter FORMAT_HOURS_MINUTES_SECONDS = new PeriodFormatterBuilder()
      .printZeroIfSupported().minimumPrintedDigits(2).appendHours()
      .printZeroIfSupported().minimumPrintedDigits(2)
      .appendSeparator(":").appendMinutes().printZeroIfSupported()
      .minimumPrintedDigits(2).appendSeparator(":").appendSeconds()
      .minimumPrintedDigits(2).toFormatter();

  public static final PeriodFormatter FORMAT_HOURS_MINUTES_SECONDS_RESULT = new PeriodFormatterBuilder()
      .appendHours()
      .appendSuffix("Hrs ")
      .appendMinutes()
      .appendSuffix(" mins")
      .appendSeconds()
      .appendSuffix(" secs")
      .toFormatter();
}
