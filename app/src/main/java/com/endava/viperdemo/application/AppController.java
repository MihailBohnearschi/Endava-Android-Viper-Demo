package com.endava.viperdemo.application;

import android.app.Application;
import com.endava.viperdemo.BuildConfig;
import com.endava.viperdemo.application.dagger.AppComponent;
import com.endava.viperdemo.application.dagger.AppModule;
import com.endava.viperdemo.application.dagger.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class AppController extends Application {

  private static AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initComponent();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }

    LeakCanary.install(this);
  }

  public static AppComponent getAppComponent() {
    return appComponent;
  }

  protected void initComponent() {
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }
}