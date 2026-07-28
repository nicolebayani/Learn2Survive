package android.bignerdranch.learn2survive.ui.home;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.data.repository.UserRepositoryImpl;
import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.domain.repository.UserRepository;
import android.bignerdranch.learn2survive.ui.custom.AchievementCard;
import android.bignerdranch.learn2survive.ui.custom.XPProgressBar;

public class ProfileFragment extends Fragment {
    private ImageView profileAvatar;
    private TextView profileUsername;
    private TextView profileEmail;
    private TextView profileLevel;
    private XPProgressBar profileXPProgressBar;
    private TextView profileCoins;
    private TextView profileStreak;
    private TextView profileCompletedLessons;
    private TextView profileCompletedQuizzes;
    private TextView profileTotalXP;
    private TextView profileAccuracy;
    private RecyclerView profileAchievementsRecyclerView;
    private Button editProfileButton;
    
    private UserRepository userRepository;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        userRepository = new UserRepositoryImpl();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser() != null 
                ? FirebaseAuth.getInstance().getCurrentUser().getUid() 
                : null;
        
        // Initialize views
        profileAvatar = view.findViewById(R.id.profileAvatar);
        profileUsername = view.findViewById(R.id.profileUsername);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileLevel = view.findViewById(R.id.profileLevel);
        profileXPProgressBar = view.findViewById(R.id.profileXPProgressBar);
        profileCoins = view.findViewById(R.id.profileCoins);
        profileStreak = view.findViewById(R.id.profileStreak);
        profileCompletedLessons = view.findViewById(R.id.profileCompletedLessons);
        profileCompletedQuizzes = view.findViewById(R.id.profileCompletedQuizzes);
        profileTotalXP = view.findViewById(R.id.profileTotalXP);
        profileAccuracy = view.findViewById(R.id.profileAccuracy);
        profileAchievementsRecyclerView = view.findViewById(R.id.profileAchievementsRecyclerView);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        
        // Setup RecyclerView
        setupAchievementsRecyclerView();
        
        // Load user data
        if (currentUserId != null) {
            loadUserData();
        }
        
        // Setup button click listener
        editProfileButton.setOnClickListener(v -> {
            // TODO: Open edit profile dialog/activity
            animateButtonPress(editProfileButton);
        });
        
        // Animate cards on load
        animateCardsOnLoad(view);
    }
    
    private void setupAchievementsRecyclerView() {
        profileAchievementsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Create and set adapter for achievements
        List<AchievementCard> achievementCards = new ArrayList<>();
        // profileAchievementsRecyclerView.setAdapter(new AchievementsAdapter(achievementCards));
    }
    
    private void loadUserData() {
        userRepository.getUserData(currentUserId, new UserRepository.UserDataCallback() {
            @Override
            public void onSuccess(User user) {
                updateUIWithUserData(user);
            }
            
            @Override
            public void onFailure(String errorMessage) {
                // Handle error
            }
        });
    }
    
    private void updateUIWithUserData(User user) {
        if (getContext() == null) return;
        
        // Update username and email
        profileUsername.setText(user.getFullName() != null ? user.getFullName() : "Survivor");
        profileEmail.setText(user.getEmail() != null ? user.getEmail() : "");
        
        // Update level badge
        profileLevel.setText("Level " + user.getLevel());
        
        // Update XP progress bar with animation
        profileXPProgressBar.setMaxProgress(user.getMaxXP());
        profileXPProgressBar.setProgress(user.getCurrentXP());
        
        // Update coins and streak
        profileCoins.setText(String.valueOf(user.getCoins()));
        profileStreak.setText(String.valueOf(user.getDailyStreak()));
        
        // Update statistics
        profileCompletedLessons.setText(String.valueOf(user.getCompletedLessons()));
        profileCompletedQuizzes.setText(String.valueOf(user.getCompletedQuizzes()));
        
        // Calculate total XP (current level XP + accumulated XP from previous levels)
        int totalXP = calculateTotalXP(user.getLevel(), user.getCurrentXP());
        profileTotalXP.setText(String.valueOf(totalXP));
        
        // Calculate accuracy (mock calculation - should be based on actual quiz data)
        int accuracy = calculateAccuracy(user);
        profileAccuracy.setText(accuracy + "%");
        
        // Update avatar
        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(user.getProfileImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(profileAvatar);
        }
        
        // Animate statistics
        animateStatistics(user);
    }
    
    private int calculateTotalXP(int level, int currentXP) {
        // Simple calculation: (level - 1) * 100 + currentXP
        // This is a simplified version - actual formula may vary
        return (level - 1) * 100 + currentXP;
    }
    
    private int calculateAccuracy(User user) {
        // Mock calculation - should be based on actual quiz performance data
        // For now, return a reasonable default
        if (user.getCompletedQuizzes() == 0) {
            return 0;
        }
        
        // This would normally be calculated from stored quiz statistics
        // For now, return a placeholder value
        return 75; // Placeholder accuracy percentage
    }
    
    private void animateStatistics(User user) {
        // Animate completed lessons
        animateTextView(profileCompletedLessons, 0, user.getCompletedLessons());
        
        // Animate completed quizzes
        animateTextView(profileCompletedQuizzes, 0, user.getCompletedQuizzes());
        
        // Animate total XP
        int totalXP = calculateTotalXP(user.getLevel(), user.getCurrentXP());
        animateTextView(profileTotalXP, 0, totalXP);
        
        // Animate accuracy
        int accuracy = calculateAccuracy(user);
        animateTextView(profileAccuracy, 0, accuracy);
    }
    
    private void animateTextView(TextView textView, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(1000);
        animator.addUpdateListener(animation -> {
            textView.setText(String.valueOf(animation.getAnimatedValue()));
        });
        animator.start();
    }
    
    private void animateButtonPress(Button button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 0.95f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 0.95f);
        scaleX.setDuration(100);
        scaleY.setDuration(100);
        scaleX.start();
        scaleY.start();
        
        button.postDelayed(() -> {
            ObjectAnimator scaleBackX = ObjectAnimator.ofFloat(button, "scaleX", 0.95f, 1f);
            ObjectAnimator scaleBackY = ObjectAnimator.ofFloat(button, "scaleY", 0.95f, 1f);
            scaleBackX.setDuration(100);
            scaleBackY.setDuration(100);
            scaleBackX.start();
            scaleBackY.start();
        }, 100);
    }
    
    private void animateCardsOnLoad(View view) {
        View[] cards = {
                view.findViewById(R.id.profileHeaderCard),
                view.findViewById(R.id.xpCoinsCard),
                view.findViewById(R.id.statisticsCard),
                view.findViewById(R.id.achievementsSectionTitle),
                view.findViewById(R.id.editProfileButton)
        };
        
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                cards[i].setAlpha(0f);
                cards[i].setTranslationY(50f);
                
                cards[i].postDelayed(() -> {
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(cards[i], "alpha", 0f, 1f);
                    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(cards[i], "translationY", 50f, 0f);
                    
                    alphaAnimator.setDuration(500);
                    translationAnimator.setDuration(500);
                    
                    alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    translationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    
                    alphaAnimator.start();
                    translationAnimator.start();
                }, i * 100);
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (currentUserId != null) {
            loadUserData();
        }
    }
}
