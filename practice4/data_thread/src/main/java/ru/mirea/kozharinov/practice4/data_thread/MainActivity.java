package ru.mirea.kozharinov.practice4.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvInfo = findViewById(R.id.textView);
        final Runnable runnable = () -> tvInfo.setText("runnable");
        final Runnable runnable1 = () -> tvInfo.setText("runnable1");
        final Runnable runnable2 = () -> tvInfo.setText("runnable2");
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(runnable);
                TimeUnit.SECONDS.sleep(1);
                tvInfo.postDelayed(runnable2, 2000);
                tvInfo.post(runnable1);
            } catch (InterruptedException ignored) { }
        });
        thread.start();
    }
}