package com.endava.viperdemo.application.dagger;

import com.endava.viperdemo.utils.rx.AppRxSchedulers;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;

@Module
class RxModule {

  @AppScope
  @Provides
  RxSchedulers provideRxSchedulers() {
    return new AppRxSchedulers();
  }
}