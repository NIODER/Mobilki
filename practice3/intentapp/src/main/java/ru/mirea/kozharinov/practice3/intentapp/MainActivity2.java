package ru.mirea.kozharinov.practice3.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button getDateTimeAndReturnButton = findViewById(R.id.button2);
        getDateTimeAndReturnButton.setOnClickListener(this::onClickGetTimeAndReturnButton);
    }

    private void onClickGetTimeAndReturnButton(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        long dateInMillis = System.currentTimeMillis();
        String format = "yyyy-MM-dd HH:mm:ss";
        @SuppressLint("SimpleDateFormat")
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateString = simpleDateFormat.format(new Date(dateInMillis));
        intent.putExtra("dateTime", dateString);
        startActivity(intent);
    }
}