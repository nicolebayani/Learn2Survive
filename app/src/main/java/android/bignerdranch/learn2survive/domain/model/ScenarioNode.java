package android.bignerdranch.learn2survive.domain.model;

import java.util.List;

public class ScenarioNode {
    private String id;
    private String scenarioId;
    private String title;
    private String description;
    private String imageUrl;
    private String animationUrl;
    private String soundEffectUrl;
    private List<ScenarioChoice> choices;
    private int timeLimitSeconds;
    private boolean isEndingNode;
    private boolean isSurvivalEnding;
    private int order;

    public ScenarioNode() {
    }

    public ScenarioNode(String id, String scenarioId, String title, String description,
                       String imageUrl, String animationUrl, String soundEffectUrl,
                       List<ScenarioChoice> choices, int timeLimitSeconds,
                       boolean isEndingNode, boolean isSurvivalEnding, int order) {
        this.id = id;
        this.scenarioId = scenarioId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.animationUrl = animationUrl;
        this.soundEffectUrl = soundEffectUrl;
        this.choices = choices;
        this.timeLimitSeconds = timeLimitSeconds;
        this.isEndingNode = isEndingNode;
        this.isSurvivalEnding = isSurvivalEnding;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
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

    public String getSoundEffectUrl() {
        return soundEffectUrl;
    }

    public void setSoundEffectUrl(String soundEffectUrl) {
        this.soundEffectUrl = soundEffectUrl;
    }

    public List<ScenarioChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<ScenarioChoice> choices) {
        this.choices = choices;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }

    public void setTimeLimitSeconds(int timeLimitSeconds) {
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public boolean isEndingNode() {
        return isEndingNode;
    }

    public void setEndingNode(boolean endingNode) {
        isEndingNode = endingNode;
    }

    public boolean isSurvivalEnding() {
        return isSurvivalEnding;
    }

    public void setSurvivalEnding(boolean survivalEnding) {
        isSurvivalEnding = survivalEnding;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
