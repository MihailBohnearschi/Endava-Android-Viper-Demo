package com.endava.viperdemo.screens.add.dagger;

import com.endava.viperdemo.application.dagger.AppComponent;
import com.endava.viperdemo.screens.add.AddActivity;
import dagger.Component;

@AddScope
@Component(modules = AddModule.class, dependencies = AppComponent.class)
public interface AddComponent {

  void inject(AddActivity activity);
}