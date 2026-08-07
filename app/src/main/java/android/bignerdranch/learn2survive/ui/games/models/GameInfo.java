package android.bignerdranch.learn2survive.ui.games.models;

import android.bignerdranch.learn2survive.domain.model.GameType;

public class GameInfo {
    private GameType gameType;
    private String title;
    private String description;
    private String instruction;
    private int iconResId;
    private String colorHex;

    public GameInfo(GameType gameType, String title, String description, String instruction, int iconResId, String colorHex) {
        this.gameType = gameType;
        this.title = title;
        this.description = description;
        this.instruction = instruction;
        this.iconResId = iconResId;
        this.colorHex = colorHex;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstruction() {
        return instruction;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getColorHex() {
        return colorHex;
    }
}