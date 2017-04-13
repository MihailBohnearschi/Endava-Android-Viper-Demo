package com.endava.viperdemo.screens.tasks;

import com.endava.viperdemo.screens.add.AddActivity;
import com.endava.viperdemo.screens.tasks.TasksActivity;

public class TasksRouter {
  private final TasksActivity activity;

  public TasksRouter(TasksActivity activity) {
    this.activity = activity;
  }

  void showAddScreen() {
    AddActivity.start(activity);
  }
}
