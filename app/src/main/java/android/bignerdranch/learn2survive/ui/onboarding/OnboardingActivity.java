package android.bignerdranch.learn2survive.ui.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.databinding.ActivityOnboardingBinding;
import android.bignerdranch.learn2survive.ui.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {
    private ActivityOnboardingBinding binding;
    private OnboardingAdapter adapter;
    private static final String PREFS_NAME = "Learn2SurvivePrefs";
    private static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupOnboardingData();
        setupViewPager();
        setupDots(0);
        setupListeners();
    }

    private void setupOnboardingData() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();
        onboardingItems.add(new OnboardingItem(
                R.string.onboarding_title_1,
                R.string.onboarding_desc_1,
                "preparedness.json"
        ));
        onboardingItems.add(new OnboardingItem(
                R.string.onboarding_title_2,
                R.string.onboarding_desc_2,
                "information.json"
        ));
        onboardingItems.add(new OnboardingItem(
                R.string.onboarding_title_3,
                R.string.onboarding_desc_3,
                "community.json"
        ));

        adapter = new OnboardingAdapter(onboardingItems);
        binding.viewPager.setAdapter(adapter);
    }

    private void setupViewPager() {
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setupDots(position);
                updateButtonText(position);
            }
        });
    }

    private void setupDots(int position) {
        binding.dotsContainer.removeAllViews();
        int dotCount = adapter.getItemCount();

        for (int i = 0; i < dotCount; i++) {
            ImageView dot = new ImageView(this);
            int dotSize = 12;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotSize, dotSize);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);

            if (i == position) {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_active));
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_inactive));
            }

            binding.dotsContainer.addView(dot);
        }
    }

    private void updateButtonText(int position) {
        if (position == adapter.getItemCount() - 1) {
            binding.nextButton.setText(R.string.get_started);
        } else {
            binding.nextButton.setText(R.string.next);
        }
    }

    private void setupListeners() {
        binding.skipButton.setOnClickListener(v -> {
            completeOnboarding();
        });

        binding.nextButton.setOnClickListener(v -> {
            int currentItem = binding.viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                binding.viewPager.setCurrentItem(currentItem + 1);
            } else {
                completeOnboarding();
            }
        });
    }

    private void completeOnboarding() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply();

        Intent intent = new Intent(this, LoginActivity.class);
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
