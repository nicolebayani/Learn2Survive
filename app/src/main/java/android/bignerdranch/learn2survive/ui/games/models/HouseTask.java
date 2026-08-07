package android.bignerdranch.learn2survive.ui.games.models;

public class HouseTask {
    private String name;
    private String description;
    private int iconResId;
    private int points;

    public HouseTask(String name, String description, int iconResId, int points) {
        this.name = name;
        this.description = description;
        this.iconResId = iconResId;
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

    public int getPoints() {
        return points;
    }
}