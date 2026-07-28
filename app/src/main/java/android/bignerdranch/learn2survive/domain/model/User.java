package android.bignerdranch.learn2survive.domain.model;

import java.util.List;
import java.util.Map;

public class User {
    private String userId;
    private String email;
    private String fullName;
    private String profileImageUrl;
    private long createdAt;
    
    // Game-related fields
    private int level;
    private int currentXP;
    private int maxXP;
    private int coins;
    private int dailyStreak;
    private long lastActiveDate;
    private List<String> achievements;
    private Map<String, Object> statistics;
    private int completedLessons;
    private int completedQuizzes;

    public User() {
        // Default values
        this.level = 1;
        this.currentXP = 0;
        this.maxXP = 100;
        this.coins = 0;
        this.dailyStreak = 0;
        this.completedLessons = 0;
        this.completedQuizzes = 0;
    }

    public User(String userId, String email, String fullName, String profileImageUrl, long createdAt) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.profileImageUrl = profileImageUrl;
        this.createdAt = createdAt;
        this.level = 1;
        this.currentXP = 0;
        this.maxXP = 100;
        this.coins = 0;
        this.dailyStreak = 0;
        this.completedLessons = 0;
        this.completedQuizzes = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void setCurrentXP(int currentXP) {
        this.currentXP = currentXP;
    }

    public int getMaxXP() {
        return maxXP;
    }

    public void setMaxXP(int maxXP) {
        this.maxXP = maxXP;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getDailyStreak() {
        return dailyStreak;
    }

    public void setDailyStreak(int dailyStreak) {
        this.dailyStreak = dailyStreak;
    }

    public long getLastActiveDate() {
        return lastActiveDate;
    }

    public void setLastActiveDate(long lastActiveDate) {
        this.lastActiveDate = lastActiveDate;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<String> achievements) {
        this.achievements = achievements;
    }

    public Map<String, Object> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Object> statistics) {
        this.statistics = statistics;
    }

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = completedLessons;
    }

    public int getCompletedQuizzes() {
        return completedQuizzes;
    }

    public void setCompletedQuizzes(int completedQuizzes) {
        this.completedQuizzes = completedQuizzes;
    }
}
