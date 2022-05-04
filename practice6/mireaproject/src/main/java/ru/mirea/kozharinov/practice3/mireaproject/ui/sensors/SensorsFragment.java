package ru.mirea.kozharinov.practice3.mireaproject.ui.sensors;

import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentSensorsBinding;

public class SensorsFragment extends Fragment implements SensorsSubscriber {

    private FragmentSensorsBinding binding;
    private SensorsEventListener sensorsEventListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentSensorsBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        SensorManager sensorManager =
                (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorsEventListener = new SensorsEventListener(sensorManager);
        sensorsEventListener.subscribe(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        sensorsEventListener.unsubscribe(this);
        super.onPause();
    }

    @Override
    public void notify(DataUpdateObject data) {
        if (!DataUpdateObject.isDefault(data.getAcceleratorX())) {
            binding.accelerometerXValue.setText(String.valueOf(data.getAcceleratorX()));
            binding.accelerometerYValue.setText(String.valueOf(data.getAcceleratorY()));
            binding.accelerometerZValue.setText(String.valueOf(data.getAcceleratorZ()));
        }
        if (!DataUpdateObject.isDefault(data.getLight())) {
            binding.lightValue.setText(String.valueOf(data.getLight()));
        }
        if (!DataUpdateObject.isDefault(data.getGravityX())) {
            binding.gravityXValue.setText(String.valueOf(data.getGravityX()));
            binding.gravityYValue.setText(String.valueOf(data.getGravityY()));
            binding.gravityZValue.setText(String.valueOf(data.getGravityZ()));
        }
    }
}
