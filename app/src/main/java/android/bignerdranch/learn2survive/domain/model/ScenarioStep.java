package android.bignerdranch.learn2survive.domain.model;

import java.util.List;
import java.util.Map;

/**
 * Represents a single step or phase in a scenario.
 * Each step has a description, choices, and can introduce new conditions.
 */
public class ScenarioStep {
    private String id;
    private ScenarioPhase phase;
    private String title;
    private String description;
    private String imageUrl;
    private String animationUrl;
    private String soundUrl;
    private List<ScenarioStepChoice> choices;
    private List<DisasterCondition> introducedConditions;
    private int timeLimitSeconds;
    private boolean requiresSupplies;
    private int maxSuppliesAllowed;
    private boolean isTerminal; // If true, scenario ends after this step
    private Map<String, Object> stepData; // Additional step-specific data

    public ScenarioStep() {
    }

    public ScenarioStep(String id, ScenarioPhase phase, String title, String description) {
        this.id = id;
        this.phase = phase;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScenarioPhase getPhase() {
        return phase;
    }

    public void setPhase(ScenarioPhase phase) {
        this.phase = phase;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }

    public String getSoundUrl() {
        return soundUrl;
    }

    public void setSoundUrl(String soundUrl) {
        this.soundUrl = soundUrl;
    }

    public List<ScenarioStepChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ScenarioStepChoice> choices) {
        this.choices = choices;
    }

    public void addChoice(ScenarioStepChoice choice) {
        if (this.choices == null) {
            this.choices = new java.util.ArrayList<>();
        }
        this.choices.add(choice);
    }

    public List<DisasterCondition> getIntroducedConditions() {
        return introducedConditions;
    }

    public void setIntroducedConditions(List<DisasterCondition> introducedConditions) {
        this.introducedConditions = introducedConditions;
    }

    public void addCondition(DisasterCondition condition) {
        if (this.introducedConditions == null) {
            this.introducedConditions = new java.util.ArrayList<>();
        }
        this.introducedConditions.add(condition);
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public boolean isRequiresSupplies() {
        return requiresSupplies;
    }

    public void setRequiresSupplies(boolean requiresSupplies) {
        this.requiresSupplies = requiresSupplies;
    }

    public int getMaxSuppliesAllowed() {
        return maxSuppliesAllowed;
    }

    public void setMaxSuppliesAllowed(int maxSuppliesAllowed) {
        this.maxSuppliesAllowed = maxSuppliesAllowed;
    }

    public boolean isTerminal() {
        return isTerminal;
    }

    public void setTerminal(boolean terminal) {
        isTerminal = terminal;
    }

    public Map<String, Object> getStepData() {
        return stepData;
    }

    public void setStepData(Map<String, Object> stepData) {
        this.stepData = stepData;
    }
}
