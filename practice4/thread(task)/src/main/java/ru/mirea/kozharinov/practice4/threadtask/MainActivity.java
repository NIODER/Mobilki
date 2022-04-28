package ru.mirea.kozharinov.practice4.threadtask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText lessonsEdit;
    private EditText daysEdit;
    private TextView result;
    private final String TAG = "MainActivity";
    private int lessons;
    private int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lessonsEdit = findViewById(R.id.lessonsEdit);
        daysEdit = findViewById(R.id.daysEdit);
        Button calculateButton = findViewById(R.id.calculateButton);
        result = findViewById(R.id.resultTextView);
        calculateButton.setOnClickListener(this::onClickCalculateButton);

    }

    private void onClickCalculateButton(View view) {
        new Thread(() -> {
            if (lessonsEdit.getText() == null && daysEdit.getText() == null) {
                Toast.makeText(this, "Неправильно введены данные", Toast.LENGTH_SHORT).show();
                return;
            }
            lessons = Integer.parseInt(String.valueOf(lessonsEdit.getText()));
            days = Integer.parseInt(String.valueOf(daysEdit.getText()));
            setResult();
        }).start();
    }

    private void setResult() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            Log.e(TAG, "setResult sleep error");
        }
        runOnUiThread(() -> result.setText(String.valueOf((float) lessons / days)));
    }
}