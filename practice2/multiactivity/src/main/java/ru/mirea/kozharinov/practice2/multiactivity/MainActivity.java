package ru.mirea.kozharinov.practice2.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this::onClickNewActivity);
    }

    private void onClickNewActivity(View button) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", "MIREA - ФАМИЛИЯ ИМЯ ОТЧЕСТВО СТУДЕНТА");
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart1");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstance) {
        super.onRestoreInstanceState(savedInstance);
        Log.i(TAG, "onRestoreInstanceState1");
    }

    @Override
    protected void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        Log.i(TAG, "onPostCreate1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume1");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume1");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow1");
    }

    @Override
    protected  void onPause() {
        super.onPause();
        Log.i(TAG, "onPause1");
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Log.i(TAG, "onSaveInstanceState1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy1");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow1");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart1");
    }
}