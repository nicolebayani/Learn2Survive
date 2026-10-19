package android.bignerdranch.learn2survive.domain.model;

public class DisasterCondition {
    private String id;
    private String description;
    private String severity;
    private int timeToImpact;
    private String effectOnPlayer;
    private String recommendedAction;
    private boolean isLifeThreatening;

    public DisasterCondition() {
    }

    public DisasterCondition(String id, String description, String severity,
                           int timeToImpact, String effectOnPlayer,
                           String recommendedAction, boolean isLifeThreatening) {
        this.id = id;
        this.description = description;
        this.severity = severity;
        this.timeToImpact = timeToImpact;
        this.effectOnPlayer = effectOnPlayer;
        this.recommendedAction = recommendedAction;
        this.isLifeThreatening = isLifeThreatening;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public int getTimeToImpact() {
        return timeToImpact;
    }

    public void setTimeToImpact(int timeToImpact) {
        this.timeToImpact = timeToImpact;
    }

    public String getEffectOnPlayer() {
        return effectOnPlayer;
    }

    public void setEffectOnPlayer(String effectOnPlayer) {
        this.effectOnPlayer = effectOnPlayer;
    }

    public String getRecommendedAction() {
        return recommendedAction;
    }

    public void setRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
    }

    public boolean isLifeThreatening() {
        return isLifeThreatening;
    }

    public void setLifeThreatening(boolean lifeThreatening) {
        isLifeThreatening = lifeThreatening;
    }
}
