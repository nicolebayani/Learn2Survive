package android.bignerdranch.learn2survive.ui.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.learn2survive.databinding.ActivitySplashBinding;
import android.bignerdranch.learn2survive.di.DependencyProvider;
import android.bignerdranch.learn2survive.ui.auth.LoginActivity;
import android.bignerdranch.learn2survive.ui.home.HomeActivity;
import android.bignerdranch.learn2survive.ui.onboarding.OnboardingActivity;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private static final String PREFS_NAME = "Learn2SurvivePrefs";
    private static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";
    private static final long SPLASH_DURATION = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupAnimations();
        navigateAfterDelay();
    }

    private void setupAnimations() {
        binding.loadingIndicator.setVisibility(android.view.View.VISIBLE);
    }

    private void navigateAfterDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            checkUserStatusAndNavigate();
        }, SPLASH_DURATION);
    }

    private void checkUserStatusAndNavigate() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean onboardingCompleted = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false);
        boolean isLoggedIn = DependencyProvider.provideAuthRepository().isLoggedIn();

        Intent intent;
        if (isLoggedIn) {
            intent = new Intent(this, HomeActivity.class);
        } else if (onboardingCompleted) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, OnboardingActivity.class);
        }

        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
