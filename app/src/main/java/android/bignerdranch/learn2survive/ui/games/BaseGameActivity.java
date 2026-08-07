package android.bignerdranch.learn2survive.ui.games;

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

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameResult;
import android.bignerdranch.learn2survive.domain.model.GameScore;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.domain.repository.GameRepository;
import android.bignerdranch.learn2survive.data.remote.GameRepositoryImpl;
import android.bignerdranch.learn2survive.domain.manager.GamificationManager;
import android.bignerdranch.learn2survive.domain.repository.GamificationRepository;
import android.bignerdranch.learn2survive.data.remote.GamificationRepositoryImpl;
import android.bignerdranch.learn2survive.ui.gamification.GamificationHelper;

public abstract class BaseGameActivity extends AppCompatActivity {
    // UI Components
    protected TextView timerTextView;
    protected TextView scoreTextView;
    protected TextView coinsTextView;
    protected TextView xpTextView;
    protected ProgressBar progressBar;
    protected ImageView pauseButton;
    protected Button pauseDialogButton;
    
    // Game State
    protected GameType gameType;
    protected GameDifficulty difficulty;
    protected int score;
    protected int coins;
    protected int xp;
    protected int stars;
    protected boolean isPaused;
    protected boolean isGameOver;
    
    // Timer
    protected CountDownTimer gameTimer;
    protected long timeRemaining;
    protected long totalTime;
    
    // Sound
    protected MediaPlayer soundPlayer;
    
    // Animation
    protected LottieAnimationView animationView;
    
    // Firebase
    protected GameRepository gameRepository;
    protected String currentUserId;
    
