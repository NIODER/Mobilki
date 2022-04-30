package ru.mirea.kozharinov.practice5.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import ru.mirea.kozharinov.practice5.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 100;
    private MediaRecorder mediaRecorder;
    private boolean hasPermissions = false;
    private ActivityMainBinding binding;
    private File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.pauseButton.setOnClickListener(this::onClickPause);
        binding.startButton.setOnClickListener(this::onClickStart);
        binding.stopButton.setOnClickListener(this::onClickStop);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    },
                    REQUEST_CODE_PERMISSION);
        }

        mediaRecorder = new MediaRecorder();
    }

    private void onClickPause(View view) {

    }

    private void onClickStart(View view) {
        try {
            binding.startButton.setEnabled(false);
            binding.stopButton.setEnabled(true);
            binding.pauseButton.setEnabled(true);
            binding.stopButton.requestFocus();
            startRecording();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onClickStop(View view) {
        binding.startButton.setEnabled(true);
        binding.stopButton.setEnabled(false);
        binding.pauseButton.setEnabled(false);
        binding.startButton.requestFocus();
        stopRecording();
        processAudioFile();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            hasPermissions = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                audioFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_MUSIC), "mirea.3gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        ContentValues contentValues = new ContentValues();
        long current = System.currentTimeMillis();
        contentValues.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        contentValues.put(MediaStore.Audio.Media.DATE_ADDED, (int) current / 1000);
        contentValues.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        contentValues.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver resolver = getContentResolver();
        Uri baseURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newURI = resolver.insert(baseURI, contentValues);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newURI));
    }
}