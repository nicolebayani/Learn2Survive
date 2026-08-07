package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;

public class GameResult {
    private String gameId;
    private GameType gameType;
    private GameDifficulty difficulty;
    private int score;
    private int stars;
    private int coinsEarned;
    private int xpEarned;
    private long timeTakenSeconds;
    private Date completionDate;
    private boolean isNewHighScore;

    public GameResult() {
        this.completionDate = new Date();
    }

    public GameResult(String gameId, GameType gameType, GameDifficulty difficulty) {
        this();
        this.gameId = gameId;
        this.gameType = gameType;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isNewHighScore() {
        return isNewHighScore;
    }

    public void setNewHighScore(boolean newHighScore) {
        isNewHighScore = newHighScore;
    }
}