    // Gamification
    protected GamificationManager gamificationManager;
    protected GamificationRepository gamificationRepository;
    protected GamificationHelper gamificationHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        
        initViews();
        initGame();
        setupListeners();
        startGame();
    }
    
    protected abstract int getLayoutResourceId();
    protected abstract void initGame();
    protected abstract void setupGameContent();
    protected abstract void onGameComplete();
    protected abstract void updateGameState();
    
    protected void initViews() {
        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        coinsTextView = findViewById(R.id.coinsTextView);
        xpTextView = findViewById(R.id.xpTextView);
        progressBar = findViewById(R.id.progressBar);
        pauseButton = findViewById(R.id.pauseButton);
        animationView = findViewById(R.id.animationView);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    protected void setupListeners() {
        if (pauseButton != null) {
            pauseButton.setOnClickListener(v -> togglePause());
        }
    }
    
    protected void startGame() {
        isPaused = false;
        isGameOver = false;
        score = 0;
        coins = 0;
        xp = 0;
        stars = 0;
        
        totalTime = difficulty.getTimeLimitSeconds() * 1000L;
        timeRemaining = totalTime;
        
        // Initialize Firebase repository
        gameRepository = new GameRepositoryImpl();
        currentUserId = getCurrentUserId();
        
        // Initialize gamification
        gamificationManager = GamificationManager.getInstance();
        gamificationRepository = new GamificationRepositoryImpl();
        gamificationHelper = GamificationHelper.getInstance();
        
        // Load player data
        loadPlayerGamificationData();
        
        setupGameContent();
        startTimer();
        updateUI();
    }
    
    protected void loadPlayerGamificationData() {
        gamificationRepository.loadPlayerData(currentUserId, new GamificationRepository.GamificationDataCallback() {
            @Override
            public void onSuccess(android.bignerdranch.learn2survive.domain.model.PlayerGamificationData playerData) {
                gamificationManager.setPlayerData(playerData);
                gamificationManager.updateStreak();
            }
            
            @Override
            public void onFailure(Exception e) {
                // Create new player data on failure
                android.bignerdranch.learn2survive.domain.model.PlayerGamificationData newPlayerData = 
                    new android.bignerdranch.learn2survive.domain.model.PlayerGamificationData();
                newPlayerData.setUserId(currentUserId);
                gamificationManager.setPlayerData(newPlayerData);
            }
        });
    }
    
    protected String getCurrentUserId() {
        // Implement this to get the current Firebase user ID
        // For now, return a default value
        return "guest_user";
    }
    
    protected void startTimer() {
        gameTimer = new CountDownTimer(timeRemaining, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                updateTimerDisplay();
            }
            
            @Override
            public void onFinish() {
                timeRemaining = 0;
                updateTimerDisplay();
                endGame(true);
            }
        }.start();
    }
    
    protected void updateTimerDisplay() {
        if (timerTextView != null) {
            long seconds = timeRemaining / 1000;
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            timerTextView.setText(String.format("%02d:%02d", minutes, remainingSeconds));
            
            // Change color when time is running low
            if (timeRemaining < 10000) {
                timerTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            } else {
                timerTextView.setTextColor(getResources().getColor(android.R.color.white));
            }
        }
    }
    
    protected void updateUI() {
        if (scoreTextView != null) {
            scoreTextView.setText(String.valueOf(score));
        }
        if (coinsTextView != null) {
            coinsTextView.setText(String.valueOf(coins));
        }
        if (xpTextView != null) {
            xpTextView.setText(String.valueOf(xp));
        }
        updateTimerDisplay();
    }
    
    protected void addScore(int points) {
        score += points;
        playSoundEffect("score.mp3");
        showScoreAnimation(points);
        updateUI();
    }
    
    protected void addCoins(int amount) {
        coins += amount;
        gamificationManager.addCoins(amount);
        playSoundEffect("coin.mp3");
        updateUI();
    }
    
    protected void addXP(int amount) {
        xp += amount;
        gamificationManager.addXP(amount);
        updateUI();
    }
    
    protected void calculateStars() {
        double percentage = (double) score / getMaxPossibleScore();
        if (percentage >= 0.9) {
            stars = 3;
        } else if (percentage >= 0.7) {
            stars = 2;
        } else if (percentage >= 0.5) {
            stars = 1;
        } else {
            stars = 0;
        }
    }
    
    protected abstract int getMaxPossibleScore();
    
    protected void togglePause() {
        if (isGameOver) return;
        
        isPaused = !isPaused;
        
        if (isPaused) {
            gameTimer.cancel();
            showPauseDialog();
        } else {
            startTimer();
        }
    }
    
    protected void showPauseDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Game Paused");
        builder.setMessage("Your game is paused. What would you like to do?");
        
        builder.setPositiveButton("Resume", (dialog, which) -> {
            togglePause();
        });
        
        builder.setNegativeButton("Restart", (dialog, which) -> {
            restartGame();
        });
        
        builder.setNeutralButton("Quit", (dialog, which) -> {
            finish();
        });
        
        builder.setCancelable(false);
        builder.show();
    }
    
    protected void restartGame() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        startGame();
    }
    
    protected void endGame(boolean timeUp) {
        if (isGameOver) return;
        isGameOver = true;
        
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        
        calculateStars();
        calculateFinalRewards();
        
        GameResult result = createGameResult();
        
        // Save score to Firebase
        saveGameScore(result);
        
        // Update gamification
        updateGamification(result);
        
        // Sync to Firebase
        syncGamificationData();
        
        onGameComplete();
        showResultsDialog(result, timeUp);
    }
    
    protected void updateGamification(GameResult result) {
        // Track old level to detect level ups
        int oldLevel = gamificationManager.getPlayerData() != null ? 
            gamificationManager.getPlayerData().getCurrentLevel() : 1;
        
        // Update game achievements
        gamificationManager.updateAchievementProgress("first_game", 1);
        gamificationManager.updateAchievementProgress("game_master_10", 1);
        gamificationManager.updateAchievementProgress("game_master_25", 1);
        
        // Update score achievements
        if (result.getScore() >= 500) {
            gamificationManager.updateAchievementProgress("high_score_game", 1);
        }
        
        // Update perfect game achievement
        if (result.getStars() >= 3) {
            gamificationManager.updateAchievementProgress("perfect_game", 1);
        }
        
        // Update challenge progress
        gamificationManager.updateChallengeProgress("daily_game_1", 1);
        gamificationManager.updateChallengeProgress("weekly_games_7", 1);
        
        // Check for level up
        int newLevel = gamificationManager.getPlayerData() != null ? 
            gamificationManager.getPlayerData().getCurrentLevel() : oldLevel;
        
        if (newLevel > oldLevel) {
            gamificationHelper.showLevelUp(this, newLevel);
            gamificationHelper.triggerConfetti(this);
        }
        
        // Show newly completed achievements
        gamificationHelper.checkAndShowAchievements(this);
    }
    
    protected void syncGamificationData() {
        android.bignerdranch.learn2survive.domain.model.PlayerGamificationData playerData = 
            gamificationManager.getPlayerData();
        
        if (playerData != null) {
            gamificationRepository.syncPlayerData(playerData, new GamificationRepository.GamificationCallback() {
                @Override
                public void onSuccess() {
                    // Sync successful
                }
                
                @Override
                public void onFailure(Exception e) {
                    // Handle sync failure
                }
            });
        }
    }
    
    protected void saveGameScore(GameResult result) {
        GameScore gameScore = new GameScore(currentUserId, gameType, difficulty);
        gameScore.setScore(result.getScore());
        gameScore.setStars(result.getStars());
        gameScore.setCoinsEarned(result.getCoinsEarned());
        gameScore.setXpEarned(result.getXpEarned());
        gameScore.setTimeTakenSeconds(result.getTimeTakenSeconds());
        
        // Check if this is a high score
        gameRepository.getUserBestScore(currentUserId, gameType, new GameRepository.BestScoreCallback() {
            @Override
            public void onSuccess(GameScore bestScore) {
                if (bestScore == null || result.getScore() > bestScore.getScore()) {
                    gameScore.setHighScore(true);
                    result.setNewHighScore(true);
                } else {
                    gameScore.setHighScore(false);
                    result.setNewHighScore(false);
                }
                
                // Save the score
                gameRepository.saveGameScore(gameScore, new GameRepository.GameScoreCallback() {
                    @Override
                    public void onSuccess(String scoreId) {
                        // Score saved successfully
                    }
                    
                    @Override
                    public void onFailure(Exception e) {
                        // Handle error
                    }
                });
            }
            
            @Override
            public void onFailure(Exception e) {
                // On error, save without high score check
                gameScore.setHighScore(false);
                gameRepository.saveGameScore(gameScore, new GameRepository.GameScoreCallback() {
                    @Override
                    public void onSuccess(String scoreId) {
                        // Score saved successfully
                    }
                    
                    @Override
                    public void onFailure(Exception e2) {
                        // Handle error
                    }
                });
            }
        });
    }
    
    protected void calculateFinalRewards() {
        // Base rewards
        coins = score / 10;
        xp = score / 5;
        
        // Bonus for stars
        coins += stars * 10;
        xp += stars * 20;
        
        // Difficulty bonus
        double difficultyMultiplier = 1.0 + (difficulty.getLevel() * 0.25);
        coins = (int) (coins * difficultyMultiplier);
        xp = (int) (xp * difficultyMultiplier);
    }
    
    protected GameResult createGameResult() {
        GameResult result = new GameResult();
        result.setGameType(gameType);
        result.setDifficulty(difficulty);
        result.setScore(score);
        result.setStars(stars);
        result.setCoinsEarned(coins);
        result.setXpEarned(xp);
        result.setTimeTakenSeconds((totalTime - timeRemaining) / 1000);
        return result;
    }
    
    protected void showResultsDialog(GameResult result, boolean timeUp) {
        String title = timeUp ? "Time's Up!" : "Game Complete!";
        if (result.isNewHighScore()) {
            title = "🎉 New High Score! 🎉";
        }
        
        String message = String.format(
            "Score: %d\nStars: %d\nCoins: %d\nXP: %d\nTime: %ds",
            result.getScore(),
            result.getStars(),
            result.getCoinsEarned(),
            result.getXpEarned(),
            result.getTimeTakenSeconds()
        );
        
        if (result.isNewHighScore()) {
            message += "\n\n🏆 Congratulations! You beat your best score!";
        }
        
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        
        builder.setPositiveButton("Play Again", (dialog, which) -> {
            restartGame();
        });
        
        builder.setNegativeButton("Back to Games", (dialog, which) -> {
            finish();
        });
        
        builder.setCancelable(false);
        builder.show();
    }
    
    protected void playSoundEffect(String soundName) {
        try {
            if (soundPlayer != null) {
                soundPlayer.release();
            }
            // In a real implementation, you would load actual sound files
            // soundPlayer = MediaPlayer.create(this, getSoundResourceId(soundName));
            // if (soundPlayer != null) {
            //     soundPlayer.start();
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected void showScoreAnimation(int points) {
        // Implement score popup animation
        if (animationView != null) {
            animationView.setAnimation(R.raw.score_popup);
            animationView.playAnimation();
        }
    }
    
    protected void showCorrectAnimation() {
        if (animationView != null) {
            animationView.setAnimation(R.raw.correct);
            animationView.playAnimation();
        }
    }
    
    protected void showWrongAnimation() {
        if (animationView != null) {
            animationView.setAnimation(R.raw.wrong);
            animationView.playAnimation();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showExitConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    protected void showExitConfirmationDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Exit Game?");
        builder.setMessage("Your progress will be lost. Are you sure you want to exit?");
        
        builder.setPositiveButton("Yes, Exit", (dialog, which) -> {
            if (gameTimer != null) {
                gameTimer.cancel();
            }
            finish();
        });
        
        builder.setNegativeButton("No, Keep Playing", (dialog, which) -> {
            dialog.dismiss();
        });
        
        builder.show();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gameTimer != null) {
            gameTimer.cancel();
        }
        if (soundPlayer != null) {
            soundPlayer.release();
        }
    }
}