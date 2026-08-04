package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SimulationAttempt {
    private String id;
    private String userId;
    private String scenarioId;
    private Date startTime;
    private Date endTime;
    private int timeTakenSeconds;
    private List<String> visitedNodeIds;
    private Map<String, String> userChoices;
    private int correctChoices;
    private int wrongChoices;
    private boolean survived;
    private int xpEarned;
    private int coinsEarned;

    public SimulationAttempt() {
    }

    public SimulationAttempt(String id, String userId, String scenarioId,
                           Date startTime, Date endTime, int timeTakenSeconds,
                           List<String> visitedNodeIds, Map<String, String> userChoices,
                           int correctChoices, int wrongChoices, boolean survived,
                           int xpEarned, int coinsEarned) {
        this.id = id;
        this.userId = userId;
        this.scenarioId = scenarioId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeTakenSeconds = timeTakenSeconds;
        this.visitedNodeIds = visitedNodeIds;
        this.userChoices = userChoices;
        this.correctChoices = correctChoices;
        this.wrongChoices = wrongChoices;
        this.survived = survived;
        this.xpEarned = xpEarned;
        this.coinsEarned = coinsEarned;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTimeTakenSeconds() {
        return timeTakenSeconds;
    }

    public void setTimeTakenSeconds(int timeTakenSeconds) {
        this.timeTakenSeconds = timeTakenSeconds;
    }

    public List<String> getVisitedNodeIds() {
        return visitedNodeIds;
    }

    public void setVisitedNodeIds(List<String> visitedNodeIds) {
        this.visitedNodeIds = visitedNodeIds;
    }

    public Map<String, String> getUserChoices() {
        return userChoices;
    }

    public void setUserChoices(Map<String, String> userChoices) {
        this.userChoices = userChoices;
    }

    public int getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(int correctChoices) {
        this.correctChoices = correctChoices;
    }

    public int getWrongChoices() {
        return wrongChoices;
    }

    public void setWrongChoices(int wrongChoices) {
        this.wrongChoices = wrongChoices;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
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
}
