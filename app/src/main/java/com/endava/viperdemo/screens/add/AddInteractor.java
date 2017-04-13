package com.endava.viperdemo.screens.add;

import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.api.request.AddTaskRequest;
import com.endava.viperdemo.api.response.AddTaskResponse;
import com.endava.viperdemo.utils.NetworkUtils;
import rx.Observable;

public class AddInteractor {

  private final AddActivity activity;
  private final TaskApi taskApi;

  public AddInteractor(AddActivity activity, TaskApi taskApi) {
    this.activity = activity;
    this.taskApi = taskApi;
  }

  Observable<AddTaskResponse> addTask(AddTaskRequest request) {
    return taskApi.addTask(request);
  }

  Boolean networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }
}