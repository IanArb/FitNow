package com.ianarbuckle.fitnow;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.RefWatcher;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class FitNowApplication extends Application {

  private ApplicationComponent applicationComponent;

  private RefWatcher refWatcher;

  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    initFabric();

  }

  public static ApplicationComponent getApplicationComponent(Context context) {
    FitNowApplication application = (FitNowApplication) context.getApplicationContext();
    if(application.applicationComponent == null) {
      application.applicationComponent = DaggerApplicationComponent.builder()
          .applicationModule(application.getApplicationModule())
          .build();
    }
    return application.applicationComponent;
  }

  protected ApplicationModule getApplicationModule() {
    return new ApplicationModule(this);
  }

  public void initFabric() {
    Fabric.with(this, new Crashlytics());
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
