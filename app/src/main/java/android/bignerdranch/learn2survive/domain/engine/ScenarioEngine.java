package android.bignerdranch.learn2survive.domain.engine;

import android.bignerdranch.learn2survive.domain.model.DisasterCondition;
import android.bignerdranch.learn2survive.domain.model.EmergencySupply;
import android.bignerdranch.learn2survive.domain.model.ImmersiveScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioPhase;
import android.bignerdranch.learn2survive.domain.model.ScenarioState;
import android.bignerdranch.learn2survive.domain.model.ScenarioStep;
import android.bignerdranch.learn2survive.domain.model.ScenarioStepChoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reusable scenario gameplay engine.
 * Manages game logic, state transitions, and outcome calculations.
 * Separated from UI for testability and reusability.
 */
public class ScenarioEngine {
    private ImmersiveScenario scenario;
    private ScenarioState state;
    private ScenarioStep currentStep;
    private List<EmergencySupply> selectedSupplies;
    private ScenarioEngineListener listener;

    public interface ScenarioEngineListener {
        void onStepChanged(ScenarioStep newStep);
        void onConditionAdded(DisasterCondition condition);
        void onHealthChanged(int newHealth);
        void onTimeChanged(int newTimeRemaining);
        void onScenarioCompleted(ScenarioResult result);
        void onError(String errorMessage);
    }

    public ScenarioEngine(ImmersiveScenario scenario) {
        if (scenario == null) {
            throw new IllegalArgumentException("Scenario cannot be null");
        }
        if (!scenario.isValid()) {
            throw new IllegalArgumentException("Scenario is not valid: " + scenario.getId());
        }

        this.scenario = scenario;
        this.state = new ScenarioState(scenario.getId());
        this.selectedSupplies = new ArrayList<>();
        initializeScenario();
    }

    private void initializeScenario() {
        // Start at the first step
        String firstStepId = scenario.getFirstStepId();
        currentStep = scenario.getStep(firstStepId);
        if (currentStep == null) {
            throw new IllegalStateException("First step not found: " + firstStepId);
        }

        state.setCurrentPhase(currentStep.getPhase());
        state.setTimeRemaining(scenario.getTotalTimeLimitSeconds());
    }

    public void setListener(ScenarioEngineListener listener) {
        this.listener = listener;
    }

    public ScenarioState getState() {
        return state;
    }

    public ScenarioStep getCurrentStep() {
        return currentStep;
    }

    public ImmersiveScenario getScenario() {
        return scenario;
    }

    /**
     * Select a choice in the current step.
     * @param choiceId The ID of the selected choice
     */
    public void selectChoice(String choiceId) {
        if (currentStep == null) {
            notifyError("No current step");
            return;
        }

        ScenarioStepChoice choice = findChoiceById(choiceId);
        if (choice == null) {
            notifyError("Choice not found: " + choiceId);
            return;
        }

        // Check if choice requires a specific condition
        if (choice.isRequiresCondition()) {
            boolean conditionMet = checkConditionMet(choice.getRequiredConditionId());
            if (!conditionMet) {
                notifyError("Required condition not met: " + choice.getRequiredConditionId());
                return;
            }
        }

        // Record the decision with explanation for feedback
        state.addPhaseDecision(currentStep.getPhase().name(), choiceId);
        if (choice.getExplanation() != null) {
            state.addPhaseDecision(currentStep.getPhase().name() + "_explanation", choice.getExplanation());
        }

        // Apply immediate effects
        applyChoiceEffects(choice);

        // Mark current step as completed
        scenario.markStepCompleted(currentStep.getId());

        // Determine next step
        String nextStepId = choice.getNextStepId();
        if (nextStepId == null || nextStepId.isEmpty()) {
            // No next step, check if this is terminal
            if (currentStep.isTerminal()) {
                completeScenario();
            } else {
                // Try to get next step by phase progression
                advanceToNextPhase();
            }
        } else {
            // Move to specified next step
            moveToStep(nextStepId);
        }
    }

