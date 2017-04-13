package com.endava.viperdemo.screens.add;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;

public class AddRouter {

  private static final String DATE_PICKER_TAG = "datePickerTag";

  private final AddActivity activity;

  public AddRouter(AddActivity activity) {
    this.activity = activity;
  }

  void finish() {
    this.activity.finish();
  }

  void showDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
    Calendar now = Calendar.getInstance();
    DatePickerDialog datePickerDialog =
      DatePickerDialog.newInstance(dateSetListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH));
    datePickerDialog.show(activity.getSupportFragmentManager(), DATE_PICKER_TAG);
  }
}