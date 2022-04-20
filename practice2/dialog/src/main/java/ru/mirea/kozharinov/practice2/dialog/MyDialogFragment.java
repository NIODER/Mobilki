package ru.mirea.kozharinov.practice2.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title)
                .setMessage(R.string.message)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.positiveButton_text,
                        this::positiveButtonAction)
                .setNeutralButton(R.string.neutralButton_text,
                        this::neutralButtonAction)
                .setNegativeButton(R.string.negativeButton_text,
                        this::negativeButtonAction);
        return builder.create();
    }

    private void positiveButtonAction(DialogInterface dialogInterface, int id) {
        Toast.makeText(requireActivity().getApplicationContext(),
                "Ok",
                Toast.LENGTH_SHORT).show();
        dialogInterface.cancel();
    }

    private void neutralButtonAction(DialogInterface dialogInterface, int id) {
        Toast.makeText(requireActivity().getApplicationContext(),
                "meh",
                Toast.LENGTH_SHORT).show();
        dialogInterface.cancel();
    }

    private void negativeButtonAction(DialogInterface dialogInterface, int id) {
        Toast.makeText(requireActivity().getApplicationContext(),
                "noo!",
                Toast.LENGTH_SHORT).show();
        dialogInterface.cancel();
    }
}
