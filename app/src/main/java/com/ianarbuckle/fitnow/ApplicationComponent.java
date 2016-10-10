package com.ianarbuckle.fitnow;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */
@Component(modules = {ApplicationModule.class})
@Singleton
public interface ApplicationComponent {

  Context context();
}
