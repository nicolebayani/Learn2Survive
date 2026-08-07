package android.bignerdranch.learn2survive.ui.games.models;

public class Person {
    private String name;
    private int iconResId;
    private int id;

    public Person(String name, int iconResId, int id) {
        this.name = name;
        this.iconResId = iconResId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getId() {
        return id;
    }
}