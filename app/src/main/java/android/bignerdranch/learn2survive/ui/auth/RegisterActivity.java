package android.bignerdranch.learn2survive.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import android.bignerdranch.learn2survive.databinding.ActivityRegisterBinding;
import android.bignerdranch.learn2survive.di.DependencyProvider;
import android.bignerdranch.learn2survive.ui.base.BaseActivity;
import android.bignerdranch.learn2survive.ui.home.HomeActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    private AuthViewModel viewModel;

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        viewModel = DependencyProvider.provideAuthViewModel();

        binding.backButton.setOnClickListener(v -> finish());

        binding.registerButton.setOnClickListener(v -> attemptRegister());

        binding.loginText.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        setupTextWatchers();
    }

    private void setupTextWatchers() {
        binding.fullNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.fullNameInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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

        binding.confirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.confirmPasswordInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void attemptRegister() {
        String fullName = binding.fullNameEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

        if (!validateInputs(fullName, email, password, confirmPassword)) {
            return;
        }

        viewModel.register(email, password, fullName);
    }

    private boolean validateInputs(String fullName, String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (fullName.isEmpty()) {
            binding.fullNameInputLayout.setError("Full name is required");
            isValid = false;
        } else if (fullName.length() < 2) {
            binding.fullNameInputLayout.setError("Full name must be at least 2 characters");
            isValid = false;
        }

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

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordInputLayout.setError("Please confirm your password");
            isValid = false;
        } else if (!viewModel.validatePasswordsMatch(password, confirmPassword)) {
            binding.confirmPasswordInputLayout.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }

    @Override
    protected void observeData() {
        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.registerButton.setEnabled(!isLoading);
            binding.fullNameEditText.setEnabled(!isLoading);
            binding.emailEditText.setEnabled(!isLoading);
            binding.passwordEditText.setEnabled(!isLoading);
            binding.confirmPasswordEditText.setEnabled(!isLoading);
        });

        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
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

        viewModel.getSuccessMessageLiveData().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
