package com.endava.viperdemo.screens.loading;

import com.endava.viperdemo.utils.UiUtils;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LoadingPresenter {

  private final LoadingInteractor interactor;
  private final LoadingRouter router;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public LoadingPresenter(LoadingInteractor interactor, LoadingRouter router,
    CompositeSubscription subscriptions, RxSchedulers rxSchedulers) {
    this.interactor = interactor;
    this.router = router;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  void onCreate() {
    subscriptions.add(loadTasksSubscription());
  }

  void onDestroy() {
    subscriptions.clear();
  }

  private Subscription loadTasksSubscription() {
    return Observable.just(interactor.networkAvailable())
      .filter(networkAvailable -> {
        if (!networkAvailable) {
          router.showTasksScreen();
        }
        return networkAvailable;
      })
      .flatMap(networkAvailable -> interactor.loadNetworkTasks())
      .subscribeOn(rxSchedulers.network())
      .observeOn(rxSchedulers.androidUI())
      .onErrorResumeNext(throwable -> {
        UiUtils.handleThrowable(throwable);
        return Observable.just(null);
      })
      .subscribe(tasks -> {
        interactor.saveTasks(tasks);
        router.showTasksScreen();
      });
  }
}