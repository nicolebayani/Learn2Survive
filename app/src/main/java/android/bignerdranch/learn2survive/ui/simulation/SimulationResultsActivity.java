package android.bignerdranch.learn2survive.ui.simulation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.DisasterSimulationType;
import android.bignerdranch.learn2survive.domain.model.SimulationResult;
import android.bignerdranch.learn2survive.domain.repository.SimulationRepository;
import android.bignerdranch.learn2survive.data.remote.SimulationRepositoryImpl;

public class SimulationResultsActivity extends AppCompatActivity {
    public static final String EXTRA_SURVIVED = "survived";
    public static final String EXTRA_TIME_TAKEN = "time_taken";
    public static final String EXTRA_CORRECT_CHOICES = "correct_choices";
    public static final String EXTRA_TOTAL_CHOICES = "total_choices";
    public static final String EXTRA_XP_EARNED = "xp_earned";
    public static final String EXTRA_COINS_EARNED = "coins_earned";
    public static final String EXTRA_SCENARIO_TITLE = "scenario_title";
    public static final String EXTRA_SCENARIO_ID = "scenario_id";

    private TextView statusTextView;
    private TextView timeTextView;
    private TextView accuracyTextView;
    private TextView correctChoicesTextView;
    private TextView xpTextView;
    private TextView coinsTextView;
    private TextView starsTextView;
    private ImageView[] starImageViews;
    private LottieAnimationView resultAnimation;
    private ProgressBar progressBar;
    private Button retryButton;
    private Button homeButton;
    private Button viewSimulationsButton;

    private SimulationRepository simulationRepository;

    private boolean survived;
    private int timeTaken;
    private int correctChoices;
    private int totalChoices;
    private int xpEarned;
    private int coinsEarned;
    private String scenarioTitle;
    private String scenarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_results);

        simulationRepository = new SimulationRepositoryImpl();

        extractIntentData();
        initViews();
        displayResults();
        saveSimulationResult();
        updateUserRewards();
    }

    private void extractIntentData() {
        survived = getIntent().getBooleanExtra(EXTRA_SURVIVED, false);
        timeTaken = getIntent().getIntExtra(EXTRA_TIME_TAKEN, 0);
        correctChoices = getIntent().getIntExtra(EXTRA_CORRECT_CHOICES, 0);
        totalChoices = getIntent().getIntExtra(EXTRA_TOTAL_CHOICES, 0);
        xpEarned = getIntent().getIntExtra(EXTRA_XP_EARNED, 0);
        coinsEarned = getIntent().getIntExtra(EXTRA_COINS_EARNED, 0);
        scenarioTitle = getIntent().getStringExtra(EXTRA_SCENARIO_TITLE);
        scenarioId = getIntent().getStringExtra(EXTRA_SCENARIO_ID);
    }

    private void initViews() {
        statusTextView = findViewById(R.id.statusTextView);
        timeTextView = findViewById(R.id.timeTextView);
        accuracyTextView = findViewById(R.id.accuracyTextView);
        correctChoicesTextView = findViewById(R.id.correctChoicesTextView);
        xpTextView = findViewById(R.id.xpTextView);
        coinsTextView = findViewById(R.id.coinsTextView);
        starsTextView = findViewById(R.id.starsTextView);
        resultAnimation = findViewById(R.id.resultAnimation);
        progressBar = findViewById(R.id.progressBar);
        retryButton = findViewById(R.id.retryButton);
        homeButton = findViewById(R.id.homeButton);
        viewSimulationsButton = findViewById(R.id.viewSimulationsButton);

        starImageViews = new ImageView[3];
        starImageViews[0] = findViewById(R.id.star1);
        starImageViews[1] = findViewById(R.id.star2);
        starImageViews[2] = findViewById(R.id.star3);

        retryButton.setOnClickListener(v -> retrySimulation());
        homeButton.setOnClickListener(v -> goHome());
        viewSimulationsButton.setOnClickListener(v -> viewSimulations());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Simulation Results");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void displayResults() {
        double accuracy = totalChoices > 0 ? 
            (double) correctChoices / totalChoices * 100 : 0;
        int stars = calculateStars(accuracy, survived);

        statusTextView.setText(survived ? "SURVIVED" : "FAILED");
        statusTextView.setTextColor(survived ? 
            getColor(R.color.success) : getColor(R.color.error));

        timeTextView.setText(formatTime(timeTaken));
        accuracyTextView.setText(String.format("%.1f%%", accuracy));
        correctChoicesTextView.setText(correctChoices + "/" + totalChoices);
        xpTextView.setText("+" + xpEarned);
        coinsTextView.setText("+" + coinsEarned);
        starsTextView.setText(stars + "/3");

        displayStars(stars);

        if (survived) {
            resultAnimation.setAnimation("success.json");
        } else {
            resultAnimation.setAnimation("failure.json");
        }
        resultAnimation.playAnimation();

        progressBar.setProgress((int) accuracy);
    }

    private int calculateStars(double accuracy, boolean survived) {
        if (!survived) return 0;
        if (accuracy >= 90) return 3;
        if (accuracy >= 70) return 2;
        if (accuracy >= 50) return 1;
        return 0;
    }

    private void displayStars(int stars) {
        for (int i = 0; i < 3; i++) {
            if (i < stars) {
                starImageViews[i].setImageResource(R.drawable.ic_star_filled);
            } else {
                starImageViews[i].setImageResource(R.drawable.ic_star_outline);
            }
        }
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }

    private void saveSimulationResult() {
        double accuracy = totalChoices > 0 ? 
            (double) correctChoices / totalChoices * 100 : 0;
        int stars = calculateStars(accuracy, survived);

        SimulationResult result = new SimulationResult(
            null,
            getCurrentUserId(),
            scenarioId,
            scenarioTitle,
            DisasterSimulationType.EARTHQUAKE,
            new Date(),
            survived,
            timeTaken,
            correctChoices,
            totalChoices,
            accuracy,
            xpEarned,
            coinsEarned,
            stars
        );

        simulationRepository.saveSimulationResult(result, new SimulationRepository.SaveCallback() {
            @Override
            public void onSuccess(String documentId) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    private void updateUserRewards() {
        if (survived) {
            updateUserXP(xpEarned);
            updateUserCoins(coinsEarned);
        }
    }

    private void updateUserXP(int xp) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getCurrentUserId();
        
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long currentXP = documentSnapshot.getLong("xp");
                        if (currentXP == null) currentXP = 0L;
                        
                        db.collection("users").document(userId)
                                .update("xp", currentXP + xp)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    private void updateUserCoins(int coins) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = getCurrentUserId();
        
        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Long currentCoins = documentSnapshot.getLong("coins");
                        if (currentCoins == null) currentCoins = 0L;
                        
                        db.collection("users").document(userId)
                                .update("coins", currentCoins + coins)
                                .addOnSuccessListener(aVoid -> {
                                })
                                .addOnFailureListener(e -> {
                                });
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    private String getCurrentUserId() {
        return "user_id";
    }

    private void retrySimulation() {
        Intent intent = new Intent(this, SimulationActivity.class);
        intent.putExtra(SimulationActivity.EXTRA_SCENARIO_ID, scenarioId);
        startActivity(intent);
        finish();
    }

    private void goHome() {
        finish();
    }

    private void viewSimulations() {
        Intent intent = new Intent(this, SimulationsListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
