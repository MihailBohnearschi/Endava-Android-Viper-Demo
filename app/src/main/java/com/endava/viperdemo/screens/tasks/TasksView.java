package com.endava.viperdemo.screens.tasks;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.endava.viperdemo.R;
import com.endava.viperdemo.database.domain.Task;
import com.endava.viperdemo.screens.tasks.list.TaskAdapter;
import com.endava.viperdemo.utils.UiUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.view.RxView;
import java.util.List;
import rx.Observable;

public class TasksView {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list)
  RecyclerView list;
  @BindView(R.id.swipeRefreshLayout)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.addTask)
  FloatingActionButton addTask;

  private View view;
  private TaskAdapter adapter;

  public TasksView(Activity activity, TaskAdapter adapter) {
    this.adapter = adapter;
    this.view = View.inflate(activity, R.layout.activity_tasks, null);
    ButterKnife.bind(this, view);
  }

  View getView() {
    return view;
  }

  void setLoading(boolean showLoading) {
    swipeRefreshLayout.setRefreshing(showLoading);
  }

  void setupView() {
    toolbar.setTitle(view.getContext().getString(R.string.toolbar_tasks));
    UiUtils.setupSimpleToolbar((AppCompatActivity) view.getContext(), toolbar);
    list.setAdapter(adapter);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
    list.setLayoutManager(mLayoutManager);
  }

  void setTasks(List<Task> tasks) {
    if (null == tasks) {
      return;
    }

    adapter.swapData(tasks);
  }

  Observable<Void> refreshCalled() {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout);
  }

  Observable<Void> addTaskClicks() {
    return RxView.clicks(addTask);
  }
}