    /**
     * Select supplies for the scenario.
     * @param supplies List of selected supplies
     */
    public void selectSupplies(List<EmergencySupply> supplies) {
        if (supplies == null) {
            supplies = new ArrayList<>();
        }

        // Validate supply count
        if (supplies.size() > scenario.getMaxSuppliesAllowed()) {
            notifyError("Too many supplies selected. Maximum: " + scenario.getMaxSuppliesAllowed());
            return;
        }

        this.selectedSupplies = supplies;
        state.setSelectedSupplies(supplies);
        state.setSelectedSupplies(true);

        // Evaluate supply selection
        evaluateSupplySelection();

        // Mark supplies step as completed and advance
        if (currentStep != null && currentStep.getPhase() == ScenarioPhase.SUPPLIES_SELECTION) {
            scenario.markStepCompleted(currentStep.getId());
            // Move to next phase
            ScenarioPhase nextPhase = getNextPhase(currentStep.getPhase());
            if (nextPhase != null) {
                ScenarioStep nextStep = findStepByPhase(nextPhase);
                if (nextStep != null) {
                    moveToStep(nextStep.getId());
                } else {
                    completeScenario();
                }
            } else {
                completeScenario();
            }
        }
    }

    /**
     * Move to the next step by phase progression.
     */
    private void advanceToNextPhase() {
        ScenarioPhase currentPhase = state.getCurrentPhase();
        ScenarioPhase nextPhase = getNextPhase(currentPhase);

        if (nextPhase == null) {
            // No more phases, complete scenario
            completeScenario();
            return;
        }

        // Find a step with the next phase
        ScenarioStep nextStep = findStepByPhase(nextPhase);
        if (nextStep != null) {
            moveToStep(nextStep.getId());
        } else {
            // No step found for next phase, complete scenario
            completeScenario();
        }
    }

    private ScenarioPhase getNextPhase(ScenarioPhase current) {
        switch (current) {
            case WARNING:
                return ScenarioPhase.SITUATION_EVALUATION;
            case SITUATION_EVALUATION:
                return ScenarioPhase.SUPPLIES_SELECTION;
            case SUPPLIES_SELECTION:
                return ScenarioPhase.EVACUATION_ACTIONS;
            case EVACUATION_ACTIONS:
                return ScenarioPhase.CHANGING_CONDITIONS;
            case CHANGING_CONDITIONS:
                return ScenarioPhase.CONSEQUENCES;
            case CONSEQUENCES:
                return ScenarioPhase.FEEDBACK;
            case FEEDBACK:
                return null; // End
            default:
                return null;
        }
    }

    private ScenarioStep findStepByPhase(ScenarioPhase phase) {
        for (ScenarioStep step : scenario.getSteps().values()) {
            if (step.getPhase() == phase && !scenario.isStepCompleted(step.getId())) {
                return step;
            }
        }
        return null;
    }

    private void moveToStep(String stepId) {
        ScenarioStep nextStep = scenario.getStep(stepId);
        if (nextStep == null) {
            notifyError("Step not found: " + stepId);
            return;
        }

        currentStep = nextStep;
        state.setCurrentPhase(nextStep.getPhase());

        // Introduce any new conditions from this step
        if (nextStep.getIntroducedConditions() != null) {
            for (DisasterCondition condition : nextStep.getIntroducedConditions()) {
                state.addCondition(condition);
                if (listener != null) {
                    listener.onConditionAdded(condition);
                }
            }
        }

        if (listener != null) {
            listener.onStepChanged(nextStep);
        }
    }

