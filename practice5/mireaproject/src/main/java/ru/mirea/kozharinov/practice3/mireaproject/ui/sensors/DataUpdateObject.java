package ru.mirea.kozharinov.practice3.mireaproject.ui.sensors;

import androidx.annotation.Nullable;

public class DataUpdateObject {

    private static final float[] defaultValue = { -1000, -1000, -1000 };

    private final float acceleratorX;
    private final float acceleratorY;
    private final float acceleratorZ;

    private final float light;

    private final float gravityX;
    private final float gravityY;
    private final float gravityZ;

    public DataUpdateObject(@Nullable float[] acceleratorValues,
                            @Nullable float[] lightValue,
                            @Nullable float[] gravityValue) {
        if (acceleratorValues == null) {
            acceleratorValues = defaultValue;
        }
        if (lightValue == null) {
            lightValue = defaultValue;
        }
        if (gravityValue == null) {
            gravityValue = defaultValue;
        }

        acceleratorX = acceleratorValues[0];
        acceleratorY = acceleratorValues[1];
        acceleratorZ = acceleratorValues[2];

        light = lightValue[0];

        gravityX = gravityValue[0];
        gravityY = gravityValue[1];
        gravityZ = gravityValue[2];
    }

    public static boolean isDefault(float value) {
        return value == -1000;
    }

    public float getAcceleratorX() {
        return acceleratorX;
    }

    public float getAcceleratorY() {
        return acceleratorY;
    }

    public float getAcceleratorZ() {
        return acceleratorZ;
    }

    public float getLight() {
        return light;
    }

    public float getGravityX() {
        return gravityX;
    }

    public float getGravityY() {
        return gravityY;
    }

    public float getGravityZ() {
        return gravityZ;
    }
}
