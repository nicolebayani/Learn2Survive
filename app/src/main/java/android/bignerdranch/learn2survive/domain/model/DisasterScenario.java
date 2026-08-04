package android.bignerdranch.learn2survive.domain.model;

public class DisasterScenario {
    private String id;
    private DisasterSimulationType type;
    private String title;
    private String description;
    private String startNodeId;
    private int totalTimeLimitSeconds;
    private int xpReward;
    private int coinReward;
    private int difficulty;
    private boolean isUnlocked;

    public DisasterScenario() {
    }

    public DisasterScenario(String id, DisasterSimulationType type, String title,
                           String description, String startNodeId,
                           int totalTimeLimitSeconds, int xpReward, int coinReward,
                           int difficulty, boolean isUnlocked) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.startNodeId = startNodeId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartNodeId() {
        return startNodeId;
    }

    public void setStartNodeId(String startNodeId) {
        this.startNodeId = startNodeId;
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
}
