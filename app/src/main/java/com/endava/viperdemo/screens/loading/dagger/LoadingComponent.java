package com.endava.viperdemo.screens.loading.dagger;

import com.endava.viperdemo.application.dagger.AppComponent;
import com.endava.viperdemo.screens.loading.LoadingActivity;
import dagger.Component;

@LoadingScope
@Component(modules = LoadingModule.class, dependencies = AppComponent.class)
public interface LoadingComponent {

  void inject(LoadingActivity activity);
}