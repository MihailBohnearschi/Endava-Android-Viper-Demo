package com.endava.viperdemo.screens.add.dagger;

import android.app.ProgressDialog;
import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.screens.add.AddActivity;
import com.endava.viperdemo.screens.add.AddInteractor;
import com.endava.viperdemo.screens.add.AddPresenter;
import com.endava.viperdemo.screens.add.AddRouter;
import com.endava.viperdemo.screens.add.AddView;
import com.endava.viperdemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class AddModule {

  private final AddActivity activity;

  public AddModule(AddActivity activity) {
    this.activity = activity;
  }

  @Provides
  @AddScope
  public AddView view() {
    ProgressDialog progressDialog = new ProgressDialog(activity);
    progressDialog.setCancelable(false);
    return new AddView(activity, progressDialog);
  }

  @Provides
  @AddScope
  public AddPresenter presenter(AddView view, AddInteractor interactor, AddRouter router,
    RxSchedulers rxSchedulers) {
    CompositeSubscription subscriptions = new CompositeSubscription();
    return new AddPresenter(view, interactor, router, subscriptions, rxSchedulers);
  }

  @Provides
  @AddScope
  public AddInteractor interactor(TaskApi taskApi) {
    return new AddInteractor(activity, taskApi);
  }

  @Provides
  @AddScope
  public AddRouter router() {
    return new AddRouter(activity);
  }
}