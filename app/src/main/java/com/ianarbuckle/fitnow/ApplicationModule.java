package com.ianarbuckle.fitnow;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */
@Module
public class ApplicationModule {
  private FitNowApplication application;

  public ApplicationModule(FitNowApplication application) {
    this.application = application;
  }

  @Provides
  public Context provideContext() {
    return application.getApplicationContext();
  }

}
