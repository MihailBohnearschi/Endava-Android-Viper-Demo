package com.endava.viperdemo.application.dagger;

import android.content.Context;
import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import dagger.Component;

@AppScope
@Component(modules = {
  AppModule.class, NetworkModule.class, RestServiceModule.class, GsonModule.class, DataModule.class,
  RxModule.class
})
public interface AppComponent {

  Context context();

  TaskApi taskApi();

  TaskRepository taskRepository();

  RxSchedulers rxSchedulers();
}