    private void applyChoiceEffects(ScenarioStepChoice choice) {
        // Apply health impact
        if (choice.getHealthImpact() != 0) {
            state.decreaseHealth(choice.getHealthImpact());
            if (listener != null) {
                listener.onHealthChanged(state.getHealthStatus());
            }
        }

        // Apply time impact
        if (choice.getTimeImpact() != 0) {
            state.decreaseTime(choice.getTimeImpact());
            if (listener != null) {
                listener.onTimeChanged(state.getTimeRemaining());
            }
        }

        // Apply any custom consequences
        if (choice.getConsequences() != null) {
            applyConsequences(choice.getConsequences());
        }
    }

    private void applyConsequences(Map<String, Object> consequences) {
        // Apply custom consequences defined in the choice
        for (Map.Entry<String, Object> entry : consequences.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {
                case "health":
                    if (value instanceof Integer) {
                        state.decreaseHealth((Integer) value);
                        if (listener != null) {
                            listener.onHealthChanged(state.getHealthStatus());
                        }
                    }
                    break;
                case "time":
                    if (value instanceof Integer) {
                        state.decreaseTime((Integer) value);
                        if (listener != null) {
                            listener.onTimeChanged(state.getTimeRemaining());
                        }
                    }
                    break;
                // Add more consequence types as needed
            }
        }
    }

    private void evaluateSupplySelection() {
        int essentialSelected = 0;
        int totalEssential = 0;
        int unnecessarySelected = 0;
        int totalWeight = 0;

        // Count essentials and unnecessary items
        for (EmergencySupply supply : scenario.getAvailableSupplies()) {
            if (supply.isEssential()) {
                totalEssential++;
                if (selectedSupplies.contains(supply)) {
                    essentialSelected++;
                }
            }
            if (supply.getImportance() < 0 && selectedSupplies.contains(supply)) {
                unnecessarySelected++;
            }
        }

        // Calculate total weight
        for (EmergencySupply supply : selectedSupplies) {
            totalWeight += supply.getWeight();
        }

        // Record decision
        state.addPhaseDecision("supplies_evaluation", 
                String.format("essential:%d/%d,unnecessary:%d,weight:%d", 
                        essentialSelected, totalEssential, unnecessarySelected, totalWeight));

        // Apply penalties for poor supply selection
        if (essentialSelected < totalEssential) {
            int penalty = (totalEssential - essentialSelected) * 10;
            state.decreaseHealth(penalty);
            if (listener != null) {
                listener.onHealthChanged(state.getHealthStatus());
            }
        }

        if (unnecessarySelected > 0) {
            int penalty = unnecessarySelected * 5;
            state.decreaseHealth(penalty);
            if (listener != null) {
                listener.onHealthChanged(state.getHealthStatus());
            }
        }

        if (totalWeight > 10) {
            int timePenalty = (totalWeight - 10) * 5;
            state.decreaseTime(timePenalty);
            if (listener != null) {
                listener.onTimeChanged(state.getTimeRemaining());
            }
        }
    }

    private void completeScenario() {
        // Determine survival based on health and time
        boolean survived = state.getHealthStatus() > 0 && state.getTimeRemaining() > 0;
        state.setSurvived(survived);

        // Calculate 100-point score breakdown
        int situationScore = calculateSituationScore();
        int suppliesScore = calculateSuppliesScore();
        int evacuationScore = calculateEvacuationScore();
        int adaptabilityScore = calculateAdaptabilityScore();
        int preparednessScore = calculatePreparednessScore(survived, state.getHealthStatus());
        
        int totalScore = situationScore + suppliesScore + evacuationScore + adaptabilityScore + preparednessScore;

        // Calculate rewards based on score
        int xpEarned = (int) (scenario.getXpReward() * (totalScore / 100.0));
        int coinsEarned = (int) (scenario.getCoinReward() * (totalScore / 100.0));

        // Build score breakdown map
        Map<String, Object> scoreBreakdown = new HashMap<>();
        scoreBreakdown.put("situationScore", situationScore);
        scoreBreakdown.put("suppliesScore", suppliesScore);
        scoreBreakdown.put("evacuationScore", evacuationScore);
        scoreBreakdown.put("adaptabilityScore", adaptabilityScore);
        scoreBreakdown.put("preparednessScore", preparednessScore);
        scoreBreakdown.put("totalScore", totalScore);

        ScenarioResult result = new ScenarioResult(
                scenario.getId(),
                survived,
                state.getHealthStatus(),
                state.getTimeRemaining(),
                xpEarned,
                coinsEarned,
                state.getPhaseDecisions(),
                state.getSelectedSupplies(),
                scoreBreakdown,
                state
        );

        if (listener != null) {
            listener.onScenarioCompleted(result);
        }
    }

