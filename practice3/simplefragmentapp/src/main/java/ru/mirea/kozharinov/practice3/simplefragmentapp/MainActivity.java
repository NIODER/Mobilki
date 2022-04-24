package ru.mirea.kozharinov.practice3.simplefragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Fragment fragment1, fragment2;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button openFragment1Button = findViewById(R.id.fragment1Button);
        Button openFragment2Button = findViewById(R.id.fragment2Button);
        openFragment1Button.setOnClickListener(this::onClickOpenFragment1Button);
        openFragment2Button.setOnClickListener(this::onClickOpenFragment2Button);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
    }

    private void onClickOpenFragment1Button(View view) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment1)
                .commit();
    }

    private void onClickOpenFragment2Button(View view) {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment2)
                .commit();
    }
}