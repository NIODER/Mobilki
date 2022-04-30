package ru.mirea.kozharinov.practice3.mireaproject.ui.sensors;

import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

public class SensorsEventListener {

    private final List<SensorsSubscriber> subscribers;

    private final AccelerometerEventListener accelerometerEventListener;
    private final LightEventListener lightEventListener;
    private final GravityEventListener gravityEventListener;
    private final SensorManager sensorManager;

    public SensorsEventListener(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        this.subscribers = new ArrayList<>();
        this.accelerometerEventListener = new AccelerometerEventListener(sensorManager, this);
        this.lightEventListener = new LightEventListener(sensorManager, this);
        this.gravityEventListener = new GravityEventListener(sensorManager, this);
    }

    public void eventAll(DataUpdateObject dataUpdateObject) {
        for (SensorsSubscriber subscriber:
             subscribers) {
            subscriber.notify(dataUpdateObject);
        }
    }

    public void subscribe(SensorsSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(SensorsSubscriber subscriber) {
        subscribers.remove(subscriber);
        if (subscribers.isEmpty()) {
            sensorManager.unregisterListener(accelerometerEventListener);
            sensorManager.unregisterListener(lightEventListener);
            sensorManager.unregisterListener(gravityEventListener);
        }
    }
}
