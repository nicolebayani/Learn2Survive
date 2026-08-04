package android.bignerdranch.learn2survive.domain.model;

public class ScenarioChoice {
    private String id;
    private String choiceText;
    private String nextNodeId;
    private boolean isCorrect;
    private String explanation;
    private int xpReward;
    private int timePenaltySeconds;

    public ScenarioChoice() {
    }

    public ScenarioChoice(String id, String choiceText, String nextNodeId,
                         boolean isCorrect, String explanation, int xpReward, int timePenaltySeconds) {
        this.id = id;
        this.choiceText = choiceText;
        this.nextNodeId = nextNodeId;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
        this.xpReward = xpReward;
        this.timePenaltySeconds = timePenaltySeconds;
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
}
