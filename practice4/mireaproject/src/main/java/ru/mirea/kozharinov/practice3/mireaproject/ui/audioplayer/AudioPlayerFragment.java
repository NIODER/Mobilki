package ru.mirea.kozharinov.practice3.mireaproject.ui.audioplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import ru.mirea.kozharinov.practice3.mireaproject.AudioPlayer;
import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentAudioplayerBinding;
import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentBrowserBinding;

public class AudioPlayerFragment extends Fragment {

    private static final String TAG = "AudioPlayer";
    private FragmentAudioplayerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentAudioplayerBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding.playButton.setOnClickListener(this::onClickPlay);
        binding.stopButton.setOnClickListener(this::onClickStop);
        return binding.getRoot();
    }

    private void onClickPlay(View view) {
        requireContext().startService(new Intent(getContext(), AudioPlayer.class));
    }

    public void onClickStop(View view) {
        requireContext().stopService(new Intent(getContext(), AudioPlayer.class));
    }
}
