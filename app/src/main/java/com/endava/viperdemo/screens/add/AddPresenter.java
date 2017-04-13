package com.endava.viperdemo.screens.add;

import com.endava.viperdemo.R;
import com.endava.viperdemo.api.request.AddTaskRequest;
import com.endava.viperdemo.api.response.AddTaskResponse;
import com.endava.viperdemo.utils.StringUtils;
import com.endava.viperdemo.utils.UiUtils;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import com.endava.viperdemo.utils.validation.ValidationResponse;
import com.endava.viperdemo.utils.validation.ValidationUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class AddPresenter implements DatePickerDialog.OnDateSetListener {

  private final AddView view;
  private final AddInteractor interactor;
  private final AddRouter router;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public AddPresenter(AddView view, AddInteractor interactor, AddRouter router,
    CompositeSubscription subscriptions, RxSchedulers rxSchedulers) {
    this.view = view;
    this.interactor = interactor;
    this.router = router;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  void onCreate() {
    view.setupView();
    subscriptions.add(toolbarNavigationSubscription());
    subscriptions.add(actionItemSelectedSubscription());
    subscriptions.add(inputDateClicksSubscription());
  }

  void onDestroy() {
    subscriptions.clear();
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    String date = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
    this.view.setDate(StringUtils.getDisplayDate(StringUtils.getMillis(date)));
  }

  private Observable<AddTaskResponse> addTask(AddTaskRequest request) {
    return interactor.addTask(request)
      .subscribeOn(rxSchedulers.network())
      .observeOn(rxSchedulers.androidUI());
  }

  private Boolean validateRequest(AddTaskRequest request) {
    ValidationResponse response = ValidationUtils.validateAddTaskRequest(request);
    if (!response.getSuccess()) {
      view.showError(response.getFailReason());
      view.handleErrorCode(response.getFailCode());
    }

    return response.getSuccess();
  }

  private Boolean validateNetwork() {
    Boolean isNetworkAvailable = interactor.networkAvailable();
    if (!isNetworkAvailable) {
      view.showError(R.string.error_no_internet);
    }
    return isNetworkAvailable;
  }

  private Subscription toolbarNavigationSubscription() {
    return view.toolbarNavigation().subscribe(aVoid -> router.finish());
  }

  private Subscription actionItemSelectedSubscription() {
    return view.observeActionItem()
      .filter(actionItem -> actionItem == R.id.addTask)
      .map(viewId -> view.getAddTaskRequest())
      .filter(this::validateRequest)
      .filter(request -> validateNetwork())
      .doOnNext(request -> view.showLoading(R.string.loading_add_task))
      .flatMap(this::addTask)
      .onErrorResumeNext(throwable -> {
        UiUtils.handleThrowable(throwable);
        return Observable.just(null);
      })
      .subscribe(response -> {
        view.hideLoading();
        if (null == response) {
          view.showError(null);
          return;
        }
        if (response.getSuccess()) {
          router.finish();
        } else {
          view.showError(response.getFailReason());
        }
      });
  }

  private Subscription inputDateClicksSubscription() {
    return view.taskDateClicks().subscribe(aVoid -> router.showDatePicker(this));
  }
}