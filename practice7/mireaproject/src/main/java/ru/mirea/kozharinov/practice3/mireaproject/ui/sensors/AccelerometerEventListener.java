package ru.mirea.kozharinov.practice3.mireaproject.ui.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;

public class AccelerometerEventListener implements SensorEventListener {

    private final SensorsEventListener sensorsEventListener;

    public AccelerometerEventListener(@NonNull SensorManager sensorManager,
                                      SensorsEventListener sensorsEventListener) {
        this.sensorsEventListener = sensorsEventListener;
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        DataUpdateObject dataUpdateObject =
                new DataUpdateObject(sensorEvent.values, null, null);
        sensorsEventListener.eventAll(dataUpdateObject);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
