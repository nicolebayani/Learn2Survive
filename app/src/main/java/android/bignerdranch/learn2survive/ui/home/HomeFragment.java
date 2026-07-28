package android.bignerdranch.learn2survive.ui.home;

import android.animation.ObjectAnimator;
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
import android.bignerdranch.learn2survive.ui.custom.StatCard;
import android.bignerdranch.learn2survive.ui.custom.XPProgressBar;

public class HomeFragment extends Fragment {
    private XPProgressBar xpProgressBar;
    private TextView usernameText;
    private TextView welcomeMessage;
    private TextView coinsText;
    private TextView streakText;
    private TextView levelBadge;
    private ImageView userAvatar;
    private StatCard lessonsCompletedCard;
    private StatCard quizzesCompletedCard;
    private StatCard achievementsCard;
    private RecyclerView achievementsRecyclerView;
    private RecyclerView disasterModulesRecyclerView;
    private Button startChallengeButton;
    
    private UserRepository userRepository;
    private String currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setupViews(view);
        return view;
    }

    private void setupViews(View view) {
        userRepository = new UserRepositoryImpl();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser() != null 
                ? FirebaseAuth.getInstance().getCurrentUser().getUid() 
                : null;
        
        // Initialize views
        xpProgressBar = view.findViewById(R.id.xpProgressBar);
        usernameText = view.findViewById(R.id.username);
        welcomeMessage = view.findViewById(R.id.welcomeMessage);
        coinsText = view.findViewById(R.id.coinsText);
        streakText = view.findViewById(R.id.streakText);
        levelBadge = view.findViewById(R.id.levelBadge);
        userAvatar = view.findViewById(R.id.userAvatar);
        lessonsCompletedCard = view.findViewById(R.id.lessonsCompletedCard);
        quizzesCompletedCard = view.findViewById(R.id.quizzesCompletedCard);
        achievementsCard = view.findViewById(R.id.achievementsCard);
        achievementsRecyclerView = view.findViewById(R.id.achievementsRecyclerView);
        disasterModulesRecyclerView = view.findViewById(R.id.disasterModulesRecyclerView);
        startChallengeButton = view.findViewById(R.id.startChallengeButton);
        
        // Setup RecyclerViews
        setupAchievementsRecyclerView();
        setupDisasterModulesRecyclerView();
        
        // Load user data
        if (currentUserId != null) {
            loadUserData();
            updateDailyStreak();
        }
        
        // Setup button animations
        setupButtonAnimations();
        
        // Animate cards on load
        animateCardsOnLoad(view);
    }
    
    private void setupAchievementsRecyclerView() {
        achievementsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Create and set adapter for achievements
        List<AchievementCard> achievementCards = new ArrayList<>();
        // achievementsRecyclerView.setAdapter(new AchievementsAdapter(achievementCards));
    }
    
    private void setupDisasterModulesRecyclerView() {
        disasterModulesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // TODO: Create and set adapter for disaster modules
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
        
        // Update username
        usernameText.setText(user.getFullName() != null ? user.getFullName() : "Survivor");
        
        // Set welcome message based on time of day
        setWelcomeMessage();
        
        // Update level badge
        levelBadge.setText("Lvl " + user.getLevel());
        
        // Update XP progress bar with animation
        xpProgressBar.setMaxProgress(user.getMaxXP());
        xpProgressBar.setProgress(user.getCurrentXP());
        
        // Update coins and streak
        coinsText.setText(user.getCoins() + " Coins");
        streakText.setText(user.getDailyStreak() + " Day Streak");
        
        // Update avatar
        if (user.getProfileImageUrl() != null && !user.getProfileImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(user.getProfileImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(userAvatar);
        }
        
        // Update stat cards with animation
        lessonsCompletedCard.animateValue(0, user.getCompletedLessons());
        quizzesCompletedCard.animateValue(0, user.getCompletedQuizzes());
        achievementsCard.animateValue(0, user.getAchievements() != null ? user.getAchievements().size() : 0);
    }
    
    private void setWelcomeMessage() {
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        String message;
        
        if (hour >= 5 && hour < 12) {
            message = "Good morning!";
        } else if (hour >= 12 && hour < 17) {
            message = "Good afternoon!";
        } else if (hour >= 17 && hour < 21) {
            message = "Good evening!";
        } else {
            message = "Welcome back!";
        }
        
        welcomeMessage.setText(message);
    }
    
    private void updateDailyStreak() {
        userRepository.updateStreak(currentUserId, new UserRepository.UserDataCallback() {
            @Override
            public void onSuccess(User user) {
                streakText.setText(user.getDailyStreak() + " Day Streak");
            }
            
            @Override
            public void onFailure(String errorMessage) {
                // Handle error
            }
        });
    }
    
    private void setupButtonAnimations() {
        startChallengeButton.setOnClickListener(v -> {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(startChallengeButton, "scaleX", 1f, 0.95f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(startChallengeButton, "scaleY", 1f, 0.95f);
            scaleX.setDuration(100);
            scaleY.setDuration(100);
            scaleX.start();
            scaleY.start();
            
            startChallengeButton.postDelayed(() -> {
                ObjectAnimator scaleBackX = ObjectAnimator.ofFloat(startChallengeButton, "scaleX", 0.95f, 1f);
                ObjectAnimator scaleBackY = ObjectAnimator.ofFloat(startChallengeButton, "scaleY", 0.95f, 1f);
                scaleBackX.setDuration(100);
                scaleBackY.setDuration(100);
                scaleBackX.start();
                scaleBackY.start();
                
                // TODO: Start daily challenge logic
            }, 100);
        });
    }
    
    private void animateCardsOnLoad(View view) {
        View[] cards = {
                view.findViewById(R.id.welcomeCard),
                view.findViewById(R.id.dailyChallengeCard),
                view.findViewById(R.id.continueLearningCard),
                view.findViewById(R.id.statsRow),
                view.findViewById(R.id.leaderboardCard)
        };
        
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                cards[i].setAlpha(0f);
                cards[i].setTranslationY(50f);
                
                final View card = cards[i];
                card.postDelayed(() -> {
                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(card, "alpha", 0f, 1f);
                    ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(card, "translationY", 50f, 0f);
                    
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
