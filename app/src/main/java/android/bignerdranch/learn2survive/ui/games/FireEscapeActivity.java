package android.bignerdranch.learn2survive.ui.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.ExitOption;

public class FireEscapeActivity extends BaseGameActivity {
    private LinearLayout exitsContainer;
    private TextView instructionTextView;
    private TextView scenarioTextView;
    private ImageView scenarioImageView;
    
    private List<ExitOption> exitOptions;
    private int currentScenario;
    private int correctChoices;
    private int totalScenarios;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_fire_escape;
    }

    @Override
    protected void initGame() {
        gameType = GameType.FIRE_ESCAPE;
        difficulty = GameDifficulty.MEDIUM;
        
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        // Set number of scenarios based on difficulty
        switch (difficulty) {
            case EASY:
                totalScenarios = 3;
                break;
            case MEDIUM:
                totalScenarios = 5;
                break;
            case HARD:
                totalScenarios = 7;
                break;
            case EXPERT:
                totalScenarios = 10;
                break;
        }
        
        currentScenario = 0;
        correctChoices = 0;
        exitOptions = new ArrayList<>();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Fire Escape");
        }
    }

    @Override
    protected void setupGameContent() {
        exitsContainer = findViewById(R.id.exitsContainer);
        instructionTextView = findViewById(R.id.instructionTextView);
        scenarioTextView = findViewById(R.id.scenarioTextView);
        scenarioImageView = findViewById(R.id.scenarioImageView);
        
        generateScenarios();
        loadScenario(currentScenario);
    }
    
    private void generateScenarios() {
        exitOptions.clear();
        
        // Fire escape scenarios
        exitOptions.add(new ExitOption("Use the stairs", "Never use elevators during a fire!", true, 20));
        exitOptions.add(new ExitOption("Use the elevator", "Elevators can be deadly during fires!", false, -10));
        exitOptions.add(new ExitOption("Wait for help", "Don't wait - evacuate immediately!", false, -5));
        
        exitOptions.add(new ExitOption("Stay low and crawl", "Smoke rises, stay low to breathe better", true, 15));
        exitOptions.add(new ExitOption("Stand up and run", "Smoke inhalation is dangerous!", false, -10));
        exitOptions.add(new ExitOption("Cover your mouth", "Use a cloth to filter smoke", true, 10));
        exitOptions.add(new ExitOption("Run through flames", "Never go through flames!", false, -15));
        
        exitOptions.add(new ExitOption("Check doors for heat", "Use the back of your hand to check", true, 15));
        exitOptions.add(new ExitOption("Open doors immediately", "Check for fire first!", false, -10));
        exitOptions.add(new ExitOption("Use wet towels", "Wet towels can help block smoke", true, 10));
        
        exitOptions.add(new ExitOption("Go to the nearest exit", "Know your nearest emergency exit", true, 15));
        exitOptions.add(new ExitOption("Use your usual exit", "It might be blocked by fire!", false, -10));
        exitOptions.add(new ExitOption("Follow emergency signs", "Green signs show emergency exits", true, 10));
        
        exitOptions.add(new ExitOption("Don't go back for items", "Life is more important than possessions", true, 20));
        exitOptions.add(new ExitOption("Grab your phone", "Leave everything behind!", false, -10));
        exitOptions.add(new ExitOption("Save your pets", "Have a plan for pets in advance", true, 5));
        
        exitOptions.add(new ExitOption("Alert others", "Warn others about the fire", true, 15));
        exitOptions.add(new ExitOption("Stay silent", "Help others evacuate!", false, -5));
        exitOptions.add(new ExitOption("Call emergency services", "Call once you're safe outside", true, 10));
        
        // Shuffle scenarios
        java.util.Collections.shuffle(exitOptions);
    }
    
    private void loadScenario(int index) {
        if (index >= exitOptions.size() || index >= totalScenarios) {
            endGame(false);
            return;
        }
        
        // Get 3 options for current scenario (1 correct, 2 wrong)
        List<ExitOption> currentOptions = new ArrayList<>();
        ExitOption correctOption = null;
        
        // Find a correct option
        for (ExitOption option : exitOptions) {
            if (option.isCorrect() && !currentOptions.contains(option)) {
                correctOption = option;
                currentOptions.add(option);
                break;
            }
        }
        
        // Add wrong options
        int wrongAdded = 0;
        for (ExitOption option : exitOptions) {
            if (!option.isCorrect() && !currentOptions.contains(option) && wrongAdded < 2) {
                currentOptions.add(option);
                wrongAdded++;
            }
        }
        
        // Shuffle the options
        java.util.Collections.shuffle(currentOptions);
        
        // Display scenario
        scenarioTextView.setText("Scenario " + (index + 1) + ": What's the safest action during a fire?");
        scenarioImageView.setImageResource(R.drawable.ic_fire);
        
        // Display options
        displayOptions(currentOptions);
    }
    
    private void displayOptions(List<ExitOption> options) {
        exitsContainer.removeAllViews();
        
        for (ExitOption option : options) {
            Button optionButton = new Button(this);
            optionButton.setText(option.getAction());
            optionButton.setBackgroundResource(R.drawable.option_background);
            optionButton.setTextColor(getResources().getColor(android.R.color.white));
            optionButton.setPadding(16, 16, 16, 16);
            optionButton.setOnClickListener(v -> handleOptionChoice(option));
            exitsContainer.addView(optionButton);
        }
    }
    
    private void handleOptionChoice(ExitOption option) {
        if (option.isCorrect()) {
            correctChoices++;
            addScore(option.getPoints());
            addCoins(2);
            addXP(10);
            showCorrectAnimation();
            android.widget.Toast.makeText(this, "Correct! " + option.getExplanation(), android.widget.Toast.LENGTH_SHORT).show();
        } else {
            addScore(option.getPoints());
            showWrongAnimation();
            android.widget.Toast.makeText(this, "Wrong! " + option.getExplanation(), android.widget.Toast.LENGTH_SHORT).show();
        }
        
        currentScenario++;
        
        // Update progress
        int progress = (int) ((double) currentScenario / totalScenarios * 100);
        progressBar.setProgress(progress);
        
        // Load next scenario or end game
        if (currentScenario >= totalScenarios) {
            endGame(false);
        } else {
            loadScenario(currentScenario);
        }
    }
    
    @Override
    protected void updateGameState() {
        // Update UI if needed
    }
    
    @Override
    protected void onGameComplete() {
        // Game completion handled in endGame
    }
    
    @Override
    protected int getMaxPossibleScore() {
        return totalScenarios * 20; // Max 20 points per scenario
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Bonus for accuracy
        double accuracy = (double) correctChoices / totalScenarios;
        if (accuracy >= 0.8) {
            coins += 20;
            xp += 30;
        } else if (accuracy >= 0.6) {
            coins += 10;
            xp += 15;
        }
    }
}