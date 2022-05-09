package ru.mirea.kozharinov.practice7.httpurlconnection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ru.mirea.kozharinov.practice7.httpurlconnection.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        @SuppressLint({"NewApi", "LocalSuppress"})
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread(getInfo).start();
        }
    }

    private final Runnable getInfo = () -> {
        IPGetter ipGetter = new IPGetter();
        try {
            IPGetter.Result info = ipGetter.getInfo();
            runOnUiThread(() -> setInfo(info));
        } catch (IOException e) {
            Toast.makeText(this, "Cant get ip", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    };

    private void setInfo(@NonNull IPGetter.Result result) {
        binding.ip.setText(result.getIp());
        binding.provider.setText(result.getProvider());
        binding.location.setText(result.getLocation());
    }
}