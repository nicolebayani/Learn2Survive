package android.bignerdranch.learn2survive.domain.model;

public class Badge {
    private String id;
    private String name;
    private String description;
    private String iconResId;
    private BadgeRarity rarity;
    private int unlockLevel;
    private String requiredAchievement;

    public enum BadgeRarity {
        COMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

    public Badge() {
    }

    public Badge(String id, String name, String description, String iconResId, 
                BadgeRarity rarity, int unlockLevel, String requiredAchievement) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
        this.rarity = rarity;
        this.unlockLevel = unlockLevel;
        this.requiredAchievement = requiredAchievement;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconResId() {
        return iconResId;
    }

    public void setIconResId(String iconResId) {
        this.iconResId = iconResId;
    }

    public BadgeRarity getRarity() {
        return rarity;
    }

    public void setRarity(BadgeRarity rarity) {
        this.rarity = rarity;
    }

    public int getUnlockLevel() {
        return unlockLevel;
    }

    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }

    public String getRequiredAchievement() {
        return requiredAchievement;
    }

    public void setRequiredAchievement(String requiredAchievement) {
        this.requiredAchievement = requiredAchievement;
    }
}