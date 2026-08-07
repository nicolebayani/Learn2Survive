package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerGamificationData {
    private String userId;
    private int currentLevel;
    private int currentXP;
    private int totalXP;
    private int coins;
    private int streak;
    private Date lastActiveDate;
    private Map<String, AchievementProgress> achievementProgress;
    private List<String> unlockedBadges;
    private List<String> unlockedItems;
    private DailyRewardData dailyRewardData;
    private ChallengeProgress dailyChallenge;
    private ChallengeProgress weeklyChallenge;

    public PlayerGamificationData() {
        this.currentLevel = 1;
        this.currentXP = 0;
        this.totalXP = 0;
        this.coins = 0;
        this.streak = 0;
        this.achievementProgress = new HashMap<>();
        this.unlockedBadges = new java.util.ArrayList<>();
        this.unlockedItems = new java.util.ArrayList<>();
        this.dailyRewardData = new DailyRewardData();
    }

    // Nested class for achievement progress
    public static class AchievementProgress {
        private String achievementId;
        private int currentValue;
        private boolean completed;
        private Date completedAt;

        public AchievementProgress() {
        }

        public AchievementProgress(String achievementId, int currentValue) {
            this.achievementId = achievementId;
            this.currentValue = currentValue;
            this.completed = false;
        }

        public String getAchievementId() {
            return achievementId;
        }

        public void setAchievementId(String achievementId) {
            this.achievementId = achievementId;
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public Date getCompletedAt() {
            return completedAt;
        }

        public void setCompletedAt(Date completedAt) {
            this.completedAt = completedAt;
        }
    }

    // Nested class for daily reward data
    public static class DailyRewardData {
        private int consecutiveDays;
        private Date lastClaimDate;
        private boolean claimedToday;

        public DailyRewardData() {
            this.consecutiveDays = 0;
            this.claimedToday = false;
        }

        public int getConsecutiveDays() {
            return consecutiveDays;
        }

        public void setConsecutiveDays(int consecutiveDays) {
            this.consecutiveDays = consecutiveDays;
        }

        public Date getLastClaimDate() {
            return lastClaimDate;
        }

        public void setLastClaimDate(Date lastClaimDate) {
            this.lastClaimDate = lastClaimDate;
        }

        public boolean isClaimedToday() {
            return claimedToday;
        }

        public void setClaimedToday(boolean claimedToday) {
            this.claimedToday = claimedToday;
        }
    }

    // Nested class for challenge progress
    public static class ChallengeProgress {
        private String challengeId;
        private int currentValue;
        private int targetValue;
        private boolean completed;
        private Date completedAt;
        private Date expiresAt;

        public ChallengeProgress() {
        }

        public ChallengeProgress(String challengeId, int targetValue, Date expiresAt) {
            this.challengeId = challengeId;
            this.targetValue = targetValue;
            this.expiresAt = expiresAt;
            this.currentValue = 0;
            this.completed = false;
        }

        public String getChallengeId() {
            return challengeId;
        }

        public void setChallengeId(String challengeId) {
            this.challengeId = challengeId;
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }

        public int getTargetValue() {
            return targetValue;
        }

        public void setTargetValue(int targetValue) {
            this.targetValue = targetValue;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public Date getCompletedAt() {
            return completedAt;
        }

        public void setCompletedAt(Date completedAt) {
            this.completedAt = completedAt;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }

        public void setExpiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
        }
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public void setTotalXP(int totalXP) {
        this.totalXP = totalXP;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Date getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(Date lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public Map<String, AchievementProgress> getAchievementProgress() {
        return achievementProgress;
    }

    public void setAchievementProgress(Map<String, AchievementProgress> achievementProgress) {
        this.achievementProgress = achievementProgress;
    }

    public List<String> getUnlockedBadges() {
        return unlockedBadges;
    }

    public void setUnlockedBadges(List<String> unlockedBadges) {
        this.unlockedBadges = unlockedBadges;
    }

    public List<String> getUnlockedItems() {
        return unlockedItems;
    }

    public void setUnlockedItems(List<String> unlockedItems) {
        this.unlockedItems = unlockedItems;
    }

    public DailyRewardData getDailyRewardData() {
        return dailyRewardData;
    }

    public void setDailyRewardData(DailyRewardData dailyRewardData) {
        this.dailyRewardData = dailyRewardData;
    }

    public ChallengeProgress getDailyChallenge() {
        return dailyChallenge;
    }

    public void setDailyChallenge(ChallengeProgress dailyChallenge) {
        this.dailyChallenge = dailyChallenge;
    }

    public ChallengeProgress getWeeklyChallenge() {
        return weeklyChallenge;
    }

    public void setWeeklyChallenge(ChallengeProgress weeklyChallenge) {
        this.weeklyChallenge = weeklyChallenge;
    }
}