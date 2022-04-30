package ru.mirea.kozharinov.practice3.mireaproject.ui.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

public class GravityEventListener implements SensorEventListener {

    private final SensorsEventListener sensorsEventListener;

    public GravityEventListener(@NonNull SensorManager sensorManager,
                                SensorsEventListener sensorsEventListener) {
        this.sensorsEventListener = sensorsEventListener;
        Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        DataUpdateObject dataUpdateObject =
                new DataUpdateObject(null, null, sensorEvent.values);
        sensorsEventListener.eventAll(dataUpdateObject);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
