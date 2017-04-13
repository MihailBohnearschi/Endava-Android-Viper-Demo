package com.endava.viperdemo.screens.tasks.dagger;

import com.endava.viperdemo.application.dagger.AppComponent;
import com.endava.viperdemo.screens.tasks.TasksActivity;
import dagger.Component;

@TasksScope
@Component(modules = TasksModule.class, dependencies = AppComponent.class)
public interface TasksComponent {

  void inject(TasksActivity activity);
}