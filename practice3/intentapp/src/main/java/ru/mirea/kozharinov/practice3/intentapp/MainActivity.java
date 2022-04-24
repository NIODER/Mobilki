package ru.mirea.kozharinov.practice3.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button goToSecondActivity = findViewById(R.id.button);
        goToSecondActivity.setOnClickListener(this::onClickGoToSecondActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String dateTime = getIntent().getStringExtra("dateTime");
        TextView textView = findViewById(R.id.textView);
        textView.setText(dateTime);
    }

    private void onClickGoToSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}