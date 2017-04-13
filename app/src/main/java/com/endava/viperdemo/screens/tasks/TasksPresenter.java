package com.endava.viperdemo.screens.tasks;

import com.endava.viperdemo.utils.UiUtils;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class TasksPresenter {

  private final TasksView view;
  private final TasksInteractor interactor;
  private final TasksRouter router;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public TasksPresenter(TasksView view, TasksInteractor interactor, TasksRouter router,
    CompositeSubscription subscriptions, RxSchedulers rxSchedulers) {
    this.view = view;
    this.interactor = interactor;
    this.router = router;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  void onCreate() {
    view.setupView();
    subscriptions.add(addTaskClicksSubscription());
    subscriptions.add(loadTasksSubscription());
    subscriptions.add(refreshCalledSubscription());
  }

  void onResume() {
    subscriptions.add(loadNetworkTasksSubscription());
  }

  void onDestroy() {
    subscriptions.clear();
  }

  private Subscription addTaskClicksSubscription() {
    return view.addTaskClicks().subscribe(aVoid -> router.showAddScreen());
  }

  private Subscription loadTasksSubscription() {
    return Observable.just(interactor.getTasks())
      .subscribeOn(rxSchedulers.background())
      .observeOn(rxSchedulers.androidUI())
      .onErrorResumeNext(throwable -> {
        UiUtils.handleThrowable(throwable);
        return Observable.just(null);
      })
      .subscribe(view::setTasks);
  }

  private Subscription loadNetworkTasksSubscription() {
    return Observable.just(interactor.networkAvailable())
      .filter(networkAvailable -> {
        view.setLoading(networkAvailable);
        return networkAvailable;
      })
      .observeOn(rxSchedulers.network())
      .flatMap(networkAvailable -> interactor.loadNetworkTasks())
      .observeOn(rxSchedulers.androidUI())
      .onErrorResumeNext(throwable -> {
        UiUtils.handleThrowable(throwable);
        return Observable.just(null);
      })
      .subscribe(tasks -> {
        view.setLoading(false);
        interactor.saveTasks(tasks);
        view.setTasks(tasks);
      });
  }

  private Subscription refreshCalledSubscription() {
    return view.refreshCalled().map(aVoid -> interactor.networkAvailable()).filter(networkAvailable -> {
      view.setLoading(networkAvailable);
      return networkAvailable;
    }).subscribe(aVoid -> subscriptions.add(loadNetworkTasksSubscription()));
  }
}
