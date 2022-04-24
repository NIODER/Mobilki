package ru.mirea.kozharinov.practice3.systemintentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button callButton = findViewById(R.id.callButton);
        Button openBrowserButton = findViewById(R.id.openBrowserButton);
        Button openMapButton = findViewById(R.id.openMapButton);

        callButton.setOnClickListener(this::onClickCallButton);
        openBrowserButton.setOnClickListener(this::onClickOpenBrowserButton);
        openMapButton.setOnClickListener(this::onClickOpenMapButton);
    }

    private void onClickCallButton(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:89811112233"));
        startActivity(intent);
    }

    private void onClickOpenBrowserButton(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://developer.android.com"));
        startActivity(intent);
    }

    private void onClickOpenMapButton(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:55.749479,37.613944"));
        startActivity(intent);
    }
}