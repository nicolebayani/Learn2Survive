package android.bignerdranch.learn2survive.ui.home;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.data.repository.UserRepositoryImpl;
import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.domain.repository.UserRepository;
import android.bignerdranch.learn2survive.ui.custom.StatCard;
import android.bignerdranch.learn2survive.ui.simulation.ImmersiveScenarioActivity;

public class HomeFragment extends Fragment {
    private TextView usernameText;
    private TextView levelBadge;
    private TextView coinsText;
    private TextView streakText;
    private ImageView userAvatar;
    private StatCard lessonsCompletedCard;
    private StatCard quizzesCompletedCard;
    private StatCard achievementsCard;
    private Button startScenarioButton;
    private TextView scenarioProgressText;
    private ProgressBar scenarioProgressBar;
    private TextView scenarioStatusText;

    // Disaster category cards
    private androidx.cardview.widget.CardView earthquakeCard;
    private androidx.cardview.widget.CardView typhoonCard;
    private androidx.cardview.widget.CardView floodCard;
    private androidx.cardview.widget.CardView fireCard;

    // Disaster progress bars
    private ProgressBar earthquakeProgressBar;
    private ProgressBar typhoonProgressBar;
    private ProgressBar floodProgressBar;
    private ProgressBar fireProgressBar;

    // Disaster progress text
    private TextView earthquakeProgressText;
    private TextView typhoonProgressText;
    private TextView floodProgressText;
    private TextView fireProgressText;

    // Quick access cards
    private androidx.cardview.widget.CardView learningCard;
    private androidx.cardview.widget.CardView gamesCard;
    
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

        // Initialize user stats views
        usernameText = view.findViewById(R.id.username);
        levelBadge = view.findViewById(R.id.levelBadge);
        coinsText = view.findViewById(R.id.coinsText);
        streakText = view.findViewById(R.id.streakText);
        userAvatar = view.findViewById(R.id.userAvatar);

        // Initialize scenario views
        startScenarioButton = view.findViewById(R.id.startScenarioButton);
        scenarioProgressText = view.findViewById(R.id.scenarioProgressText);
        scenarioProgressBar = view.findViewById(R.id.scenarioProgressBar);
        scenarioStatusText = view.findViewById(R.id.scenarioStatusText);

        // Initialize disaster category cards
        earthquakeCard = view.findViewById(R.id.earthquakeCard);
        typhoonCard = view.findViewById(R.id.typhoonCard);
        floodCard = view.findViewById(R.id.floodCard);
        fireCard = view.findViewById(R.id.fireCard);

        // Initialize disaster progress bars
        earthquakeProgressBar = view.findViewById(R.id.earthquakeProgressBar);
        typhoonProgressBar = view.findViewById(R.id.typhoonProgressBar);
        floodProgressBar = view.findViewById(R.id.floodProgressBar);
        fireProgressBar = view.findViewById(R.id.fireProgressBar);

        // Initialize disaster progress text
        earthquakeProgressText = view.findViewById(R.id.earthquakeProgressText);
        typhoonProgressText = view.findViewById(R.id.typhoonProgressText);
        floodProgressText = view.findViewById(R.id.floodProgressText);
        fireProgressText = view.findViewById(R.id.fireProgressText);

        // Initialize stat cards
        lessonsCompletedCard = view.findViewById(R.id.lessonsCompletedCard);
        quizzesCompletedCard = view.findViewById(R.id.quizzesCompletedCard);
        achievementsCard = view.findViewById(R.id.achievementsCard);

        // Initialize quick access cards
        learningCard = view.findViewById(R.id.learningCard);
        gamesCard = view.findViewById(R.id.gamesCard);

        // Setup click listeners
        setupScenarioNavigation();
        setupDisasterCategoryNavigation();
        setupQuickAccessNavigation();

        // Load user data
        if (currentUserId != null) {
            loadUserData();
            updateDailyStreak();
        }

        // Update scenario progress
        updateScenarioProgress();

        // Animate cards on load
        animateCardsOnLoad(view);
    }

    private void setupScenarioNavigation() {
        startScenarioButton.setOnClickListener(v -> {
            launchScenario("earthquake_first_60_seconds");
        });
    }

    private void setupDisasterCategoryNavigation() {
        earthquakeCard.setOnClickListener(v -> {
            launchScenario("earthquake_first_60_seconds");
        });

        typhoonCard.setOnClickListener(v -> {
            launchScenario("typhoon_house_defender");
        });

        floodCard.setOnClickListener(v -> {
            launchScenario("flood_rescue");
        });

        fireCard.setOnClickListener(v -> {
            launchScenario("fire_escape");
        });
    }

    private void setupQuickAccessNavigation() {
        learningCard.setOnClickListener(v -> {
            // Navigate to LearningActivity
            Intent intent = new Intent(getContext(), android.bignerdranch.learn2survive.ui.learning.LearningActivity.class);
            startActivity(intent);
        });

        gamesCard.setOnClickListener(v -> {
            // Navigate to GamesListActivity
            Intent intent = new Intent(getContext(), android.bignerdranch.learn2survive.ui.games.GamesListActivity.class);
            startActivity(intent);
        });
    }

    private void launchScenario(String scenarioId) {
        Intent intent = new Intent(getContext(), android.bignerdranch.learn2survive.ui.simulation.ImmersiveScenarioActivity.class);
        intent.putExtra(ImmersiveScenarioActivity.EXTRA_SCENARIO_ID, scenarioId);
        startActivity(intent);
    }

    private void updateScenarioProgress() {
        // For now, set initial progress to 0
        // This will be updated based on actual user progress from Firestore
        scenarioProgressText.setText("0/4");
        scenarioProgressBar.setProgress(0);

        // Update individual disaster progress
        earthquakeProgressBar.setProgress(0);
        typhoonProgressBar.setProgress(0);
        floodProgressBar.setProgress(0);
        fireProgressBar.setProgress(0);

        earthquakeProgressText.setText("Not Started");
        typhoonProgressText.setText("Not Started");
        floodProgressText.setText("Not Started");
        fireProgressText.setText("Not Started");
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

        // Update level badge
        levelBadge.setText("Level " + user.getLevel());

        // Update coins and streak
        coinsText.setText(String.valueOf(user.getCoins()));
        streakText.setText(String.valueOf(user.getDailyStreak()));

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

    private void animateCardsOnLoad(View view) {
        View[] cards = {
                view.findViewById(R.id.userStatsCard),
                view.findViewById(R.id.scenarioCard),
                view.findViewById(R.id.earthquakeCard),
                view.findViewById(R.id.typhoonCard),
                view.findViewById(R.id.floodCard),
                view.findViewById(R.id.fireCard),
                view.findViewById(R.id.quickAccessRow),
                view.findViewById(R.id.statsRow)
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
