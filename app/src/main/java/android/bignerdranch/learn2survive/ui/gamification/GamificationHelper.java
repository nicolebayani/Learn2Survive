package android.bignerdranch.learn2survive.ui.gamification;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;

import android.bignerdranch.learn2survive.domain.manager.GamificationManager;
import android.bignerdranch.learn2survive.domain.model.Achievement;
import android.bignerdranch.learn2survive.domain.model.PlayerLevel;

public class GamificationHelper {
    private static GamificationHelper instance;
    private GamificationManager gamificationManager;
    
    private GamificationHelper() {
        gamificationManager = GamificationManager.getInstance();
    }
    
    public static synchronized GamificationHelper getInstance() {
        if (instance == null) {
            instance = new GamificationHelper();
        }
        return instance;
    }
    
    public void showAchievementUnlocked(Activity activity, String achievementId) {
        Achievement achievement = gamificationManager.getAchievementById(achievementId);
        if (achievement == null) return;
        
        // Create and show achievement view
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        AchievementUnlockedView achievementView = new AchievementUnlockedView(activity);
        rootView.addView(achievementView);
        
        achievementView.showAchievement(achievement, () -> {
            rootView.removeView(achievementView);
        });
    }
    
    public void showLevelUp(Activity activity, int newLevel) {
        PlayerLevel levelData = gamificationManager.getLevelData(newLevel);
        if (levelData == null) return;
        
        // Create and show level up view
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        LevelUpView levelUpView = new LevelUpView(activity);
        rootView.addView(levelUpView);
        
        levelUpView.showLevelUp(levelData, () -> {
            rootView.removeView(levelUpView);
        });
    }
    
    public void triggerConfetti(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        ConfettiHelper confettiHelper = new ConfettiHelper(activity);
        confettiHelper.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));
        rootView.addView(confettiHelper);
        
        confettiHelper.startConfetti();
        
        // Auto-stop after 3 seconds
        confettiHelper.postDelayed(() -> {
            confettiHelper.stopConfetti();
            rootView.removeView(confettiHelper);
        }, 3000);
    }
    
    public void checkAndShowAchievements(Activity activity) {
        // Check for newly completed achievements and show them
        android.bignerdranch.learn2survive.domain.model.PlayerGamificationData playerData = 
            gamificationManager.getPlayerData();
        
        if (playerData != null && playerData.getAchievementProgress() != null) {
            for (String achievementId : playerData.getAchievementProgress().keySet()) {
                android.bignerdranch.learn2survive.domain.model.PlayerGamificationData.AchievementProgress progress = 
                    playerData.getAchievementProgress().get(achievementId);
                
                if (progress != null && progress.isCompleted()) {
                    // Check if this was just completed (within last few seconds)
                    if (progress.getCompletedAt() != null) {
                        long timeSinceCompletion = System.currentTimeMillis() - progress.getCompletedAt().getTime();
                        if (timeSinceCompletion < 5000) { // Completed within last 5 seconds
                            showAchievementUnlocked(activity, achievementId);
                        }
                    }
                }
            }
        }
    }
    
    public void checkAndShowLevelUp(Activity activity) {
        android.bignerdranch.learn2survive.domain.model.PlayerGamificationData playerData = 
            gamificationManager.getPlayerData();
        
        if (playerData != null) {
            int currentLevel = playerData.getCurrentLevel();
            // Check if level just increased (this would need additional tracking)
            // For now, just show level up if needed
            showLevelUp(activity, currentLevel);
        }
    }
}