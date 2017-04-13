package com.endava.viperdemo.screens.loading;

import android.app.Activity;
import android.view.View;
import butterknife.ButterKnife;
import com.endava.viperdemo.R;

public class LoadingView {

  private View view;

  public LoadingView(Activity activity) {
    this.view = View.inflate(activity, R.layout.activity_loading, null);
    ButterKnife.bind(this, view);
  }

  View getView() {
    return view;
  }
}
