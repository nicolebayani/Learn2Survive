package android.bignerdranch.learn2survive.domain.model;

public class PlayerLevel {
    private int level;
    private String title;
    private int xpRequired;
    private int coinsReward;
    private String badgeUnlock;

    public PlayerLevel(int level, String title, int xpRequired, int coinsReward, String badgeUnlock) {
        this.level = level;
        this.title = title;
        this.xpRequired = xpRequired;
        this.coinsReward = coinsReward;
        this.badgeUnlock = badgeUnlock;
    }

    public int getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public int getXpRequired() {
        return xpRequired;
    }

    public int getCoinsReward() {
        return coinsReward;
    }

    public String getBadgeUnlock() {
        return badgeUnlock;
    }
}