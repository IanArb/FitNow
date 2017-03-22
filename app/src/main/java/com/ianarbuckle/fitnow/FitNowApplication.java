package com.ianarbuckle.fitnow;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelper;
import com.ianarbuckle.fitnow.firebase.auth.AuthenticationHelperImpl;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelper;
import com.ianarbuckle.fitnow.firebase.database.DatabaseHelperImpl;

/**
 * Created by Ian Arbuckle on 10/10/2016.
 *
 */

public class  FitNowApplication extends Application {

  private ApplicationComponent applicationComponent;

  private AuthenticationHelper authenticationHelper;

  private DatabaseHelper databaseHelper;

  private static FitNowApplication appInstance;

  @Override
  public void onCreate() {
    super.onCreate();

    getApplicationComponent(this);

    initFirebase();
  }

  private void initFirebase() {
    if(!FirebaseApp.getApps(this).isEmpty()) {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
      appInstance = this;
      FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
      FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
      databaseHelper = new DatabaseHelperImpl(firebaseDatabase);
      authenticationHelper = new AuthenticationHelperImpl(firebaseAuth);
    }
  }

  public static FitNowApplication getAppInstance() {
    return appInstance;
  }

  public DatabaseHelper getDatabaseHelper() {
    return databaseHelper;
  }

  public AuthenticationHelper getAuthenticationHelper() {
    return authenticationHelper;
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

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
