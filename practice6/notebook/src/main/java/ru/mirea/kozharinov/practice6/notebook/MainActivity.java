package ru.mirea.kozharinov.practice6.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import ru.mirea.kozharinov.practice6.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private static final String PROPERTY_NAME = "last_file_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.editText.setOnClickListener(view -> binding.editText.setText(""));
        preferences = getPreferences(MODE_PRIVATE);
        String filename = preferences.getString(PROPERTY_NAME, null);
        if (filename != null) {
            binding.fileName.setText(filename);
            String fileContent = getFileContent(filename);
            binding.editText.setText(fileContent);
        }
        binding.saveButton.setOnClickListener(this::onClickSaveButton);
    }

    private void onClickSaveButton(View view) {
        String filename = binding.fileName.getText().toString();
        if (filename.equals("")) {
            Toast.makeText(this, "Unsupported filename", Toast.LENGTH_LONG).show();
            return;
        }
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROPERTY_NAME, filename);
        editor.apply();
        String text = binding.editText.getText().toString();
        writeContentInFile(filename, text);
        binding.editText.setText(getFileContent(preferences.getString(PROPERTY_NAME, null)));
    }

    private void writeContentInFile(@NonNull String filename, @NonNull String content) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PROPERTY_NAME, filename);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can not write", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    private String getFileContent(String filename) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(filename);
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
        return "nothing write here";
    }
}