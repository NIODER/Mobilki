package ru.mirea.kozharinov.practice2.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyDatePickerDialog extends DatePickerDialog {

    public MyDatePickerDialog(@NonNull Context context,
                              @Nullable OnDateSetListener listener,
                              int year,
                              int month,
                              int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }
}
