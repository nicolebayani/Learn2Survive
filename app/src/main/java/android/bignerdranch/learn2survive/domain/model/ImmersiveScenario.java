package android.bignerdranch.learn2survive.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reusable scenario model for disaster preparedness gameplay.
 * Contains all steps, choices, conditions, and outcomes for a complete scenario.
 */
public class ImmersiveScenario {
    private String id;
    private DisasterSimulationType type;
    private String title;
    private String description;
    private String warningMessage;
    private String warningAnimationUrl;
    private String warningSoundUrl;
    private String imageUrl;
    private List<EmergencySupply> availableSupplies;
    private int maxSuppliesAllowed;
    private int totalTimeLimitSeconds;
    private int xpReward;
    private int coinReward;
    private int difficulty;
    private boolean isUnlocked;
    private String firstStepId; // ID of the first step in the scenario
    private Map<String, ScenarioStep> steps; // All steps mapped by ID
    private List<String> completedSteps; // Track which steps have been completed

    public ImmersiveScenario() {
        this.steps = new HashMap<>();
        this.completedSteps = new ArrayList<>();
    }

    public ImmersiveScenario(String id, DisasterSimulationType type, String title,
                            String description, String warningMessage, String warningAnimationUrl,
                            String warningSoundUrl, String imageUrl, List<EmergencySupply> availableSupplies,
                            int maxSuppliesAllowed, int totalTimeLimitSeconds,
                            int xpReward, int coinReward, int difficulty, boolean isUnlocked) {
        this();
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.warningMessage = warningMessage;
        this.warningAnimationUrl = warningAnimationUrl;
        this.warningSoundUrl = warningSoundUrl;
        this.imageUrl = imageUrl;
        this.availableSupplies = availableSupplies;
        this.maxSuppliesAllowed = maxSuppliesAllowed;
        this.totalTimeLimitSeconds = totalTimeLimitSeconds;
        this.xpReward = xpReward;
        this.coinReward = coinReward;
        this.difficulty = difficulty;
        this.isUnlocked = isUnlocked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DisasterSimulationType getType() {
        return type;
    }

    public void setType(DisasterSimulationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getWarningAnimationUrl() {
        return warningAnimationUrl;
    }

    public void setWarningAnimationUrl(String warningAnimationUrl) {
        this.warningAnimationUrl = warningAnimationUrl;
    }

    public String getWarningSoundUrl() {
        return warningSoundUrl;
    }

    public void setWarningSoundUrl(String warningSoundUrl) {
        this.warningSoundUrl = warningSoundUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<EmergencySupply> getAvailableSupplies() {
        return availableSupplies;
    }

    public void setAvailableSupplies(List<EmergencySupply> availableSupplies) {
        this.availableSupplies = availableSupplies;
    }

    public int getMaxSuppliesAllowed() {
        return maxSuppliesAllowed;
    }

    public void setMaxSuppliesAllowed(int maxSuppliesAllowed) {
        this.maxSuppliesAllowed = maxSuppliesAllowed;
    }

    public int getTotalTimeLimitSeconds() {
        return totalTimeLimitSeconds;
    }

    public void setTotalTimeLimitSeconds(int totalTimeLimitSeconds) {
        this.totalTimeLimitSeconds = totalTimeLimitSeconds;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public int getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(int coinReward) {
        this.coinReward = coinReward;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }

    // Reusable engine methods

    public String getFirstStepId() {
        return firstStepId;
    }

    public void setFirstStepId(String firstStepId) {
        this.firstStepId = firstStepId;
    }

    public Map<String, ScenarioStep> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, ScenarioStep> steps) {
        this.steps = steps;
    }

    public void addStep(ScenarioStep step) {
        if (this.steps == null) {
            this.steps = new HashMap<>();
        }
        this.steps.put(step.getId(), step);
    }

    public ScenarioStep getStep(String stepId) {
        return steps != null ? steps.get(stepId) : null;
    }

    public List<String> getCompletedSteps() {
        return completedSteps;
    }

    public void setCompletedSteps(List<String> completedSteps) {
        this.completedSteps = completedSteps;
    }

    public void markStepCompleted(String stepId) {
        if (this.completedSteps == null) {
            this.completedSteps = new ArrayList<>();
        }
        if (!this.completedSteps.contains(stepId)) {
            this.completedSteps.add(stepId);
        }
    }

    public boolean isStepCompleted(String stepId) {
        return completedSteps != null && completedSteps.contains(stepId);
    }

    public int getProgressPercentage() {
        if (steps == null || steps.isEmpty()) {
            return 0;
        }
        return (int) ((completedSteps.size() * 100.0) / steps.size());
    }

    /**
     * Validates that the scenario has all required data for gameplay.
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        if (id == null || id.isEmpty()) return false;
        if (type == null) return false;
        if (title == null || title.isEmpty()) return false;
        if (firstStepId == null || firstStepId.isEmpty()) return false;
        if (steps == null || steps.isEmpty()) return false;
        if (!steps.containsKey(firstStepId)) return false;

        // Validate each step
        for (ScenarioStep step : steps.values()) {
            if (step.getId() == null || step.getId().isEmpty()) return false;
            if (step.getPhase() == null) return false;
            if (step.getDescription() == null || step.getDescription().isEmpty()) return false;

            // Validate choices if present
            if (step.getChoices() != null) {
                for (ScenarioStepChoice choice : step.getChoices()) {
                    if (choice.getId() == null || choice.getId().isEmpty()) return false;
                    if (choice.getText() == null || choice.getText().isEmpty()) return false;
                }
            }
        }

        return true;
    }
}
