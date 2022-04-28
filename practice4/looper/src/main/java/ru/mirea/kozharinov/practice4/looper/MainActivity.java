package ru.mirea.kozharinov.practice4.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyLooper myLooper;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this::onClick);
        myLooper = new MyLooper();
        myLooper.start();
        textView = findViewById(R.id.textView);
    }

    private void onClick(View view) {
//        Message message = new Message();
//        Bundle bundle = new Bundle();
//        bundle.putString("KEY", "mirea");
//        message.setData(bundle);
//        if (myLooper != null) {
//            myLooper.handler.sendMessage(message);
//        }
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("AGE", 19);
        bundle.putString("PROF", "programmer");
        message.setData(bundle);
        if (myLooper != null) {
            myLooper.handler.sendMessage(message);
        }
        textView.setText("Ждем 19 сек");
    }
}