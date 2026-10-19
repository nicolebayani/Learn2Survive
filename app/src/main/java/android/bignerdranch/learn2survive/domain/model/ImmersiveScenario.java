package android.bignerdranch.learn2survive.domain.model;

import java.util.List;

public class ImmersiveScenario {
    private String id;
    private DisasterSimulationType type;
    private String title;
    private String warningMessage;
    private String warningAnimationUrl;
    private String warningSoundUrl;
    private String situationDescription;
    private String imageUrl;
    private List<EmergencySupply> availableSupplies;
    private int maxSuppliesAllowed;
    private int totalTimeLimitSeconds;
    private int xpReward;
    private int coinReward;
    private int difficulty;
    private boolean isUnlocked;

    public ImmersiveScenario() {
    }

    public ImmersiveScenario(String id, DisasterSimulationType type, String title,
                            String warningMessage, String warningAnimationUrl,
                            String warningSoundUrl, String situationDescription,
                            String imageUrl, List<EmergencySupply> availableSupplies,
                            int maxSuppliesAllowed, int totalTimeLimitSeconds,
                            int xpReward, int coinReward, int difficulty, boolean isUnlocked) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.warningMessage = warningMessage;
        this.warningAnimationUrl = warningAnimationUrl;
        this.warningSoundUrl = warningSoundUrl;
        this.situationDescription = situationDescription;
        this.imageUrl = imageUrl;
        this.availableSupplies = availableSupplies;
        this.maxSuppliesAllowed = maxSuppliesAllowed;
        this.totalTimeLimitSeconds = totalTimeLimitSeconds;
        this.xpReward = xpReward;
        this.coinReward = coinReward;
        this.difficulty = difficulty;
        this.isUnlocked = isUnlocked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DisasterSimulationType getType() {
        return type;
    }

    public void setType(DisasterSimulationType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getWarningAnimationUrl() {
        return warningAnimationUrl;
    }

    public void setWarningAnimationUrl(String warningAnimationUrl) {
        this.warningAnimationUrl = warningAnimationUrl;
    }

    public String getWarningSoundUrl() {
        return warningSoundUrl;
    }

    public void setWarningSoundUrl(String warningSoundUrl) {
        this.warningSoundUrl = warningSoundUrl;
    }

    public String getSituationDescription() {
        return situationDescription;
    }

    public void setSituationDescription(String situationDescription) {
        this.situationDescription = situationDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<EmergencySupply> getAvailableSupplies() {
        return availableSupplies;
    }

    public void setAvailableSupplies(List<EmergencySupply> availableSupplies) {
        this.availableSupplies = availableSupplies;
    }

    public int getMaxSuppliesAllowed() {
        return maxSuppliesAllowed;
    }

    public void setMaxSuppliesAllowed(int maxSuppliesAllowed) {
        this.maxSuppliesAllowed = maxSuppliesAllowed;
    }

    public int getTotalTimeLimitSeconds() {
        return totalTimeLimitSeconds;
    }

    public void setTotalTimeLimitSeconds(int totalTimeLimitSeconds) {
        this.totalTimeLimitSeconds = totalTimeLimitSeconds;
    }

    public int getXpReward() {
        return xpReward;
    }

    public void setXpReward(int xpReward) {
        this.xpReward = xpReward;
    }

    public int getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(int coinReward) {
        this.coinReward = coinReward;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void setUnlocked(boolean unlocked) {
        isUnlocked = unlocked;
    }
}
