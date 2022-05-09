package ru.mirea.kozharinov.practice3.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.kozharinov.practice3.mireaproject.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createAccountButton.setOnClickListener(this::onClickCreateAccount);
        binding.skipAuthButton.setOnClickListener(this::onClickSkipAuth);
        binding.singInButton.setOnClickListener(this::onClickSignIn);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

//region onClickListeners

    private void onClickSignIn(View view) {
        if (!validateForm()) {
            Toast.makeText(this, "Validation error", Toast.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                binding.editTextTextPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    }
                });
    }
    private void onClickCreateAccount(View view) {
        if (!validateForm()) {
            return;
        }

        auth.createUserWithEmailAndPassword(binding.editTextTextEmailAddress.getText().toString(),
                binding.editTextTextPassword.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(AuthActivity.this,
                                "Auth failed",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void onClickSkipAuth(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", "asdf");
        startActivity(intent);
    }

//endregion

    private boolean validateForm() {
        return !TextUtils.isEmpty(binding.editTextTextEmailAddress.getText().toString())
                && !TextUtils.isEmpty(binding.editTextTextPassword.getText().toString());
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user.isEmailVerified()) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("email", user.getEmail());
                startActivity(intent);
            } else {
                Toast.makeText(AuthActivity.this, "Auth Error", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AuthActivity.this, "Auth error", Toast.LENGTH_LONG).show();
        }
    }
}
