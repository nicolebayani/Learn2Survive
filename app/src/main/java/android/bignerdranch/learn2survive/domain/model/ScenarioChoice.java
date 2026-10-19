package android.bignerdranch.learn2survive.domain.model;

/**
 * Legacy ScenarioChoice class for backward compatibility with old SimulationActivity.
 * This is separate from the new ScenarioStepChoice used by the reusable engine.
 */
public class ScenarioChoice {
    private String id;
    private String choiceText;
    private String explanation;
    private int xpReward;
    private int timePenaltySeconds;
    private String nextNodeId;
    private boolean isCorrect;

    public ScenarioChoice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public int getTimePenaltySeconds() {
        return timePenaltySeconds;
    }

    public void setTimePenaltySeconds(int timePenaltySeconds) {
        this.timePenaltySeconds = timePenaltySeconds;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public void setNextNodeId(String nextNodeId) {
        this.nextNodeId = nextNodeId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
