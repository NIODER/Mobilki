package ru.mirea.kozharinov.clickbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvOut;
    private Button buttonOk;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOut = (TextView) findViewById(R.id.tvOut);
        buttonCancel = (Button) findViewById(R.id.btnCancel);
        buttonOk = (Button) findViewById(R.id.btnOk);
        View.OnClickListener onClickListenerBtnOk = view -> tvOut.setText("Нажата кнопка ОК");
        View.OnClickListener onClickListenerBtnCancel =
                view -> tvOut.setText("Нажата кнопка Cancel");
        buttonOk.setOnClickListener(onClickListenerBtnOk);
        buttonCancel.setOnClickListener(onClickListenerBtnCancel);

    }

    public void onAnotherButtonClick(View view) {
        Toast.makeText(this, "Еще один способ", Toast.LENGTH_SHORT).show();
    }
}