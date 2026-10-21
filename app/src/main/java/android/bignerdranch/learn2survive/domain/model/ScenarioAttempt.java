package android.bignerdranch.learn2survive.domain.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Model for storing individual scenario attempt results.
 * Each completion of a scenario is saved as a separate attempt.
 */
public class ScenarioAttempt {
    private String id;
    private String userId;
    private String scenarioId;
    private String scenarioTitle;
    private DisasterSimulationType disasterType;
    private Date completedAt;
    private boolean survived;
    private int finalHealth;
    private int finalTimeRemaining;
    private int xpEarned;
    private int coinsEarned;
    private int totalScore;

    // Detailed score breakdown
    private int situationScore;
    private int suppliesScore;
    private int evacuationScore;
    private int adaptabilityScore;
    private int preparednessScore;

    // Decision tracking
    private Map<String, String> phaseDecisions;
    private List<String> selectedSupplyIds;

    // Attempt tracking
    private int attemptNumber;
    private boolean wasRewardClaimed;

    public ScenarioAttempt() {
    }

    public ScenarioAttempt(String id, String userId, String scenarioId, String scenarioTitle,
                          DisasterSimulationType disasterType, Date completedAt, boolean survived,
                          int finalHealth, int finalTimeRemaining, int xpEarned, int coinsEarned,
                          int totalScore, int situationScore, int suppliesScore,
                          int evacuationScore, int adaptabilityScore, int preparednessScore,
                          Map<String, String> phaseDecisions, List<String> selectedSupplyIds,
                          int attemptNumber, boolean wasRewardClaimed) {
        this.id = id;
        this.userId = userId;
        this.scenarioId = scenarioId;
        this.scenarioTitle = scenarioTitle;
        this.disasterType = disasterType;
        this.completedAt = completedAt;
        this.survived = survived;
        this.finalHealth = finalHealth;
        this.finalTimeRemaining = finalTimeRemaining;
        this.xpEarned = xpEarned;
        this.coinsEarned = coinsEarned;
        this.totalScore = totalScore;
        this.situationScore = situationScore;
        this.suppliesScore = suppliesScore;
        this.evacuationScore = evacuationScore;
        this.adaptabilityScore = adaptabilityScore;
        this.preparednessScore = preparednessScore;
        this.phaseDecisions = phaseDecisions;
        this.selectedSupplyIds = selectedSupplyIds;
        this.attemptNumber = attemptNumber;
        this.wasRewardClaimed = wasRewardClaimed;
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

    public DisasterSimulationType getDisasterType() {
        return disasterType;
    }

    public void setDisasterType(DisasterSimulationType disasterType) {
        this.disasterType = disasterType;
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

    public int getFinalHealth() {
        return finalHealth;
    }

    public void setFinalHealth(int finalHealth) {
        this.finalHealth = finalHealth;
    }

    public int getFinalTimeRemaining() {
        return finalTimeRemaining;
    }

    public void setFinalTimeRemaining(int finalTimeRemaining) {
        this.finalTimeRemaining = finalTimeRemaining;
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getSituationScore() {
        return situationScore;
    }

    public void setSituationScore(int situationScore) {
        this.situationScore = situationScore;
    }

    public int getSuppliesScore() {
        return suppliesScore;
    }

    public void setSuppliesScore(int suppliesScore) {
        this.suppliesScore = suppliesScore;
    }

    public int getEvacuationScore() {
        return evacuationScore;
    }

    public void setEvacuationScore(int evacuationScore) {
        this.evacuationScore = evacuationScore;
    }

    public int getAdaptabilityScore() {
        return adaptabilityScore;
    }

    public void setAdaptabilityScore(int adaptabilityScore) {
        this.adaptabilityScore = adaptabilityScore;
    }

    public int getPreparednessScore() {
        return preparednessScore;
    }

    public void setPreparednessScore(int preparednessScore) {
        this.preparednessScore = preparednessScore;
    }

    public Map<String, String> getPhaseDecisions() {
        return phaseDecisions;
    }

    public void setPhaseDecisions(Map<String, String> phaseDecisions) {
        this.phaseDecisions = phaseDecisions;
    }

    public List<String> getSelectedSupplyIds() {
        return selectedSupplyIds;
    }

    public void setSelectedSupplyIds(List<String> selectedSupplyIds) {
        this.selectedSupplyIds = selectedSupplyIds;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public boolean isWasRewardClaimed() {
        return wasRewardClaimed;
    }

    public void setWasRewardClaimed(boolean wasRewardClaimed) {
        this.wasRewardClaimed = wasRewardClaimed;
    }
}
