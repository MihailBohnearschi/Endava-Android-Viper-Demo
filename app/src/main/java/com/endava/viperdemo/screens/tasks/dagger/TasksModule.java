package com.endava.viperdemo.screens.tasks.dagger;

import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.screens.tasks.TasksActivity;
import com.endava.viperdemo.screens.tasks.TasksInteractor;
import com.endava.viperdemo.screens.tasks.TasksPresenter;
import com.endava.viperdemo.screens.tasks.TasksRouter;
import com.endava.viperdemo.screens.tasks.TasksView;
import com.endava.viperdemo.screens.tasks.list.TaskAdapter;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class TasksModule {

  private final TasksActivity activity;

  public TasksModule(TasksActivity activity) {
    this.activity = activity;
  }

  @Provides
  @TasksScope
  public TasksView view() {
    return new TasksView(activity, new TaskAdapter());
  }

  @Provides
  @TasksScope
  public TasksPresenter presenter(TasksView view, TasksInteractor interactor, TasksRouter router,
    RxSchedulers rxSchedulers) {
    CompositeSubscription subscriptions = new CompositeSubscription();
    return new TasksPresenter(view, interactor, router, subscriptions, rxSchedulers);
  }

  @Provides
  @TasksScope
  public TasksInteractor interactor(TaskApi taskApi, TaskRepository taskRepository) {
    return new TasksInteractor(activity, taskApi, taskRepository);
  }

  @Provides
  @TasksScope
  public TasksRouter router() {
    return new TasksRouter(activity);
  }
}