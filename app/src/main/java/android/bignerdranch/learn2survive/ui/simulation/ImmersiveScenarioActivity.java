package android.bignerdranch.learn2survive.ui.simulation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.data.repository.ScenarioRepository;
import android.bignerdranch.learn2survive.domain.engine.ScenarioEngine;
import android.bignerdranch.learn2survive.domain.model.DisasterCondition;
import android.bignerdranch.learn2survive.domain.model.EmergencySupply;
import android.bignerdranch.learn2survive.domain.model.ImmersiveScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioPhase;
import android.bignerdranch.learn2survive.domain.model.ScenarioStep;
import android.bignerdranch.learn2survive.domain.model.ScenarioStepChoice;
import android.bignerdranch.learn2survive.ui.simulation.adapters.SupplySelectionAdapter;

public class ImmersiveScenarioActivity extends AppCompatActivity {
    public static final String EXTRA_SCENARIO_ID = "scenario_id";

    // Warning Screen Views
    private LinearLayout warningScreen;
    private TextView warningIconTextView;
    private TextView warningTitleTextView;
    private LottieAnimationView warningAnimationView;
    private TextView warningMessageTextView;
    private TextView countdownTextView;
    private Button startScenarioButton;

    // Situation Screen Views
    private LinearLayout situationScreen;
    private TextView situationTitleTextView;
    private ImageView situationImageView;
    private TextView situationDescriptionTextView;
    private TextView situationQuestionTextView;
    private Button situationChoice1Button;
    private Button situationChoice2Button;
    private Button situationChoice3Button;

    // Supplies Screen Views
    private LinearLayout suppliesScreen;
    private TextView suppliesTitleTextView;
    private TextView suppliesLimitTextView;
    private TextView suppliesCountTextView;
    private TextView backpackStatusTextView;
    private RecyclerView suppliesRecyclerView;
    private Button confirmSuppliesButton;

    // Evacuation Screen Views
    private LinearLayout evacuationScreen;
    private TextView evacuationTitleTextView;
    private TextView evacuationDescriptionTextView;
    private Button evacuationChoice1Button;
    private Button evacuationChoice2Button;
    private Button evacuationChoice3Button;

    // Conditions Screen Views
    private LinearLayout conditionsScreen;
    private TextView conditionsTitleTextView;
    private LottieAnimationView conditionsAnimationView;
    private TextView conditionsDescriptionTextView;
    private TextView conditionsQuestionTextView;
    private Button conditionsChoice1Button;
    private Button conditionsChoice2Button;

    // Consequences Screen Views
    private LinearLayout consequencesScreen;
    private TextView consequencesTitleTextView;
    private LottieAnimationView consequencesAnimationView;
    private TextView consequencesDescriptionTextView;
    private Button viewFeedbackButton;

    // Feedback Screen Views
    private LinearLayout feedbackScreen;
    private TextView feedbackTitleTextView;
    private TextView survivalStatusTextView;
    private TextView feedbackDescriptionTextView;
    private TextView rewardsTextView;
    private Button returnToDashboardButton;

    private ProgressBar progressBar;

    private ScenarioRepository scenarioRepository;
    private ScenarioEngine scenarioEngine;
    private ImmersiveScenario scenario;
    private SupplySelectionAdapter supplyAdapter;
    private List<EmergencySupply> selectedSupplies;

