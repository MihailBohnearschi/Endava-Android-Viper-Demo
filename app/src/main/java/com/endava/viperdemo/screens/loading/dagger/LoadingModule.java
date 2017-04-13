package com.endava.viperdemo.screens.loading.dagger;

import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.screens.loading.LoadingActivity;
import com.endava.viperdemo.screens.loading.LoadingInteractor;
import com.endava.viperdemo.screens.loading.LoadingPresenter;
import com.endava.viperdemo.screens.loading.LoadingRouter;
import com.endava.viperdemo.screens.loading.LoadingView;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class LoadingModule {

  private final LoadingActivity activity;

  public LoadingModule(LoadingActivity activity) {
    this.activity = activity;
  }

  @Provides
  @LoadingScope
  public LoadingView view() {
    return new LoadingView(activity);
  }

  @Provides
  @LoadingScope
  public LoadingPresenter presenter(LoadingInteractor interactor, LoadingRouter router,
    RxSchedulers rxSchedulers) {
    CompositeSubscription subscriptions = new CompositeSubscription();
    return new LoadingPresenter(interactor, router, subscriptions, rxSchedulers);
  }

  @Provides
  @LoadingScope
  public LoadingInteractor interactor(TaskApi taskApi, TaskRepository taskRepository) {
    return new LoadingInteractor(activity, taskApi, taskRepository);
  }

  @Provides
  @LoadingScope
  public LoadingRouter router() {
    return new LoadingRouter(activity);
  }
}