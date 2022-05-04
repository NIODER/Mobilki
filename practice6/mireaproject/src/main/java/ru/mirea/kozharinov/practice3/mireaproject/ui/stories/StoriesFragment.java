package ru.mirea.kozharinov.practice3.mireaproject.ui.stories;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.mirea.kozharinov.practice3.mireaproject.database.stories.Stories;
import ru.mirea.kozharinov.practice3.mireaproject.database.stories.StoriesVideoDataBase;
import ru.mirea.kozharinov.practice3.mireaproject.database.stories.Video;
import ru.mirea.kozharinov.practice3.mireaproject.database.stories.VideoDAO;
import ru.mirea.kozharinov.practice3.mireaproject.databinding.FragmentStoriesBinding;

public class StoriesFragment extends Fragment {

    private FragmentStoriesBinding binding;
    private VideoDAO videoDAO;
    private ArrayList<Video> videos;

    private boolean hasPermissions = false;
    private static final int PERMISSION_CODE = 922;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updatePermissionStatus();

        binding = FragmentStoriesBinding.inflate(getLayoutInflater());
        binding.floatingActionButton.setOnClickListener(this::onClickCreateStoryButton);

        StoriesVideoDataBase dataBase = Stories.getInstance().getDataBase();
        videoDAO = dataBase.videoDAO();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::onActivityResult
        );
    }

    private void updatePermissionStatus() {
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
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                    }, PERMISSION_CODE);
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void onClickCreateStoryButton(View view) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null
                && hasPermissions) {
            try {
                @SuppressLint("SimpleDateFormat")
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "IMAGE_" + timeStamp + "_";
                File storageDirectory =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                File videoFile = File.createTempFile(fileName, ".3gp", storageDirectory);
                String authorities = requireContext().getPackageName() + ".fileprovider";
                Uri newStoryUri = FileProvider.getUriForFile(requireContext(), authorities, videoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, newStoryUri);
                activityResultLauncher.launch(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("onClickCreateStory", e.getMessage());
                Toast.makeText(requireContext(),
                        "Something went wrong",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onActivityResult(@NonNull ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                SetNameDialogFragment setNameDialogFragment = new SetNameDialogFragment();
                setNameDialogFragment.show(requireActivity().getSupportFragmentManager(),
                        "SetStoryName");

                StoriesViewModel model = new ViewModelProvider(requireActivity()).get(StoriesViewModel.class);
                Video video = new Video();
                if (model.getName() == null) {
                    video.name = "";
                } else {
                    video.name = model.getName().toString();
                }
                video.uri = result.getData().getData().toString();
                videoDAO.insert(video);
            } else {
                Log.e("GET VIDEO DATA", "result.getData() is null");
                Toast.makeText(requireContext(),
                        "Cant get uri on videoclip",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(requireContext(), "Camera error", Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = binding.storiesRecyclerView;
        StoriesRecyclerAdapter adapter = new StoriesRecyclerAdapter(requireContext(), videos);
        recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        videos = (ArrayList<Video>) videoDAO.getAll();
    }
}
