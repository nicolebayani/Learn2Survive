package android.bignerdranch.learn2survive.domain.model;

public class Achievement {
    private String id;
    private String title;
    private String description;
    private int xpReward;
    private int coinsReward;
    private String iconResId;
    private AchievementCategory category;
    private AchievementType type;
    private int targetValue;
    private boolean hidden;
    private String badgeUnlock;

    public enum AchievementCategory {
        LESSONS,
        QUIZZES,
        SIMULATIONS,
        GAMES,
        SOCIAL,
        STREAK,
        MILESTONE
    }

    public enum AchievementType {
        COUNT,
        SCORE,
        STREAK,
        COMPLETION,
        TIME_BASED
    }

    public Achievement() {
    }

    public Achievement(String id, String title, String description, int xpReward, int coinsReward, 
                      String iconResId, AchievementCategory category, AchievementType type, 
                      int targetValue, boolean hidden, String badgeUnlock) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.xpReward = xpReward;
        this.coinsReward = coinsReward;
        this.iconResId = iconResId;
        this.category = category;
        this.type = type;
        this.targetValue = targetValue;
        this.hidden = hidden;
        this.badgeUnlock = badgeUnlock;
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

    public AchievementCategory getCategory() {
        return category;
    }

    public void setCategory(AchievementCategory category) {
        this.category = category;
    }

    public AchievementType getType() {
        return type;
    }

    public void setType(AchievementType type) {
        this.type = type;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        this.targetValue = targetValue;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getBadgeUnlock() {
        return badgeUnlock;
    }

    public void setBadgeUnlock(String badgeUnlock) {
        this.badgeUnlock = badgeUnlock;
    }
}