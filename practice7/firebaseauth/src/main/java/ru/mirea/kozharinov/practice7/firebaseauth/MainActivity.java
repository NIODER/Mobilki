package ru.mirea.kozharinov.practice7.firebaseauth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.kozharinov.practice7.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.createButton.setOnClickListener(this::onClickCreateButton);
        binding.signInButton.setOnClickListener(this::onClickSignInButton);
        auth = FirebaseAuth.getInstance();
    }

//region onClickListeners

    private void onClickSignInButton(View view) {
        signIn(binding.editTextTextEmailAddress.getText().toString(),
                binding.editTextTextPassword.getText().toString());
    }
    private void onClickCreateButton(View view) {
        createAccount(binding.editTextTextEmailAddress.getText().toString(),
                binding.editTextTextPassword.getText().toString());
    }
    private void onClickSignOutButton(View view) {

    }
    private void onClickVerifyEmailButton(View view) {

    }

//endregion


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(@NonNull FirebaseUser user) {
        binding.statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                user.getEmail(),
                user.isEmailVerified()));
        binding.detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = binding.editTextTextEmailAddress.getText().toString();
        if (TextUtils.isEmpty(email)) {
            binding.editTextTextEmailAddress.setError("Required");
            valid = false;
        } else {
            binding.editTextTextEmailAddress.setError(null);
        }

        String password = binding.editTextTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            binding.editTextTextPassword.setError("Required");
            valid = false;
        } else {
            binding.editTextTextPassword.setError(null);
        }
        return valid;
    }

    private void createAccount(String email, String password) {
        Log.d("createAccount", "create " + email);
        if (!validateForm()) {
            return;
        }
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("createAccount", "createUser success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w("createAccount", "createUser failure", task.getException());
                        Toast.makeText(MainActivity.this,
                                "Auth failed",
                                Toast.LENGTH_LONG).show();

                        binding.statusTextView.setText(R.string.sign_out);
                        binding.detailTextView.setText(null);
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d("signIn", "sign in" + email);
        if (!validateForm()) {
            return;
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("signIn", "success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w("signIn", "failure");
                        Toast.makeText(MainActivity.this,
                                "Auth failed",
                                Toast.LENGTH_LONG).show();

                        binding.statusTextView.setText(R.string.sign_out);
                        binding.detailTextView.setText(null);
                    }

                    if (!task.isSuccessful()) {
                        binding.statusTextView.setText(R.string.auth_failed);
                    }
                });
    }

    private void signOut() {
        auth.signOut();
        binding.statusTextView.setText(R.string.sign_out);
        binding.detailTextView.setText(null);
    }
}