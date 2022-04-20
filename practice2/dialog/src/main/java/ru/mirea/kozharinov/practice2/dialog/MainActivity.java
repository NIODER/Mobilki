package ru.mirea.kozharinov.practice2.dialog;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment =
                new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void onClickShowTimePickerDialog(View view) {
        MyTimePickerDialog timePickerDialog =
                new MyTimePickerDialog(
                        getApplicationContext(),
                        this::onTimeSetFunction,
                        1,
                        1,
                        true
                );
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickShowDatePickerDialog(View view) {
        MyDatePickerDialog datePickerDialog =
                new MyDatePickerDialog(
                        getApplicationContext(),
                        this::onDateSetFunction,
                        1,
                        1,
                        1
                );
        datePickerDialog.show();
    }

    public void onClickShowProgressPickerDialog(View view) {
        MyProgressDialog progressDialog =
                new MyProgressDialog(getApplicationContext());
        progressDialog.show();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(),
                    "sleep error",
                    Toast.LENGTH_SHORT).show();
        }
        progressDialog.setProgress(10);
    }

    private void onTimeSetFunction(TimePicker timePicker, int i, int i1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setText(
                    new StringBuilder()
                            .append(timePicker.getHour())
                            .append("\n")
                            .append(timePicker.getMinute())
                            .append("\n")
                            .append(i)
                            .append(" | ")
                            .append(i1)
            );
        } else {
            textView.setText("onTimeSetFunction api ver low");
        }
    }

    private void onDateSetFunction(DatePicker view, int year, int month, int day) {
        textView.setText(
                new StringBuilder()
                        .append(view.getYear())
                        .append("\n")
                        .append(view.getMonth())
                        .append("\n")
                        .append(year)
                        .append(" | ")
                        .append(day)
        );
    }
}