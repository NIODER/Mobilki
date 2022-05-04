package ru.mirea.kozharinov.practice6.internalfilestorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String fileName = "mirea.txt";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this::onClickButton);
        String text = "Hello mirea";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, MODE_PRIVATE);
            outputStream.write(text.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            String exceptionText = String.format("Cannot openFileOutput %s", e.getMessage());
            Log.e(TAG, exceptionText);
        } catch (IOException e) {
            e.printStackTrace();
            String exceptionText = String.format("Cannot write %s", e.getMessage());
            Log.e(TAG, exceptionText);
        }
    }

    private void onClickButton(View view) {
        Toast.makeText(this, "Start reading", Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(this, "Sleep error", Toast.LENGTH_SHORT).show();
            }
            textView.post(() -> textView.setText(getFromFile()));
        }).start();
    }

    @NonNull
    private String getFromFile() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
            byte[] bytes = new byte[fileInputStream.available()];
            //noinspection ResultOfMethodCallIgnored
            fileInputStream.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return "error";
    }
}