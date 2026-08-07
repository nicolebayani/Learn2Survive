package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;

public class GameScore {
    private String id;
    private String userId;
    private GameType gameType;
    private GameDifficulty difficulty;
    private int score;
    private int stars;
    private int coinsEarned;
    private int xpEarned;
    private long timeTakenSeconds;
    private Date createdAt;
    private boolean isHighScore;

    public GameScore() {
        this.createdAt = new Date();
    }

    public GameScore(String userId, GameType gameType, GameDifficulty difficulty) {
        this();
        this.userId = userId;
        this.gameType = gameType;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public long getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(long timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isHighScore() {
        return isHighScore;
    }

    public void setHighScore(boolean highScore) {
        isHighScore = highScore;
    }
}