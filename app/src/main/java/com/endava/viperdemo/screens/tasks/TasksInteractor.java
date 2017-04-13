package com.endava.viperdemo.screens.tasks;

import com.endava.viperdemo.api.TaskApi;
import com.endava.viperdemo.database.domain.Task;
import com.endava.viperdemo.database.repository.TaskRepository;
import com.endava.viperdemo.utils.NetworkUtils;
import java.util.List;
import rx.Observable;

public class TasksInteractor {

  private final TasksActivity activity;
  private final TaskApi taskApi;
  private final TaskRepository taskRepository;

  public TasksInteractor(TasksActivity activity, TaskApi taskApi, TaskRepository taskRepository) {
    this.activity = activity;
    this.taskApi = taskApi;
    this.taskRepository = taskRepository;
  }

  Observable<List<Task>> loadNetworkTasks() {
    return taskApi.getTasks();
  }

  List<Task> getTasks() {
    return taskRepository.getAll();
  }

  Boolean networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }

  void saveTasks(List<Task> tasks) {
    if (null == tasks) {
      return;
    }

    taskRepository.deleteAll();
    taskRepository.saveAll(tasks);
  }
}