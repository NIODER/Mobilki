package ru.mirea.kozharinov.practice3.resultactivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 143;
    private ActivityResultLauncher<Intent> dataActivityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        this::onActivityResult
                );
        setContentView(R.layout.activity_main);
        Button answerButton = findViewById(R.id.answerButton);
        answerButton.setOnClickListener(this::onClickAnswerButton);
    }

    private void onClickAnswerButton(View view) {
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        dataActivityResultLauncher.launch(intent);
    }

    private void onActivityResult(@NonNull ActivityResult result) {
        Intent data = result.getData();
        super.onActivityResult(REQUEST_CODE, result.getResultCode(), data);
        if (data != null) {
            String universityName = data.getStringExtra("universityName");
            TextView answer = findViewById(R.id.answerView);
            answer.setText(universityName);
        }
    }
}