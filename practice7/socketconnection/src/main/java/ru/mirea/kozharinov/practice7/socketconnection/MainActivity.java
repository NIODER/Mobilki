package ru.mirea.kozharinov.practice7.socketconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ru.mirea.kozharinov.practice7.socketconnection.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(this::onClickButton);
    }

    private void onClickButton(View view) {
        new Thread(() -> {
            try {
                Socket socket =
                        new Socket(getString(R.string.time_url),
                                getResources().getInteger(R.integer.time_port));
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine();
                String time = reader.readLine();
                runOnUiThread(() -> binding.textView.setText(time));
                socket.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}