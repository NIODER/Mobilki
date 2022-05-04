package ru.mirea.kozharinov.practice6.preferences;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.strictmode.Violation;
import android.view.View;

import ru.mirea.kozharinov.practice6.preferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;
    private static final String PROPERTY_NAME = "prop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.saveButton.setOnClickListener(this::onClickSaveButton);
        binding.loadButton.setOnClickListener(this::onClickLoadButton);
        preferences = getPreferences(MODE_PRIVATE);
    }

    private void onClickSaveButton(View view) {
        String text = binding.editTextTextPersonName.getText().toString();
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PROPERTY_NAME, text);
        editor.apply();
    }

    private void onClickLoadButton(View view) {
        binding.textView.setText(preferences.getString(PROPERTY_NAME, "empty"));
    }
}