package ru.mirea.kozharinov.practice3.mireaproject.ui.photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentPhotoBinding;

public class PhotoFragment extends Fragment {

    private FragmentPhotoBinding binding;

    private MutableLiveData<Uri> imageURI;
    private boolean hasPermissions = false;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private static final int REQUEST_PERMISSION = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPhotoBinding.inflate(getLayoutInflater());
        imageURI = new MutableLiveData<>();
        int cameraPermissionStatus =
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        int storagePermissionStatus =
                ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
                && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            hasPermissions = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[] {
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_PERMISSION);
        }
        binding.makePhotoButton.setOnClickListener(this::onClickMakePhotoButton);
        binding.savePhotoButton.setOnClickListener(this::onClickSaveButton);
        activityResultLauncher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        this::onActivityResult);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (imageURI.getValue() == null) {
            binding.imageView.setVisibility(View.INVISIBLE);
            binding.savePhotoButton.setEnabled(false);
        } else {
            binding.imageView.setImageURI(imageURI.getValue());
        }
        return binding.getRoot();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {
        if (imageURI == null) {
            binding.imageView.setVisibility(View.INVISIBLE);
        }
        super.onResume();
    }

    private void onClickSaveButton(View view) {
        Toast.makeText(requireContext(),
                "Изображение сохранено в " + Objects.requireNonNull(imageURI.getValue()),
                Toast.LENGTH_LONG).show();
        binding.savePhotoButton.setEnabled(false);
        binding.makePhotoButton.setEnabled(true);
        binding.noPhotoTextView.setVisibility(View.VISIBLE);
        binding.imageView.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void onClickMakePhotoButton(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(requireActivity().getPackageManager()) != null
                && hasPermissions) {
            try {
                @SuppressLint("SimpleDateFormat")
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName  = "IMAGE_" + timeStamp + "_";
                File storageDirectory =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                String authorities = requireContext().getPackageName() + ".fileprovider";
                imageURI.setValue(FileProvider.getUriForFile(requireContext(), authorities, imageFile));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI.getValue());
                activityResultLauncher.launch(cameraIntent);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("onClickMakePhotoButton", e.getMessage());
                Toast.makeText(requireContext(),
                        "You have no permissions",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            binding.noPhotoTextView.setVisibility(View.INVISIBLE);
            binding.imageView.setVisibility(View.VISIBLE);
            binding.imageView.setImageURI(imageURI.getValue());
            binding.savePhotoButton.setEnabled(true);
            binding.makePhotoButton.setEnabled(false);
        } else {
            binding.noPhotoTextView.setVisibility(View.VISIBLE);
            Log.e("onActivityResult",
                    String.format("%d resultCode: %d",
                            result.describeContents(),
                            result.getResultCode()));
            Toast.makeText(requireContext(),
                    "Something wrong with your camera",
                    Toast.LENGTH_LONG).show();
        }
    }
}
