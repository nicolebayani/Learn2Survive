package android.bignerdranch.learn2survive.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.databinding.ActivityLoginBinding;
import android.bignerdranch.learn2survive.di.DependencyProvider;
import android.bignerdranch.learn2survive.ui.base.BaseActivity;
import android.bignerdranch.learn2survive.ui.home.HomeActivity;
import android.bignerdranch.learn2survive.ui.onboarding.OnboardingActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private AuthViewModel viewModel;

    @Override
    protected ActivityLoginBinding getViewBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        viewModel = DependencyProvider.provideAuthViewModel();

        binding.backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            finish();
        });

        binding.loginButton.setOnClickListener(v -> attemptLogin());

        binding.forgotPasswordText.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        binding.registerText.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        setupTextWatchers();
    }

    private void setupTextWatchers() {
        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.emailInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.passwordInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void attemptLogin() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        if (!validateInputs(email, password)) {
            return;
        }

        viewModel.login(email, password);
    }

    private boolean validateInputs(String email, String password) {
        boolean isValid = true;

        if (email.isEmpty()) {
            binding.emailInputLayout.setError("Email is required");
            isValid = false;
        } else if (!viewModel.validateEmail(email)) {
            binding.emailInputLayout.setError("Invalid email address");
            isValid = false;
        }

        if (password.isEmpty()) {
            binding.passwordInputLayout.setError("Password is required");
            isValid = false;
        } else if (!viewModel.validatePassword(password)) {
            binding.passwordInputLayout.setError("Password must be at least 6 characters");
            isValid = false;
        }

        return isValid;
    }

    @Override
    protected void observeData() {
        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.loginButton.setEnabled(!isLoading);
            binding.emailEditText.setEnabled(!isLoading);
            binding.passwordEditText.setEnabled(!isLoading);
        });

        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
