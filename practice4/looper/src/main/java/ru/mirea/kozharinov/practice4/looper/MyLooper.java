package ru.mirea.kozharinov.practice4.looper;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {

    private int number = 0;
    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    Thread.sleep(19000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("MainActivity", msg.getData().getInt("AGE") + " " +
                        msg.getData().getString("PROF"));
                number++;
            }
        };
        Looper.loop();
    }
}
