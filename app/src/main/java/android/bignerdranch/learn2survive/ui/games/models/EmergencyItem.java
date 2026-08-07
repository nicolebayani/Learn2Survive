package android.bignerdranch.learn2survive.ui.games.models;

public class EmergencyItem {
    private String name;
    private String description;
    private int iconResId;
    private boolean essential;
    private int points;

    public EmergencyItem(String name, String description, int iconResId, boolean essential, int points) {
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
        this.essential = essential;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isEssential() {
        return essential;
    }

    public int getPoints() {
        return points;
    }
}