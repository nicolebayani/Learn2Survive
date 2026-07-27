package android.bignerdranch.learn2survive.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import android.bignerdranch.learn2survive.databinding.ActivityForgotPasswordBinding;
import android.bignerdranch.learn2survive.di.DependencyProvider;
import android.bignerdranch.learn2survive.ui.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding> {
    private AuthViewModel viewModel;

    @Override
    protected ActivityForgotPasswordBinding getViewBinding() {
        return ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        viewModel = DependencyProvider.provideAuthViewModel();

        binding.backButton.setOnClickListener(v -> finish());

        binding.resetButton.setOnClickListener(v -> attemptPasswordReset());

        binding.backToLoginText.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
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
    }

    private void attemptPasswordReset() {
        String email = binding.emailEditText.getText().toString().trim();

        if (!validateEmail(email)) {
            return;
        }

        viewModel.forgotPassword(email);
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            binding.emailInputLayout.setError("Email is required");
            return false;
        } else if (!viewModel.validateEmail(email)) {
            binding.emailInputLayout.setError("Invalid email address");
            return false;
        }
        return true;
    }

    @Override
    protected void observeData() {
        viewModel.getLoadingLiveData().observe(this, isLoading -> {
            binding.loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.resetButton.setEnabled(!isLoading);
            binding.emailEditText.setEnabled(!isLoading);
        });

        viewModel.getSuccessMessageLiveData().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                // Navigate back to login after successful reset
                new android.os.Handler(android.os.Looper.getMainLooper()).postDelayed(() -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }, 2000);
            }
        });

        viewModel.getErrorLiveData().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }
}
