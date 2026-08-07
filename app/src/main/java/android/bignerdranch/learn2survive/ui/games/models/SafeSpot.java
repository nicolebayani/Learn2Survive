package android.bignerdranch.learn2survive.ui.games.models;

public class SafeSpot {
    private int row;
    private int col;
    private String type;
    private int iconResId;
    private boolean safe;
    private boolean revealed;

    public SafeSpot(int row, int col, String type, int iconResId, boolean safe) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.iconResId = iconResId;
        this.safe = safe;
        this.revealed = false;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getType() {
        return type;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isSafe() {
        return safe;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}