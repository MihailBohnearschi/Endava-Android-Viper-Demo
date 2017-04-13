package com.endava.viperdemo.screens.loading;

import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.database.domain.Task;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.utils.NetworkUtils;
import java.util.List;
import rx.Observable;

public class LoadingInteractor {

  private final LoadingActivity activity;
  private final TaskApi taskApi;
  private final TaskRepository taskRepository;

  public LoadingInteractor(LoadingActivity activity, TaskApi taskApi, TaskRepository taskRepository) {
    this.activity = activity;
    this.taskApi = taskApi;
    this.taskRepository = taskRepository;
  }

  Boolean networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }

  Observable<List<Task>> loadNetworkTasks() {
    return taskApi.getTasks();
  }

  void saveTasks(List<Task> tasks) {
    if (null == tasks) {
      return;
    }

    taskRepository.deleteAll();
    taskRepository.saveAll(tasks);
  }
}