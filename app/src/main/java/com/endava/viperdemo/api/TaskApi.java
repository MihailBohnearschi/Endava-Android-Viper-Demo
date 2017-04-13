package com.endava.viperdemo.api;

import com.endava.viperdemo.api.request.AddTaskRequest;
import com.endava.viperdemo.api.response.AddTaskResponse;
import com.endava.viperdemo.database.domain.Task;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface TaskApi {

  @GET("api/getTasks")
  Observable<List<Task>> getTasks();

  @POST("api/addTask")
  Observable<AddTaskResponse> addTask(@Body AddTaskRequest request);
}