    private MediaPlayer soundPlayer;
    private CountDownTimer countdownTimer;
    private CountDownTimer conditionsTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive_scenario);

        String scenarioId = getIntent().getStringExtra(EXTRA_SCENARIO_ID);

        scenarioRepository = ScenarioRepository.getInstance();
        selectedSupplies = new ArrayList<>();

        initViews();
        loadScenario(scenarioId);
    }

    private void initViews() {
        // Warning Screen
        warningScreen = findViewById(R.id.warningScreen);
        warningIconTextView = findViewById(R.id.warningIconTextView);
        warningTitleTextView = findViewById(R.id.warningTitleTextView);
        warningAnimationView = findViewById(R.id.warningAnimationView);
        warningMessageTextView = findViewById(R.id.warningMessageTextView);
        countdownTextView = findViewById(R.id.countdownTextView);
        startScenarioButton = findViewById(R.id.startScenarioButton);

        // Situation Screen
        situationScreen = findViewById(R.id.situationScreen);
        situationTitleTextView = findViewById(R.id.situationTitleTextView);
        situationImageView = findViewById(R.id.situationImageView);
        situationDescriptionTextView = findViewById(R.id.situationDescriptionTextView);
        situationQuestionTextView = findViewById(R.id.situationQuestionTextView);
        situationChoice1Button = findViewById(R.id.situationChoice1Button);
        situationChoice2Button = findViewById(R.id.situationChoice2Button);
        situationChoice3Button = findViewById(R.id.situationChoice3Button);

        // Supplies Screen
        suppliesScreen = findViewById(R.id.suppliesScreen);
        suppliesTitleTextView = findViewById(R.id.suppliesTitleTextView);
        suppliesLimitTextView = findViewById(R.id.suppliesLimitTextView);
        suppliesCountTextView = findViewById(R.id.suppliesCountTextView);
        backpackStatusTextView = findViewById(R.id.backpackStatusTextView);
        suppliesRecyclerView = findViewById(R.id.suppliesRecyclerView);
        confirmSuppliesButton = findViewById(R.id.confirmSuppliesButton);

        // Evacuation Screen
        evacuationScreen = findViewById(R.id.evacuationScreen);
        evacuationTitleTextView = findViewById(R.id.evacuationTitleTextView);
        evacuationDescriptionTextView = findViewById(R.id.evacuationDescriptionTextView);
        evacuationChoice1Button = findViewById(R.id.evacuationChoice1Button);
        evacuationChoice2Button = findViewById(R.id.evacuationChoice2Button);
        evacuationChoice3Button = findViewById(R.id.evacuationChoice3Button);

        // Conditions Screen
        conditionsScreen = findViewById(R.id.conditionsScreen);
        conditionsTitleTextView = findViewById(R.id.conditionsTitleTextView);
        conditionsAnimationView = findViewById(R.id.conditionsAnimationView);
        conditionsDescriptionTextView = findViewById(R.id.conditionsDescriptionTextView);
        conditionsQuestionTextView = findViewById(R.id.conditionsQuestionTextView);
        conditionsChoice1Button = findViewById(R.id.conditionsChoice1Button);
        conditionsChoice2Button = findViewById(R.id.conditionsChoice2Button);

        // Consequences Screen
        consequencesScreen = findViewById(R.id.consequencesScreen);
        consequencesTitleTextView = findViewById(R.id.consequencesTitleTextView);
        consequencesAnimationView = findViewById(R.id.consequencesAnimationView);
        consequencesDescriptionTextView = findViewById(R.id.consequencesDescriptionTextView);
        viewFeedbackButton = findViewById(R.id.viewFeedbackButton);

        // Feedback Screen
        feedbackScreen = findViewById(R.id.feedbackScreen);
        feedbackTitleTextView = findViewById(R.id.feedbackTitleTextView);
        survivalStatusTextView = findViewById(R.id.survivalStatusTextView);
        feedbackDescriptionTextView = findViewById(R.id.feedbackDescriptionTextView);
        rewardsTextView = findViewById(R.id.rewardsTextView);
        returnToDashboardButton = findViewById(R.id.returnToDashboardButton);

        progressBar = findViewById(R.id.progressBar);

        // Setup confirm supplies button
        if (confirmSuppliesButton != null) {
            confirmSuppliesButton.setOnClickListener(v -> confirmSupplies());
        }
    }

    private void loadScenario(String scenarioId) {
        progressBar.setVisibility(View.VISIBLE);

        // Load scenario from repository
        scenario = scenarioRepository.getScenario(scenarioId);
        
        if (scenario == null) {
            Toast.makeText(this, "Scenario not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Validate scenario
        if (!scenario.isValid()) {
            Toast.makeText(this, "Invalid scenario data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize the scenario engine
        try {
            scenarioEngine = new ScenarioEngine(scenario);
            scenarioEngine.setListener(new ScenarioEngineListener());
            getSupportActionBar().setTitle(scenario.getTitle());
            progressBar.setVisibility(View.GONE);
            
            // Show the first step
            showStep(scenarioEngine.getCurrentStep());
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing scenario: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Listener for scenario engine events.
     */
    private class ScenarioEngineListener implements ScenarioEngine.ScenarioEngineListener {
        @Override
        public void onStepChanged(ScenarioStep newStep) {
            showStep(newStep);
        }

        @Override
        public void onConditionAdded(DisasterCondition condition) {
            // Show condition alert
            Toast.makeText(ImmersiveScenarioActivity.this, 
                    "Condition: " + condition.getDescription(), 
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onHealthChanged(int newHealth) {
            // Update health display if available
            // For now, could show a visual indicator
        }

        @Override
        public void onTimeChanged(int newTimeRemaining) {
            // Update time display if available
        }

        @Override
        public void onScenarioCompleted(ScenarioEngine.ScenarioResult result) {
            showFeedback(result);
        }

        @Override
        public void onError(String errorMessage) {
            Toast.makeText(ImmersiveScenarioActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Display a scenario step based on its phase.
     */
    private void showStep(ScenarioStep step) {
        if (step == null) {
            Toast.makeText(this, "No step to display", Toast.LENGTH_SHORT).show();
            return;
        }

        hideAllScreens();

        switch (step.getPhase()) {
            case WARNING:
                showWarningScreen(step);
                break;
            case SITUATION_EVALUATION:
                showSituationScreen(step);
                break;
            case SUPPLIES_SELECTION:
                showSuppliesScreen(step);
                break;
            case EVACUATION_ACTIONS:
                showEvacuationScreen(step);
                break;
            case CHANGING_CONDITIONS:
                showConditionsScreen(step);
                break;
            case CONSEQUENCES:
                showConsequencesScreen(step);
                break;
            case FEEDBACK:
                showFeedbackScreen();
                break;
        }
        
        // Add fade-in animation for the new screen
        animateScreenIn();
    }

    /**
     * Add fade-in animation for the current screen
     */
    private void animateScreenIn() {
        android.view.View currentScreen = getCurrentVisibleScreen();
        if (currentScreen != null) {
            currentScreen.setAlpha(0f);
            currentScreen.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .setInterpolator(new android.view.animation.DecelerateInterpolator())
                    .start();
        }
    }

    /**
     * Get the currently visible screen
     */
    private android.view.View getCurrentVisibleScreen() {
        if (warningScreen.getVisibility() == View.VISIBLE) return warningScreen;
        if (situationScreen.getVisibility() == View.VISIBLE) return situationScreen;
        if (suppliesScreen.getVisibility() == View.VISIBLE) return suppliesScreen;
        if (evacuationScreen.getVisibility() == View.VISIBLE) return evacuationScreen;
        if (conditionsScreen.getVisibility() == View.VISIBLE) return conditionsScreen;
        if (consequencesScreen.getVisibility() == View.VISIBLE) return consequencesScreen;
        if (feedbackScreen.getVisibility() == View.VISIBLE) return feedbackScreen;
        return null;
    }

    private void showWarningScreen(ScenarioStep step) {
        hideAllScreens();
        warningScreen.setVisibility(View.VISIBLE);

        warningTitleTextView.setText(step.getTitle());
        warningMessageTextView.setText(step.getDescription());

        // Play warning sound
        playSoundEffect(scenario.getWarningSoundUrl());

        // Load warning animation
        if (step.getAnimationUrl() != null && !step.getAnimationUrl().isEmpty()) {
            warningAnimationView.setAnimationFromUrl(step.getAnimationUrl());
            warningAnimationView.playAnimation();
        }

        // Start countdown
        startCountdown(step.getTimeLimitSeconds());

        // Setup start button
        startScenarioButton.setOnClickListener(v -> {
            if (countdownTimer != null) {
                countdownTimer.cancel();
            }
            // Select the only choice to advance
            if (step.getChoices() != null && !step.getChoices().isEmpty()) {
                scenarioEngine.selectChoice(step.getChoices().get(0).getId());
            }
        });
    }

    private void showSituationScreen(ScenarioStep step) {
        hideAllScreens();
        situationScreen.setVisibility(View.VISIBLE);

        situationTitleTextView.setText(step.getTitle());
        situationDescriptionTextView.setText(step.getDescription());
        situationQuestionTextView.setText("What should you do?");

        // Load scenario image
        if (scenario.getImageUrl() != null && !scenario.getImageUrl().isEmpty()) {
            com.bumptech.glide.Glide.with(this)
                    .load(scenario.getImageUrl())
                    .into(situationImageView);
        }

        // Setup choices from step
        List<ScenarioStepChoice> choices = step.getChoices();
        if (choices != null && !choices.isEmpty()) {
            setupChoiceButtons(choices, situationChoice1Button, situationChoice2Button, situationChoice3Button);
        }
    }

    private void showSuppliesScreen(ScenarioStep step) {
        hideAllScreens();
        suppliesScreen.setVisibility(View.VISIBLE);

        suppliesTitleTextView.setText(step.getTitle());
        suppliesLimitTextView.setText("Select up to " + step.getMaxSuppliesAllowed() + " items");

        // Setup supplies recycler view
        supplyAdapter = new SupplySelectionAdapter(scenario.getAvailableSupplies(), selectedSupplies, step.getMaxSuppliesAllowed());
        supplyAdapter.setOnSupplySelectionListener(this::updateConfirmButton);
        suppliesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        suppliesRecyclerView.setAdapter(supplyAdapter);

        updateConfirmButton();
    }

    private void showEvacuationScreen(ScenarioStep step) {
        hideAllScreens();
        evacuationScreen.setVisibility(View.VISIBLE);

        evacuationTitleTextView.setText(step.getTitle());
        evacuationDescriptionTextView.setText(step.getDescription());

        // Setup choices from step
        List<ScenarioStepChoice> choices = step.getChoices();
        if (choices != null && !choices.isEmpty()) {
            setupChoiceButtons(choices, evacuationChoice1Button, evacuationChoice2Button, evacuationChoice3Button);
        }
    }

    private void showConditionsScreen(ScenarioStep step) {
        hideAllScreens();
        conditionsScreen.setVisibility(View.VISIBLE);

        conditionsTitleTextView.setText(step.getTitle());
        conditionsDescriptionTextView.setText(step.getDescription());
        conditionsQuestionTextView.setText("How do you respond?");

        // Load animation
        if (step.getAnimationUrl() != null && !step.getAnimationUrl().isEmpty()) {
            conditionsAnimationView.setAnimationFromUrl(step.getAnimationUrl());
            conditionsAnimationView.playAnimation();
        }

        // Setup choices from step
        List<ScenarioStepChoice> choices = step.getChoices();
        if (choices != null && !choices.isEmpty()) {
            setupChoiceButtons(choices, conditionsChoice1Button, conditionsChoice2Button);
        }
    }

    private void showConsequencesScreen(ScenarioStep step) {
        hideAllScreens();
        consequencesScreen.setVisibility(View.VISIBLE);

        consequencesTitleTextView.setText(step.getTitle());
        consequencesDescriptionTextView.setText(step.getDescription());

        // Load animation
        if (step.getAnimationUrl() != null && !step.getAnimationUrl().isEmpty()) {
            consequencesAnimationView.setAnimationFromUrl(step.getAnimationUrl());
            consequencesAnimationView.playAnimation();
        }

        // Setup choice to view feedback
        if (step.getChoices() != null && !step.getChoices().isEmpty()) {
            ScenarioStepChoice choice = step.getChoices().get(0);
            viewFeedbackButton.setText(choice.getText());
            viewFeedbackButton.setOnClickListener(v -> {
                scenarioEngine.selectChoice(choice.getId());
            });
        }
    }

    private void showFeedbackScreen() {
        hideAllScreens();
        feedbackScreen.setVisibility(View.VISIBLE);

        feedbackTitleTextView.setText("Scenario Complete");
        
        // This will be updated when scenario completes
        survivalStatusTextView.setText("Processing results...");
        feedbackDescriptionTextView.setText("Calculating your performance based on decisions made.");
        rewardsTextView.setText("");
        
        returnToDashboardButton.setOnClickListener(v -> {
            returnToDashboard();
        });
    }

    private void showFeedback(ScenarioEngine.ScenarioResult result) {
        hideAllScreens();
        feedbackScreen.setVisibility(View.VISIBLE);

        feedbackTitleTextView.setText("EARTHQUAKE PREPAREDNESS FEEDBACK");
        
        if (result.isSurvived()) {
            survivalStatusTextView.setText("✓ SURVIVED");
            survivalStatusTextView.setTextColor(getResources().getColor(R.color.secondary));
        } else {
            survivalStatusTextView.setText("✗ DID NOT SURVIVE");
            survivalStatusTextView.setTextColor(getResources().getColor(R.color.danger));
        }

        StringBuilder feedback = new StringBuilder();
        feedback.append("═══════════════════════════════════\n");
        feedback.append("SCORE BREAKDOWN (100 POINTS)\n");
        feedback.append("═══════════════════════════════════\n\n");
        
        if (result.getScoreBreakdown() != null) {
            int situationScore = (int) result.getScoreBreakdown().get("situationScore");
            int suppliesScore = (int) result.getScoreBreakdown().get("suppliesScore");
            int evacuationScore = (int) result.getScoreBreakdown().get("evacuationScore");
            int adaptabilityScore = (int) result.getScoreBreakdown().get("adaptabilityScore");
            int preparednessScore = (int) result.getScoreBreakdown().get("preparednessScore");
            int totalScore = (int) result.getScoreBreakdown().get("totalScore");
            
            feedback.append("📊 Situation Evaluation: ").append(situationScore).append("/20\n");
            feedback.append("🎒 Supply Selection: ").append(suppliesScore).append("/20\n");
            feedback.append("🚶 Evacuation Actions: ").append(evacuationScore).append("/25\n");
            feedback.append("🔄 Adaptability: ").append(adaptabilityScore).append("/25\n");
            feedback.append("📋 Preparedness: ").append(preparednessScore).append("/10\n");
            feedback.append("─────────────────────────────\n");
            feedback.append("TOTAL SCORE: ").append(totalScore).append("/100\n\n");
        }
        
        feedback.append("═══════════════════════════════════\n");
        feedback.append("DECISION ANALYSIS\n");
        feedback.append("═══════════════════════════════════\n\n");
        
        // Show decision feedback
        Map<String, String> decisions = result.getDecisions();
        if (decisions != null) {
            // Situation evaluation
            String situationDecision = decisions.get(ScenarioPhase.SITUATION_EVALUATION.name());
            String situationExplanation = decisions.get(ScenarioPhase.SITUATION_EVALUATION.name() + "_explanation");
            if (situationExplanation != null) {
                feedback.append("🏠 SITUATION EVALUATION:\n");
                feedback.append(situationExplanation).append("\n\n");
            }
            
            // Evacuation
            String evacuationDecision = decisions.get(ScenarioPhase.EVACUATION_ACTIONS.name());
            String evacuationExplanation = decisions.get(ScenarioPhase.EVACUATION_ACTIONS.name() + "_explanation");
            if (evacuationExplanation != null) {
                feedback.append("🚶 EVACUATION ACTIONS:\n");
                feedback.append(evacuationExplanation).append("\n\n");
            }
            
            // Adaptability
            String conditionDecision = decisions.get(ScenarioPhase.CHANGING_CONDITIONS.name());
            String conditionExplanation = decisions.get(ScenarioPhase.CHANGING_CONDITIONS.name() + "_explanation");
            if (conditionExplanation != null) {
                feedback.append("🔄 ADAPTABILITY TO CONDITIONS:\n");
                feedback.append(conditionExplanation).append("\n\n");
            }
        }
        
        feedback.append("═══════════════════════════════════\n");
        feedback.append("PRACTICAL PREPAREDNESS TIPS\n");
        feedback.append("═══════════════════════════════════\n\n");
        feedback.append("✓ Practice DROP, COVER, and HOLD ON regularly\n");
        feedback.append("✓ Secure heavy furniture to walls\n");
        feedback.append("✓ Create and maintain an emergency supply kit\n");
        feedback.append("✓ Identify safe spots in each room of your home\n");
        feedback.append("✓ Know your evacuation routes and meeting points\n");
        feedback.append("✓ Stay away from windows, glass, and heavy objects\n");
        feedback.append("✓ Never use elevators during/after earthquakes\n");
        feedback.append("✓ Move to open areas away from buildings after shaking\n");
        
        feedbackDescriptionTextView.setText(feedback.toString());
        
        StringBuilder rewards = new StringBuilder();
        rewards.append("Final Health: ").append(result.getFinalHealth()).append("/100\n");
        rewards.append("Time Remaining: ").append(result.getFinalTimeRemaining()).append(" seconds\n\n");
        rewards.append("REWARDS EARNED:\n");
        rewards.append("+").append(result.getXpEarned()).append(" XP\n");
        rewards.append("+").append(result.getCoinsEarned()).append(" Coins");
        rewardsTextView.setText(rewards.toString());

        returnToDashboardButton.setOnClickListener(v -> {
            returnToDashboard();
        });
    }

    /**
     * Helper method to setup choice buttons from ScenarioStepChoice list
     */
    private void setupChoiceButtons(List<ScenarioStepChoice> choices, Button... buttons) {
        for (int i = 0; i < buttons.length; i++) {
            if (i < choices.size()) {
                ScenarioStepChoice choice = choices.get(i);
                buttons[i].setText(choice.getText());
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setOnClickListener(v -> {
                    // Show feedback before proceeding
                    showChoiceFeedback(choice);
                });
            } else {
                buttons[i].setVisibility(View.GONE);
            }
        }
    }

    /**
     * Show feedback for a choice selection
     */
    private void showChoiceFeedback(ScenarioStepChoice choice) {
        boolean isCorrect = choice.isCorrect();
        String explanation = choice.getExplanation();
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(isCorrect ? "✓ Good Decision!" : "✗ Poor Decision");
        builder.setMessage(explanation);
        builder.setPositiveButton("Continue", (dialog, which) -> {
            scenarioEngine.selectChoice(choice.getId());
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void updateConfirmButton() {
        if (confirmSuppliesButton != null) {
            confirmSuppliesButton.setEnabled(selectedSupplies.size() > 0 && selectedSupplies.size() <= scenario.getMaxSuppliesAllowed());
        }

        // Update supplies count display
        if (suppliesCountTextView != null) {
            suppliesCountTextView.setText("Selected: " + selectedSupplies.size() + "/" + scenario.getMaxSuppliesAllowed());
        }

        // Update backpack status
        int totalWeight = selectedSupplies.stream().mapToInt(EmergencySupply::getWeight).sum();
        String backpackStatus;
        if (selectedSupplies.size() == 0) {
            backpackStatus = "Backpack Status: Empty";
        } else if (totalWeight <= 3) {
            backpackStatus = "Backpack Status: Light";
        } else if (totalWeight <= 6) {
            backpackStatus = "Backpack Status: Moderate";
        } else {
            backpackStatus = "Backpack Status: Heavy";
        }
        if (backpackStatusTextView != null) {
            backpackStatusTextView.setText(backpackStatus);
        }
    }

    private void confirmSupplies() {
        scenarioEngine.selectSupplies(selectedSupplies);
    }

    private void startCountdown(int seconds) {
        countdownTextView.setVisibility(View.VISIBLE);
        startScenarioButton.setVisibility(View.GONE);

        countdownTimer = new CountDownTimer(seconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Scenario starting in: " + (millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                countdownTextView.setVisibility(View.GONE);
                startScenarioButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void hideAllScreens() {
        warningScreen.setVisibility(View.GONE);
        situationScreen.setVisibility(View.GONE);
        suppliesScreen.setVisibility(View.GONE);
        evacuationScreen.setVisibility(View.GONE);
        conditionsScreen.setVisibility(View.GONE);
        consequencesScreen.setVisibility(View.GONE);
        feedbackScreen.setVisibility(View.GONE);
    }

    private void playSoundEffect(String soundUrl) {
        // Sound effects can be implemented here
        // For now, this is a placeholder
    }

    private void returnToDashboard() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up timers
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
        if (conditionsTimer != null) {
            conditionsTimer.cancel();
        }
    }
}
