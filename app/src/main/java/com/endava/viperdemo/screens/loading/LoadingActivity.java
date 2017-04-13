package com.endava.viperdemo.screens.loading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.endava.viperdemo.application.AppController;
import com.endava.viperdemo.screens.loading.dagger.DaggerLoadingComponent;
import com.endava.viperdemo.screens.loading.dagger.LoadingModule;
import javax.inject.Inject;

public class LoadingActivity extends AppCompatActivity {

  @Inject
  LoadingPresenter presenter;

  @Inject
  LoadingView view;

  public static void start(Context context) {
    Intent intent = new Intent(context, LoadingActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerLoadingComponent.builder()
      .appComponent(AppController.getAppComponent())
      .loadingModule(new LoadingModule(this))
      .build()
      .inject(this);

    setContentView(view.getView());
    presenter.onCreate();
  }

  @Override
  protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }
}