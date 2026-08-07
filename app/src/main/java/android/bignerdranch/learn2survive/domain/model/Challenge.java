package android.bignerdranch.learn2survive.domain.model;

public class Challenge {
    private String id;
    private String title;
    private String description;
    private int xpReward;
    private int coinsReward;
    private String iconResId;
    private ChallengeType type;
    private int targetValue;
    private ChallengeFrequency frequency;

    public enum ChallengeType {
        COMPLETE_LESSONS,
        COMPLETE_QUIZZES,
        PLAY_GAMES,
        EARN_XP,
        PERFECT_SCORE,
        TIME_LIMIT
    }

    public enum ChallengeFrequency {
        DAILY,
        WEEKLY
    }

    public Challenge() {
    }

    public Challenge(String id, String title, String description, int xpReward, int coinsReward, 
                     String iconResId, ChallengeType type, int targetValue, ChallengeFrequency frequency) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.xpReward = xpReward;
        this.coinsReward = coinsReward;
        this.iconResId = iconResId;
        this.type = type;
        this.targetValue = targetValue;
        this.frequency = frequency;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public int getCoinsReward() {
        return coinsReward;
    }

    public void setCoinsReward(int coinsReward) {
        this.coinsReward = coinsReward;
    }

    public String getIconResId() {
        return iconResId;
    }

    public void setIconResId(String iconResId) {
        this.iconResId = iconResId;
    }

    public ChallengeType getType() {
        return type;
    }

    public void setType(ChallengeType type) {
        this.type = type;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    public ChallengeFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(ChallengeFrequency frequency) {
        this.frequency = frequency;
    }
}