    private int calculateSituationScore() {
        // 20 points for situation evaluation
        String decision = state.getPhaseDecisions().get(ScenarioPhase.SITUATION_EVALUATION.name());
        if ("choice_drop_cover".equals(decision)) {
            return 20; // Correct immediate action
        } else if ("choice_run_outside".equals(decision)) {
            return 5; // Very dangerous
        } else if ("choice_grab_bag".equals(decision)) {
            return 8; // Poor prioritization
        }
        return 0;
    }

    private int calculateSuppliesScore() {
        // 20 points for supply selection
        if (state.getSelectedSupplies() == null || state.getSelectedSupplies().isEmpty()) {
            return 0;
        }

        String suppliesDecision = state.getPhaseDecisions().get("supplies_evaluation");
        if (suppliesDecision != null) {
            // Parse the decision string: "essential:X/Y,unnecessary:Z,weight:W"
            int essentialSelected = 0;
            int totalEssential = 0;
            int unnecessarySelected = 0;
            int totalWeight = 0;

            try {
                if (suppliesDecision.contains("essential:")) {
                    String[] parts = suppliesDecision.split(",");
                    for (String part : parts) {
                        if (part.startsWith("essential:")) {
                            String[] values = part.split(":")[1].split("/");
                            essentialSelected = Integer.parseInt(values[0]);
                            totalEssential = Integer.parseInt(values[1]);
                        } else if (part.startsWith("unnecessary:")) {
                            unnecessarySelected = Integer.parseInt(part.split(":")[1]);
                        } else if (part.startsWith("weight:")) {
                            totalWeight = Integer.parseInt(part.split(":")[1]);
                        }
                    }
                }
            } catch (Exception e) {
                // If parsing fails, calculate from actual supplies
                essentialSelected = (int) state.getSelectedSupplies().stream()
                        .filter(EmergencySupply::isEssential).count();
                totalEssential = (int) scenario.getAvailableSupplies().stream()
                        .filter(EmergencySupply::isEssential).count();
                unnecessarySelected = (int) state.getSelectedSupplies().stream()
                        .filter(s -> s.getImportance() < 0).count();
                totalWeight = state.getSelectedSupplies().stream()
                        .mapToInt(EmergencySupply::getWeight).sum();
            }

            // Score calculation
            int score = 0;
            
            // Essential items (10 points max)
            if (totalEssential > 0) {
                double essentialRatio = (double) essentialSelected / totalEssential;
                score += (int) (essentialRatio * 10);
            }

            // Unnecessary items penalty (5 points max penalty)
            score -= Math.min(5, unnecessarySelected);

            // Weight penalty (5 points max penalty)
            if (totalWeight > 10) {
                score -= Math.min(5, (totalWeight - 10) / 2);
            }

            return Math.max(0, Math.min(20, score));
        }
        return 0;
    }

    private int calculateEvacuationScore() {
        // 25 points for evacuation decisions
        String decision = state.getPhaseDecisions().get(ScenarioPhase.EVACUATION_ACTIONS.name());
        if ("evac_stairs".equals(decision)) {
            return 25; // Correct evacuation method
        } else if ("evac_elevator".equals(decision)) {
            return 0; // Extremely dangerous
        } else if ("evac_wait".equals(decision)) {
            return 8; // Poor decision
        }
        return 0;
    }

