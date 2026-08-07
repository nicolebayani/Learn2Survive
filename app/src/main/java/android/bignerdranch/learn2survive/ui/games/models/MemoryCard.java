package android.bignerdranch.learn2survive.ui.games.models;

public class MemoryCard {
    private int iconRes;
    private int pairId;
    private boolean flipped;
    private boolean matched;

    public MemoryCard(int iconRes, int pairId) {
        this.iconRes = iconRes;
        this.pairId = pairId;
        this.flipped = false;
        this.matched = false;
    }

    public int getIconRes() {
        return iconRes;
    }

    public int getPairId() {
        return pairId;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }
}