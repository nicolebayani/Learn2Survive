package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;

public class SimulationResult {
    private String id;
    private String userId;
    private String scenarioId;
    private String scenarioTitle;
    private DisasterSimulationType type;
    private Date completedAt;
    private boolean survived;
    private int timeTakenSeconds;
    private int correctChoices;
    private int totalChoices;
    private double accuracy;
    private int xpEarned;
    private int coinsEarned;
    private int stars;

    public SimulationResult() {
    }

    public SimulationResult(String id, String userId, String scenarioId,
                          String scenarioTitle, DisasterSimulationType type,
                          Date completedAt, boolean survived, int timeTakenSeconds,
                          int correctChoices, int totalChoices, double accuracy,
                          int xpEarned, int coinsEarned, int stars) {
        this.id = id;
        this.userId = userId;
        this.scenarioId = scenarioId;
        this.scenarioTitle = scenarioTitle;
        this.type = type;
        this.completedAt = completedAt;
        this.survived = survived;
        this.timeTakenSeconds = timeTakenSeconds;
        this.correctChoices = correctChoices;
        this.totalChoices = totalChoices;
        this.accuracy = accuracy;
        this.xpEarned = xpEarned;
        this.coinsEarned = coinsEarned;
        this.stars = stars;
    }

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

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getScenarioTitle() {
        return scenarioTitle;
    }

    public void setScenarioTitle(String scenarioTitle) {
        this.scenarioTitle = scenarioTitle;
    }

    public DisasterSimulationType getType() {
        return type;
    }

    public void setType(DisasterSimulationType type) {
        this.type = type;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public int getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(int correctChoices) {
        this.correctChoices = correctChoices;
    }

    public int getTotalChoices() {
        return totalChoices;
    }

    public void setTotalChoices(int totalChoices) {
        this.totalChoices = totalChoices;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
