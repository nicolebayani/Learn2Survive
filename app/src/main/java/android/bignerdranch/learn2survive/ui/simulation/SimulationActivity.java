package android.bignerdranch.learn2survive.ui.simulation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.DisasterScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioChoice;
import android.bignerdranch.learn2survive.domain.model.ScenarioNode;
import android.bignerdranch.learn2survive.domain.model.SimulationAttempt;
import android.bignerdranch.learn2survive.domain.repository.SimulationRepository;
import android.bignerdranch.learn2survive.data.remote.SimulationRepositoryImpl;

public class SimulationActivity extends AppCompatActivity {
    public static final String EXTRA_SCENARIO_ID = "scenario_id";

    private TextView timerTextView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView scenarioImageView;
    private LottieAnimationView animationView;
    private ProgressBar progressBar;
    private Button choice1Button;
    private Button choice2Button;
    private Button choice3Button;
    private Button choice4Button;

    private SimulationRepository simulationRepository;
    private DisasterScenario scenario;
    private ScenarioNode currentNode;
    private String currentNodeId;
    private List<String> visitedNodeIds;
    private Map<String, String> userChoices;
    private int correctChoices;
    private int wrongChoices;
    private int totalXP;

    private CountDownTimer scenarioTimer;
    private CountDownTimer nodeTimer;
    private long timeRemaining;
    private Date startTime;

    private MediaPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        String scenarioId = getIntent().getStringExtra(EXTRA_SCENARIO_ID);

        simulationRepository = new SimulationRepositoryImpl();
        visitedNodeIds = new ArrayList<>();
        userChoices = new HashMap<>();
        correctChoices = 0;
        wrongChoices = 0;
        totalXP = 0;
        startTime = new Date();

