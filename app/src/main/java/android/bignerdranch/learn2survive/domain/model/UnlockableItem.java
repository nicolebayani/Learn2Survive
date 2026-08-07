package android.bignerdranch.learn2survive.domain.model;

public class UnlockableItem {
    private String id;
    private String name;
    private String description;
    private String iconResId;
    private ItemType type;
    private int cost;
    private int requiredLevel;
    private String requiredBadge;

    public enum ItemType {
        AVATAR,
        THEME,
        TITLE,
        BACKGROUND,
        EFFECT
    }

    public UnlockableItem() {
    }

    public UnlockableItem(String id, String name, String description, String iconResId, 
                         ItemType type, int cost, int requiredLevel, String requiredBadge) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
        this.type = type;
        this.cost = cost;
        this.requiredLevel = requiredLevel;
        this.requiredBadge = requiredBadge;
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

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public String getRequiredBadge() {
        return requiredBadge;
    }

    public void setRequiredBadge(String requiredBadge) {
        this.requiredBadge = requiredBadge;
    }
}