    private int calculateAdaptabilityScore() {
        // 25 points for adaptability to changing conditions
        String decision = state.getPhaseDecisions().get(ScenarioPhase.CHANGING_CONDITIONS.name());
        if ("condition_adapt".equals(decision)) {
            return 25; // Good adaptation
        } else if ("condition_ignore".equals(decision)) {
            return 5; // Poor adaptation
        }
        return 0;
    }

    private int calculatePreparednessScore(boolean survived, int finalHealth) {
        // 10 points for final preparedness assessment
        if (!survived) {
            return 0;
        }
        
        // Base points for survival
        int score = 5;
        
        // Bonus for high health
        if (finalHealth >= 80) {
            score += 5;
        } else if (finalHealth >= 50) {
            score += 3;
        } else if (finalHealth >= 30) {
            score += 1;
        }
        
        return score;
    }

    private int countCorrectChoices() {
        int count = 0;
        for (ScenarioStep step : scenario.getSteps().values()) {
            if (step.getChoices() != null) {
                for (ScenarioStepChoice choice : step.getChoices()) {
                    if (choice.isCorrect()) {
                        String decision = state.getPhaseDecisions().get(step.getPhase().name());
                        if (decision != null && decision.equals(choice.getId())) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private ScenarioStepChoice findChoiceById(String choiceId) {
        if (currentStep == null || currentStep.getChoices() == null) {
            return null;
        }
        for (ScenarioStepChoice choice : currentStep.getChoices()) {
            if (choice.getId().equals(choiceId)) {
                return choice;
            }
        }
        return null;
    }

    private boolean checkConditionMet(String conditionId) {
        if (state.getCurrentConditions() == null) {
            return false;
        }
        for (DisasterCondition condition : state.getCurrentConditions()) {
            if (condition.getId().equals(conditionId)) {
                return true;
            }
        }
        return false;
    }

    private void notifyError(String message) {
        if (listener != null) {
            listener.onError(message);
        }
    }

    /**
     * Result class for scenario completion.
     */
    public static class ScenarioResult {
        private String scenarioId;
        private boolean survived;
        private int finalHealth;
        private int finalTimeRemaining;
        private int xpEarned;
        private int coinsEarned;
        private Map<String, String> decisions;
        private List<EmergencySupply> supplies;
        private Map<String, Object> scoreBreakdown;
        private ScenarioState state;

        public ScenarioResult(String scenarioId, boolean survived, int finalHealth,
                           int finalTimeRemaining, int xpEarned, int coinsEarned,
                           Map<String, String> decisions, List<EmergencySupply> supplies,
                           Map<String, Object> scoreBreakdown, ScenarioState state) {
            this.scenarioId = scenarioId;
            this.survived = survived;
            this.finalHealth = finalHealth;
            this.finalTimeRemaining = finalTimeRemaining;
            this.xpEarned = xpEarned;
            this.coinsEarned = coinsEarned;
            this.decisions = decisions;
            this.supplies = supplies;
            this.scoreBreakdown = scoreBreakdown;
            this.state = state;
        }

        // Getters
        public String getScenarioId() { return scenarioId; }
        public boolean isSurvived() { return survived; }
        public int getFinalHealth() { return finalHealth; }
        public int getFinalTimeRemaining() { return finalTimeRemaining; }
        public int getXpEarned() { return xpEarned; }
        public int getCoinsEarned() { return coinsEarned; }
        public Map<String, String> getDecisions() { return decisions; }
        public List<EmergencySupply> getSupplies() { return supplies; }
        public Map<String, Object> getScoreBreakdown() { return scoreBreakdown; }
        public ScenarioState getState() { return state; }
        
        public int getTotalScore() {
            if (scoreBreakdown != null && scoreBreakdown.containsKey("totalScore")) {
                return (int) scoreBreakdown.get("totalScore");
            }
            return 0;
        }
    }
}
