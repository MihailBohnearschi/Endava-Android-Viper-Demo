package com.endava.viperdemo.screens.add;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.endava.viperdemo.R;
import com.endava.viperdemo.api.request.AddTaskRequest;
import com.endava.viperdemo.database.domain.Task;
import com.endava.viperdemo.utils.StringUtils;
import com.endava.viperdemo.utils.UiUtils;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import rx.Observable;

import static com.endava.viperdemo.utils.UiUtils.getInputText;
import static com.endava.viperdemo.utils.UiUtils.playFailureAnimation;
import static com.endava.viperdemo.utils.validation.ValidationUtils.TASK_ASSIGNEE_FAIL;
import static com.endava.viperdemo.utils.validation.ValidationUtils.TASK_DATE_FAIL;
import static com.endava.viperdemo.utils.validation.ValidationUtils.TASK_DESCRIPTION_FAIL;
import static com.endava.viperdemo.utils.validation.ValidationUtils.TASK_NAME_FAIL;

public class AddView {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.taskName)
  TextInputLayout taskName;
  @BindView(R.id.taskDescription)
  TextInputLayout taskDescription;
  @BindView(R.id.taskAssignee)
  TextInputLayout taskAssignee;
  @BindView(R.id.taskDate)
  TextView taskDate;

  private final View view;
  private final AddActivity activity;
  private final ProgressDialog progressDialog;

  public AddView(AddActivity activity, ProgressDialog progressDialog) {
    this.activity = activity;
    this.progressDialog = progressDialog;
    this.view = View.inflate(activity, R.layout.activity_add, null);
    ButterKnife.bind(this, view);
  }

  View getView() {
    return this.view;
  }

  Observable<Void> toolbarNavigation() {
    return RxToolbar.navigationClicks(toolbar);
  }

  Observable<Integer> observeActionItem() {
    return RxToolbar.itemClicks(toolbar).map(MenuItem::getItemId);
  }

  Observable<Void> taskDateClicks() {
    return RxView.clicks(taskDate);
  }

  AddTaskRequest getAddTaskRequest() {
    Task task = new Task();

    task.setName(getInputText(taskName));
    task.setDescription(getInputText(taskDescription));
    task.setAssignee(getInputText(taskAssignee));
    task.setDate(StringUtils.getDisplayMillis(getInputText(taskDate)));

    return new AddTaskRequest(task);
  }

  void setupView() {
    toolbar.setTitle(R.string.toolbar_add_task);
    UiUtils.setupBackToolbar(activity, toolbar);
  }

  void showLoading(int loadingResourceId) {
    progressDialog.setMessage(activity.getString(loadingResourceId));
    progressDialog.show();
  }

  void hideLoading() {
    progressDialog.hide();
  }

  void setDate(String date) {
    this.taskDate.setText(date);
  }

  void handleErrorCode(Integer errorCode) {
    if (null == errorCode) {
      return;
    }

    switch (errorCode) {
      case TASK_NAME_FAIL:
        playFailureAnimation(taskName);
        break;

      case TASK_DESCRIPTION_FAIL:
        playFailureAnimation(taskDescription);
        break;

      case TASK_ASSIGNEE_FAIL:
        playFailureAnimation(taskAssignee);
        break;

      case TASK_DATE_FAIL:
        playFailureAnimation(taskDate);
        break;

      default:
        //no-op
        break;
    }
  }

  void showError(String error) {
    if (null == error) {
      showError(R.string.error_add_task);
      return;
    }
    UiUtils.showSnackbar(view, error, Snackbar.LENGTH_SHORT);
  }

  void showError(int errorResourceId) {
    UiUtils.showSnackbar(view, errorResourceId, Snackbar.LENGTH_SHORT);
  }
}