        initViews();
        loadScenario(scenarioId);
    }

    private void initViews() {
        timerTextView = findViewById(R.id.timerTextView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        scenarioImageView = findViewById(R.id.scenarioImageView);
        animationView = findViewById(R.id.animationView);
        progressBar = findViewById(R.id.progressBar);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);
        choice3Button = findViewById(R.id.choice3Button);
        choice4Button = findViewById(R.id.choice4Button);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadScenario(String scenarioId) {
        progressBar.setVisibility(View.VISIBLE);

        simulationRepository.getScenario(scenarioId, new SimulationRepository.ScenarioCallback() {
            @Override
            public void onSuccess(DisasterScenario loadedScenario) {
                scenario = loadedScenario;
                getSupportActionBar().setTitle(scenario.getTitle());
                startScenarioTimer(scenario.getTotalTimeLimitSeconds());
                loadNode(scenario.getStartNodeId());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadNode(String nodeId) {
        currentNodeId = nodeId;
        visitedNodeIds.add(nodeId);

        simulationRepository.getScenarioNode(nodeId, new SimulationRepository.NodeCallback() {
            @Override
            public void onSuccess(ScenarioNode node) {
                currentNode = node;
                displayNode(node);
                playSoundEffect(node.getSoundEffectUrl());
                if (node.getTimeLimitSeconds() > 0) {
                    startNodeTimer(node.getTimeLimitSeconds());
                }
            }

            @Override
            public void onFailure(Exception e) {
            }
        });
    }

    private void displayNode(ScenarioNode node) {
        titleTextView.setText(node.getTitle());
        descriptionTextView.setText(node.getDescription());

        if (node.getAnimationUrl() != null && !node.getAnimationUrl().isEmpty()) {
            animationView.setVisibility(View.VISIBLE);
            scenarioImageView.setVisibility(View.GONE);
            animationView.setAnimationFromUrl(node.getAnimationUrl());
            animationView.playAnimation();
        } else if (node.getImageUrl() != null && !node.getImageUrl().isEmpty()) {
            scenarioImageView.setVisibility(View.VISIBLE);
            animationView.setVisibility(View.GONE);
            com.bumptech.glide.Glide.with(this)
                    .load(node.getImageUrl())
                    .into(scenarioImageView);
        }

        displayChoices(node.getChoices());
    }

    private void displayChoices(List<ScenarioChoice> choices) {
        Button[] buttons = {choice1Button, choice2Button, choice3Button, choice4Button};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisibility(View.GONE);
            buttons[i].setOnClickListener(null);
        }

        for (int i = 0; i < choices.size() && i < buttons.length; i++) {
            ScenarioChoice choice = choices.get(i);
            buttons[i].setVisibility(View.VISIBLE);
            buttons[i].setText(choice.getChoiceText());
            final int index = i;
            buttons[i].setOnClickListener(v -> onChoiceSelected(choice, index));
        }
    }

    private void onChoiceSelected(ScenarioChoice choice, int index) {
        userChoices.put(currentNodeId, choice.getId());

        if (choice.isCorrect()) {
            correctChoices++;
            totalXP += choice.getXpReward();
            showCorrectFeedback(choice.getExplanation());
        } else {
            wrongChoices++;
            timeRemaining -= choice.getTimePenaltySeconds() * 1000;
            showWrongFeedback(choice.getExplanation());
        }

        if (currentNode.isEndingNode()) {
            finishSimulation(choice.isCorrect());
        } else {
            loadNode(choice.getNextNodeId());
        }
    }

    private void showCorrectFeedback(String explanation) {
        playSoundEffect("correct.mp3");
        if (explanation != null && !explanation.isEmpty()) {
            showFeedbackDialog("Correct!", explanation, true);
        }
    }

    private void showWrongFeedback(String explanation) {
        playSoundEffect("wrong.mp3");
        if (explanation != null && !explanation.isEmpty()) {
            showFeedbackDialog("Wrong!", explanation, false);
        }
    }

    private void showFeedbackDialog(String title, String message, boolean isCorrect) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Continue", (dialog, which) -> {
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void playSoundEffect(String soundUrl) {
        if (soundUrl != null && !soundUrl.isEmpty()) {
            try {
                if (soundPlayer != null) {
                    soundPlayer.release();
                }
                soundPlayer = new MediaPlayer();
                soundPlayer.setDataSource(soundUrl);
                soundPlayer.prepare();
                soundPlayer.start();
            } catch (Exception e) {
            }
        }
    }

    private void startScenarioTimer(int totalSeconds) {
        timeRemaining = totalSeconds * 1000L;
        scenarioTimer = new CountDownTimer(timeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                finishSimulation(false);
            }
        }.start();
    }

    private void startNodeTimer(int seconds) {
        if (nodeTimer != null) {
            nodeTimer.cancel();
        }
        nodeTimer = new CountDownTimer(seconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                autoSelectChoice();
            }
        }.start();
    }

    private void autoSelectChoice() {
        if (currentNode.getChoices() != null && !currentNode.getChoices().isEmpty()) {
            ScenarioChoice firstChoice = currentNode.getChoices().get(0);
            onChoiceSelected(firstChoice, 0);
        }
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void finishSimulation(boolean survived) {
        if (scenarioTimer != null) {
            scenarioTimer.cancel();
        }
        if (nodeTimer != null) {
            nodeTimer.cancel();
        }
        if (soundPlayer != null) {
            soundPlayer.release();
        }

        Date endTime = new Date();
        int timeTaken = (int) ((endTime.getTime() - startTime.getTime()) / 1000);

        int coinsEarned = survived ? scenario.getCoinReward() : 0;
        int xpEarned = survived ? scenario.getXpReward() + totalXP : totalXP;

        SimulationAttempt attempt = new SimulationAttempt(
            null,
            getCurrentUserId(),
            scenario.getId(),
            startTime,
            endTime,
            timeTaken,
            visitedNodeIds,
            userChoices,
            correctChoices,
            wrongChoices,
            survived,
            xpEarned,
            coinsEarned
        );

        simulationRepository.saveSimulationAttempt(attempt, new SimulationRepository.SaveCallback() {
            @Override
            public void onSuccess(String documentId) {
                navigateToResults(survived, timeTaken, xpEarned, coinsEarned);
            }

            @Override
            public void onFailure(Exception e) {
                navigateToResults(survived, timeTaken, xpEarned, coinsEarned);
            }
        });
    }

    private String getCurrentUserId() {
        return "user_id";
    }

    private void navigateToResults(boolean survived, int timeTaken, int xpEarned, int coinsEarned) {
        Intent intent = new Intent(this, SimulationResultsActivity.class);
        intent.putExtra(SimulationResultsActivity.EXTRA_SURVIVED, survived);
        intent.putExtra(SimulationResultsActivity.EXTRA_TIME_TAKEN, timeTaken);
        intent.putExtra(SimulationResultsActivity.EXTRA_CORRECT_CHOICES, correctChoices);
        intent.putExtra(SimulationResultsActivity.EXTRA_TOTAL_CHOICES, userChoices.size());
        intent.putExtra(SimulationResultsActivity.EXTRA_XP_EARNED, xpEarned);
        intent.putExtra(SimulationResultsActivity.EXTRA_COINS_EARNED, coinsEarned);
        intent.putExtra(SimulationResultsActivity.EXTRA_SCENARIO_TITLE, scenario.getTitle());
        intent.putExtra(SimulationResultsActivity.EXTRA_SCENARIO_ID, scenario.getId());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scenarioTimer != null) {
            scenarioTimer.cancel();
        }
        if (nodeTimer != null) {
            nodeTimer.cancel();
        }
        if (soundPlayer != null) {
            soundPlayer.release();
        }
    }
}
