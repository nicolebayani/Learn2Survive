package android.bignerdranch.learn2survive.domain.model;

import java.util.Map;

/**
 * Represents a selectable choice in a scenario step.
 * Each choice can lead to different outcomes and affect the next step.
 * This is the new engine's choice model, separate from the legacy ScenarioChoice.
 */
public class ScenarioStepChoice {
    private String id;
    private String text;
    private String description;
    private boolean isCorrect;
    private String explanation;
    private int healthImpact;
    private int timeImpact;
    private String nextStepId;
    private Map<String, Object> consequences;
    private boolean requiresCondition;
    private String requiredConditionId;

    public ScenarioStepChoice() {
    }

    public ScenarioStepChoice(String id, String text, String description, boolean isCorrect,
                         String explanation, int healthImpact, int timeImpact,
                         String nextStepId) {
        this.id = id;
        this.text = text;
        this.description = description;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
        this.healthImpact = healthImpact;
        this.timeImpact = timeImpact;
        this.nextStepId = nextStepId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getHealthImpact() {
        return healthImpact;
    }

    public void setHealthImpact(int healthImpact) {
        this.healthImpact = healthImpact;
    }

    public int getTimeImpact() {
        return timeImpact;
    }

    public void setTimeImpact(int timeImpact) {
        this.timeImpact = timeImpact;
    }

    public String getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(String nextStepId) {
        this.nextStepId = nextStepId;
    }

    public Map<String, Object> getConsequences() {
        return consequences;
    }

    public void setConsequences(Map<String, Object> consequences) {
        this.consequences = consequences;
    }

    public boolean isRequiresCondition() {
        return requiresCondition;
    }

    public void setRequiresCondition(boolean requiresCondition) {
        this.requiresCondition = requiresCondition;
    }

    public String getRequiredConditionId() {
        return requiredConditionId;
    }

    public void setRequiredConditionId(String requiredConditionId) {
        this.requiredConditionId = requiredConditionId;
    }
}
