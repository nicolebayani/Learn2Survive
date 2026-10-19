package android.bignerdranch.learn2survive.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioState {
    private String scenarioId;
    private ScenarioPhase currentPhase;
    private List<EmergencySupply> selectedSupplies;
    private List<DisasterCondition> currentConditions;
    private Map<String, String> phaseDecisions;
    private int healthStatus;
    private int timeRemaining;
    private boolean hasWarningReceived;
    private boolean hasEvaluatedSituation;
    private boolean hasSelectedSupplies;
    private boolean hasEvacuated;
    private boolean survived;
    private long startTime;

    public ScenarioState() {
        this.selectedSupplies = new ArrayList<>();
        this.currentConditions = new ArrayList<>();
        this.phaseDecisions = new HashMap<>();
        this.healthStatus = 100;
        this.currentPhase = ScenarioPhase.WARNING;
        this.startTime = System.currentTimeMillis();
    }

    public ScenarioState(String scenarioId) {
        this();
        this.scenarioId = scenarioId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public ScenarioPhase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(ScenarioPhase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public List<EmergencySupply> getSelectedSupplies() {
        return selectedSupplies;
    }

    public void setSelectedSupplies(List<EmergencySupply> selectedSupplies) {
        this.selectedSupplies = selectedSupplies;
    }

    public void addSupply(EmergencySupply supply) {
        this.selectedSupplies.add(supply);
    }

    public List<DisasterCondition> getCurrentConditions() {
        return currentConditions;
    }

    public void setCurrentConditions(List<DisasterCondition> currentConditions) {
        this.currentConditions = currentConditions;
    }

    public void addCondition(DisasterCondition condition) {
        this.currentConditions.add(condition);
    }

    public Map<String, String> getPhaseDecisions() {
        return phaseDecisions;
    }

    public void setPhaseDecisions(Map<String, String> phaseDecisions) {
        this.phaseDecisions = phaseDecisions;
    }

    public void addPhaseDecision(String phase, String decision) {
        this.phaseDecisions.put(phase, decision);
    }

    public int getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(int healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void decreaseHealth(int amount) {
        this.healthStatus = Math.max(0, this.healthStatus - amount);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public void decreaseTime(int amount) {
        this.timeRemaining = Math.max(0, this.timeRemaining - amount);
    }

    public boolean hasWarningReceived() {
        return hasWarningReceived;
    }

    public void setWarningReceived(boolean warningReceived) {
        hasWarningReceived = warningReceived;
    }

    public boolean hasEvaluatedSituation() {
        return hasEvaluatedSituation;
    }

    public void setEvaluatedSituation(boolean evaluatedSituation) {
        hasEvaluatedSituation = evaluatedSituation;
    }

    public boolean hasSelectedSupplies() {
        return hasSelectedSupplies;
    }

    public void setSelectedSupplies(boolean selectedSupplies) {
        hasSelectedSupplies = selectedSupplies;
    }

    public boolean hasEvacuated() {
        return hasEvacuated;
    }

    public void setEvacuated(boolean evacuated) {
        hasEvacuated = evacuated;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
