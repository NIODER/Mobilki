package ru.mirea.kozharinov.practice5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        for (Sensor sensor :
                sensors) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("name", sensor.getName());
            stringObjectHashMap.put("value", sensor.getMaximumRange());
            hashMaps.add(stringObjectHashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                hashMaps,
                android.R.layout.simple_list_item_2,
                new String[] {"name", "value"},
                new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);
    }
}