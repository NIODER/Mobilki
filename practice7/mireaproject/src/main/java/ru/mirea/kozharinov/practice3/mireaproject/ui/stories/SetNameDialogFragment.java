package ru.mirea.kozharinov.practice3.mireaproject.ui.stories;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.kozharinov.practice3.mireaproject.R;
import ru.mirea.kozharinov.practice3.mireaproject.databinding.SetStoryNameViewBinding;

public class SetNameDialogFragment extends DialogFragment {

    private SetStoryNameViewBinding binding;
    private StoriesViewModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = SetStoryNameViewBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage(R.string.set_story_name)
                .setPositiveButton(R.string.set_button, this::onClickOk)
                .setPositiveButton(R.string.decline_button, this::onClickDecline)
                .setView(R.layout.set_story_name_view);
        return builder.create();
    }

    private void onClickOk(DialogInterface dialogInterface, int id) {
        model = new ViewModelProvider(requireActivity()).get(StoriesViewModel.class);
        model.setName(binding.editTextStoryName.getText().toString());
    }

    private void onClickDecline(DialogInterface dialogInterface, int id) {
        model = new ViewModelProvider(requireActivity()).get(StoriesViewModel.class);
        model.setName("");
    }
}
