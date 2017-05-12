package com.ianarbuckle.fitnow.helpers;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import com.ianarbuckle.fitnow.R;
import com.ianarbuckle.fitnow.utils.Constants;

import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Seconds;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ian Arbuckle on 17/04/2017.
 *
 */

public class TimerHelper {

  public interface TimerHelperView {
    void setResult(String result);
  }

  private Activity activity;
  private TimerHelperView view;

  private Timer timer;
  TimerTask timerTask;
  private final Handler handler;

  private int seconds;
  private boolean running;
  private String result;

  private Bundle bundle;

  public TimerHelper(Activity activity, TimerHelperView view) {
    this.view = view;
    this.activity = activity;
    result = "";
    handler = new Handler();
    running = false;
    bundle = new Bundle();
  }

  public void startTimer() {
    if(running) {
      return;
    }

    if(timer == null) {
      running = true;
      initTimerTask();
    }
  }

  private void initTimerTask() {
    timer = new Timer();
    timerTask = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          @Override
          public void run() {
            updateTextInUiThread();
          }
        });
      }
    };
    Resources resources = activity.getResources();
    int delay = resources.getInteger(R.integer.timer_delay);
    int period = resources.getInteger(R.integer.timer_period);
    timer.schedule(timerTask, delay, period);
  }

  private void updateTextInUiThread() {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        updateTimerText();
      }
    });
  }

  private void updateTimerText() {
    seconds += 1;
    result = getTimeFormat(seconds);
    bundle.putInt(Constants.SECONDS_KEY, seconds);
    view.setResult(result);
  }

  public Bundle getBundle() {
    return bundle;
  }

  private String getTimeFormat(int seconds) {
    Seconds convertSeconds = Seconds.seconds(seconds);
    Period period = new Period(convertSeconds);
    return Constants.FORMAT_HOURS_MINUTES_SECONDS.print(period.normalizedStandard(PeriodType.time()));
  }

  public void stopTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  public void pauseTimer() {
    if (!isRunning()) {
      return;
    }
    this.stopTimer();
    running = false;
  }

  public boolean isRunning() {
    return running;
  }

  public void resumeTimer() {
    this.startTimer();
  }

}
