package ru.mirea.kozharinov.practice2.dialog;

import android.app.TimePickerDialog;
import android.content.Context;

public class MyTimePickerDialog extends TimePickerDialog {
    public MyTimePickerDialog(Context context,
                                      OnTimeSetListener listener,
                                      int hourOfDay,
                                      int minute,
                                      boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
    }
}
