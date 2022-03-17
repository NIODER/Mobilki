package ru.mirea.kozharinov.layouttype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        // тут так ибо я не понял, что за TextView в мейне
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        editText.setText("New text in MIREA");
        Button button = (Button) findViewById(R.id.button3);
        button.setText("MireaButton");
        CheckBox checkBox = (CheckBox) findViewById(R.id.myCheckBox);
        checkBox.setChecked(true);

    }
}