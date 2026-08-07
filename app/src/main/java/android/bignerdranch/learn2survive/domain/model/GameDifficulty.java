package android.bignerdranch.learn2survive.domain.model;

public enum GameDifficulty {
    EASY(1, "Easy", 60),
    MEDIUM(2, "Medium", 45),
    HARD(3, "Hard", 30),
    EXPERT(4, "Expert", 20);

    private final int level;
    private final String displayName;
    private final int timeLimitSeconds;

    GameDifficulty(int level, String displayName, int timeLimitSeconds) {
        this.level = level;
        this.displayName = displayName;
        this.timeLimitSeconds = timeLimitSeconds;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getTimeLimitSeconds() {
        return timeLimitSeconds;
    }
}