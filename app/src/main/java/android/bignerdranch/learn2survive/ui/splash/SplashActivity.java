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
        
        // Check if user is logged in AND has data in Firestore
        DependencyProvider.provideAuthRepository().getCurrentUser(new android.bignerdranch.learn2survive.domain.repository.AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(android.bignerdranch.learn2survive.domain.model.User user) {
                // User exists in both Firebase Auth and Firestore
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onFailure(String errorMessage) {
                // User not logged in or no data in Firestore
                Intent intent;
                if (onboardingCompleted) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, OnboardingActivity.class);
                }
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
