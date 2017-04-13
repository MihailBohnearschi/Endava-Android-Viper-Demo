package com.endava.viperdemo.screens.loading;

import com.endava.viperdemo.screens.tasks.TasksActivity;

public class LoadingRouter {

  private final LoadingActivity activity;

  public LoadingRouter(LoadingActivity activity) {
    this.activity = activity;
  }

  void showTasksScreen() {
    TasksActivity.start(activity);
  }
}
