package android.bignerdranch.learn2survive.data.repository;

import android.bignerdranch.learn2survive.domain.model.DisasterCondition;
import android.bignerdranch.learn2survive.domain.model.DisasterSimulationType;
import android.bignerdranch.learn2survive.domain.model.EmergencySupply;
import android.bignerdranch.learn2survive.domain.model.ImmersiveScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioPhase;
import android.bignerdranch.learn2survive.domain.model.ScenarioStep;
import android.bignerdranch.learn2survive.domain.model.ScenarioStepChoice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for loading scenario definitions.
 * Currently loads from local definitions but structured for future Firestore integration.
 */
public class ScenarioRepository {
    private static ScenarioRepository instance;
    private Map<String, ImmersiveScenario> scenarioCache;

    private ScenarioRepository() {
        this.scenarioCache = new HashMap<>();
        initializeScenarios();
    }

    public static synchronized ScenarioRepository getInstance() {
        if (instance == null) {
            instance = new ScenarioRepository();
        }
        return instance;
    }

    /**
     * Get a scenario by ID.
     * @param scenarioId The scenario ID
     * @return The scenario, or null if not found
     */
    public ImmersiveScenario getScenario(String scenarioId) {
        return scenarioCache.get(scenarioId);
    }

    /**
     * Get all available scenarios.
     * @return List of all scenarios
     */
    public List<ImmersiveScenario> getAllScenarios() {
        return new ArrayList<>(scenarioCache.values());
    }

    /**
     * Initialize scenarios with local definitions.
     * In the future, this can be replaced with Firestore loading.
     */
    private void initializeScenarios() {
        // Create Earthquake scenario
        scenarioCache.put("earthquake_first_60_seconds", createEarthquakeScenario());
        
        // Create Typhoon scenario (basic structure for testing)
        scenarioCache.put("typhoon_house_defender", createTyphoonScenario());
        
        // Create Flood scenario (basic structure for testing)
        scenarioCache.put("flood_rescue", createFloodScenario());
        
        // Create Fire scenario (basic structure for testing)
        scenarioCache.put("fire_escape", createFireScenario());
    }

    private ImmersiveScenario createEarthquakeScenario() {
        ImmersiveScenario scenario = new ImmersiveScenario();
        scenario.setId("earthquake_first_60_seconds");
        scenario.setType(DisasterSimulationType.EARTHQUAKE);
        scenario.setTitle("The First 60 Seconds");
        scenario.setDescription("Experience the critical first minute of an earthquake and learn the correct survival actions.");
        scenario.setWarningMessage("A strong earthquake has started in your area. DROP, COVER, and HOLD ON!");
        scenario.setWarningAnimationUrl("earthquake_warning.json");
        scenario.setWarningSoundUrl("earthquake_alert.mp3");
        scenario.setImageUrl("https://example.com/earthquake_scene.jpg");
        scenario.setMaxSuppliesAllowed(8);
        scenario.setTotalTimeLimitSeconds(300);
        scenario.setXpReward(100);
        scenario.setCoinReward(50);
        scenario.setDifficulty(2);
        scenario.setUnlocked(true);

        // Create available supplies
        List<EmergencySupply> supplies = createEarthquakeSupplies();
        scenario.setAvailableSupplies(supplies);

        // Create scenario steps
        createEarthquakeSteps(scenario);

        return scenario;
    }

    private List<EmergencySupply> createEarthquakeSupplies() {
        List<EmergencySupply> supplies = new ArrayList<>();

        supplies.add(new EmergencySupply("1", "Flashlight", "Essential for seeing in dark conditions", "", 1, 10, true, "Lighting"));
        supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for treating injuries", "", 2, 10, true, "Medical"));
        supplies.add(new EmergencySupply("3", "Water Bottle", "Stay hydrated", "", 1, 9, true, "Food & Water"));
        supplies.add(new EmergencySupply("4", "Whistle", "Signal for help", "", 0, 8, true, "Signaling"));
        supplies.add(new EmergencySupply("5", "Dust Mask", "Protection from dust", "", 0, 7, true, "Protection"));
        supplies.add(new EmergencySupply("6", "Emergency Blanket", "Protection from elements", "", 1, 6, false, "Shelter"));
        supplies.add(new EmergencySupply("7", "Radio", "Emergency broadcasts", "", 1, 5, false, "Communication"));
        supplies.add(new EmergencySupply("8", "Phone Charger", "Keep phone powered", "", 1, 5, false, "Communication"));
        supplies.add(new EmergencySupply("9", "Power Bank", "Backup power", "", 1, 4, false, "Communication"));
        supplies.add(new EmergencySupply("10", "Important Documents", "ID and medical info", "", 0, 3, false, "Documents"));
        supplies.add(new EmergencySupply("11", "Laptop", "Not essential - heavy", "", 3, -5, false, "Electronics"));
        supplies.add(new EmergencySupply("12", "Umbrella", "Not useful", "", 1, -3, false, "Protection"));
        supplies.add(new EmergencySupply("13", "Jewelry Box", "Valuable but not essential", "", 2, -4, false, "Personal"));
        supplies.add(new EmergencySupply("14", "Gaming Console", "Completely unnecessary", "", 2, -5, false, "Electronics"));

        return supplies;
    }

    private void createEarthquakeSteps(ImmersiveScenario scenario) {
        // Step 1: Warning
        ScenarioStep warningStep = new ScenarioStep();
        warningStep.setId("step_warning");
        warningStep.setPhase(ScenarioPhase.WARNING);
        warningStep.setTitle("EARTHQUAKE WARNING");
        warningStep.setDescription("The ground begins to shake violently. Books fall from shelves, furniture slides across the floor, and you can hear glass breaking nearby.");
        warningStep.setAnimationUrl("earthquake_shaking.json");
        warningStep.setSoundUrl("earthquake_rumble.mp3");
        warningStep.setTimeLimitSeconds(5);
        
        ScenarioStepChoice startChoice = new ScenarioStepChoice();
        startChoice.setId("choice_start");
        startChoice.setText("I understand the situation");
        startChoice.setCorrect(true);
        startChoice.setNextStepId("step_situation");
        warningStep.addChoice(startChoice);
        
        scenario.addStep(warningStep);
        scenario.setFirstStepId("step_warning");

        // Step 2: Situation Evaluation
        ScenarioStep situationStep = new ScenarioStep();
        situationStep.setId("step_situation");
        situationStep.setPhase(ScenarioPhase.SITUATION_EVALUATION);
        situationStep.setTitle("What should you do?");
        situationStep.setDescription("The shaking is intensifying. You have seconds to react. A heavy cabinet is teetering above you, and the exit door is to your left.");
        
        ScenarioStepChoice choice1 = new ScenarioStepChoice();
        choice1.setId("choice_drop_cover");
        choice1.setText("DROP, COVER, and HOLD ON under sturdy furniture");
        choice1.setDescription("Get under a sturdy table or desk and hold on");
        choice1.setCorrect(true);
        choice1.setExplanation("Correct! DROP, COVER, and HOLD ON protects you from falling objects and debris.");
        choice1.setHealthImpact(0);
        choice1.setNextStepId("step_supplies");
        
        ScenarioStepChoice choice2 = new ScenarioStepChoice();
        choice2.setId("choice_run_outside");
        choice2.setText("RUN outside immediately");
        choice2.setDescription("Try to get outside as fast as possible");
        choice2.setCorrect(false);
        choice2.setExplanation("Incorrect! Running during shaking is dangerous due to falling debris.");
        choice2.setHealthImpact(-30);
        choice2.setNextStepId("step_supplies");
        
        ScenarioStepChoice choice3 = new ScenarioStepChoice();
        choice3.setId("choice_grab_bag");
        choice3.setText("GRAB your emergency bag and head for the door");
        choice3.setDescription("Quickly grab supplies and leave");
        choice3.setCorrect(false);
        choice3.setExplanation("Incorrect! Doorways are not safer than other areas. Taking time to grab supplies risks injury.");
        choice3.setHealthImpact(-20);
        choice3.setNextStepId("step_supplies");
        
        situationStep.addChoice(choice1);
        situationStep.addChoice(choice2);
        situationStep.addChoice(choice3);
        
        scenario.addStep(situationStep);

        // Step 3: Supplies Selection
        ScenarioStep suppliesStep = new ScenarioStep();
        suppliesStep.setId("step_supplies");
        suppliesStep.setPhase(ScenarioPhase.SUPPLIES_SELECTION);
        suppliesStep.setTitle("Select Emergency Supplies");
        suppliesStep.setDescription("The shaking has stopped but aftershocks are possible. Quickly select up to 8 items to take with you.");
        suppliesStep.setRequiresSupplies(true);
        suppliesStep.setMaxSuppliesAllowed(8);
        suppliesStep.setTimeLimitSeconds(30);
        
        // No choices for supplies step - handled by selectSupplies method
        // After supplies are selected, advance to evacuation
        suppliesStep.setTerminal(false);
        
        scenario.addStep(suppliesStep);

        // Step 4: Evacuation Actions
        ScenarioStep evacuationStep = new ScenarioStep();
        evacuationStep.setId("step_evacuation");
        evacuationStep.setPhase(ScenarioPhase.EVACUATION_ACTIONS);
        evacuationStep.setTitle("Evacuation Decision");
        evacuationStep.setDescription("The building structure may be compromised. You can see cracks in the walls and debris blocking some paths. The stairs are to your left, elevator to your right.");
        
        ScenarioStepChoice evacChoice1 = new ScenarioStepChoice();
        evacChoice1.setId("evac_stairs");
        evacChoice1.setText("Take the stairs, watching for debris");
        evacChoice1.setDescription("Use stairs carefully, avoid debris");
        evacChoice1.setCorrect(true);
        evacChoice1.setExplanation("Correct! Elevators are dangerous during and after earthquakes. Use stairs carefully.");
        evacChoice1.setHealthImpact(0);
        evacChoice1.setNextStepId("step_conditions");
        
        ScenarioStepChoice evacChoice2 = new ScenarioStepChoice();
        evacChoice2.setId("evac_elevator");
        evacChoice2.setText("Use the elevator to reach ground floor quickly");
        evacChoice2.setDescription("Take elevator for speed");
        evacChoice2.setCorrect(false);
        evacChoice2.setExplanation("Incorrect! Elevators can fail or trap you during earthquakes.");
        evacChoice2.setHealthImpact(-40);
        evacChoice2.setNextStepId("step_conditions");
        
        ScenarioStepChoice evacChoice3 = new ScenarioStepChoice();
        evacChoice3.setId("evac_wait");
        evacChoice3.setText("Wait inside until officials confirm it's safe");
        evacChoice3.setDescription("Stay put and wait");
        evacChoice3.setCorrect(false);
        evacChoice3.setExplanation("Incorrect! After shaking stops, you should evacuate to an open area away from buildings.");
        evacChoice3.setHealthImpact(-20);
        evacChoice3.setNextStepId("step_conditions");
        
        evacuationStep.addChoice(evacChoice1);
        evacuationStep.addChoice(evacChoice2);
        evacuationStep.addChoice(evacChoice3);
        
        scenario.addStep(evacuationStep);

        // Step 5: Changing Conditions
        ScenarioStep conditionsStep = new ScenarioStep();
        conditionsStep.setId("step_conditions");
        conditionsStep.setPhase(ScenarioPhase.CHANGING_CONDITIONS);
        conditionsStep.setTitle("Aftershock!");
        conditionsStep.setDescription("A strong aftershock hits! Loose items fall around you. Some pathways are now blocked.");
        conditionsStep.setAnimationUrl("aftershock.json");
        conditionsStep.setSoundUrl("aftershock_sound.mp3");
        
        // Introduce a new condition
        DisasterCondition aftershockCondition = new DisasterCondition();
        aftershockCondition.setId("aftershock");
        aftershockCondition.setDescription("Strong aftershock causing additional falling debris");
        aftershockCondition.setSeverity("HIGH");
        aftershockCondition.setTimeToImpact(0);
        aftershockCondition.setEffectOnPlayer("Blocked paths, falling debris risk");
        aftershockCondition.setRecommendedAction("Stay low, protect head, move carefully");
        aftershockCondition.setLifeThreatening(true);
        conditionsStep.addCondition(aftershockCondition);
        
        ScenarioStepChoice conditionChoice = new ScenarioStepChoice();
        conditionChoice.setId("condition_continue");
        conditionChoice.setText("Continue to safety");
        conditionChoice.setDescription("Move carefully despite aftershock");
        conditionChoice.setCorrect(true);
        conditionChoice.setExplanation("You navigate through the aftershock carefully, protecting your head.");
        conditionChoice.setHealthImpact(-10);
        conditionChoice.setNextStepId("step_consequences");
        
        conditionsStep.addChoice(conditionChoice);
        
        scenario.addStep(conditionsStep);

        // Step 6: Consequences
        ScenarioStep consequencesStep = new ScenarioStep();
        consequencesStep.setId("step_consequences");
        consequencesStep.setPhase(ScenarioPhase.CONSEQUENCES);
        consequencesStep.setTitle("Immediate Consequences");
        consequencesStep.setDescription("You've reached the designated safe zone. Emergency responders are arriving.");
        consequencesStep.setAnimationUrl("safe_zone.json");
        
        ScenarioStepChoice consequenceChoice = new ScenarioStepChoice();
        consequenceChoice.setId("consequence_view_feedback");
        consequenceChoice.setText("View my performance");
        consequenceChoice.setDescription("See how you did");
        consequenceChoice.setCorrect(true);
        consequenceChoice.setNextStepId("step_feedback");
        
        consequencesStep.addChoice(consequenceChoice);
        
        scenario.addStep(consequencesStep);

        // Step 7: Feedback (Terminal)
        ScenarioStep feedbackStep = new ScenarioStep();
        feedbackStep.setId("step_feedback");
        feedbackStep.setPhase(ScenarioPhase.FEEDBACK);
        feedbackStep.setTitle("Scenario Complete");
        feedbackStep.setDescription("Review your decisions and learn from your experience.");
        feedbackStep.setTerminal(true);
        
        scenario.addStep(feedbackStep);
    }

    // Basic scenarios for other disaster types (can be expanded in future phases)
    private ImmersiveScenario createTyphoonScenario() {
        ImmersiveScenario scenario = new ImmersiveScenario();
        scenario.setId("typhoon_house_defender");
        scenario.setType(DisasterSimulationType.TYPHOON);
        scenario.setTitle("Typhoon House Defender");
        scenario.setDescription("Secure your home against a powerful typhoon.");
        scenario.setWarningMessage("A typhoon is approaching with strong winds and heavy rain.");
        scenario.setWarningAnimationUrl("typhoon_warning.json");
        scenario.setWarningSoundUrl("typhoon_alert.mp3");
        scenario.setMaxSuppliesAllowed(10);
        scenario.setTotalTimeLimitSeconds(600);
        scenario.setXpReward(150);
        scenario.setCoinReward(75);
        scenario.setDifficulty(3);
        scenario.setUnlocked(true);
        scenario.setAvailableSupplies(createTyphoonSupplies());
        
        // Add basic structure - will be fully implemented in future phase
        ScenarioStep warningStep = new ScenarioStep("step_typhoon_warning", ScenarioPhase.WARNING, 
                "TYPHOON WARNING", "Typhoon approaching. Secure your home.");
        warningStep.setTerminal(true);
        scenario.addStep(warningStep);
        scenario.setFirstStepId("step_typhoon_warning");
        
        return scenario;
    }

    private ImmersiveScenario createFloodScenario() {
        ImmersiveScenario scenario = new ImmersiveScenario();
        scenario.setId("flood_rescue");
        scenario.setType(DisasterSimulationType.FLOOD);
        scenario.setTitle("Flood Rescue");
        scenario.setDescription("Navigate rising floodwaters to safety.");
        scenario.setWarningMessage("Flash flood warning in your area. Move to higher ground immediately.");
        scenario.setWarningAnimationUrl("flood_warning.json");
        scenario.setWarningSoundUrl("flood_alert.mp3");
        scenario.setMaxSuppliesAllowed(8);
        scenario.setTotalTimeLimitSeconds(300);
        scenario.setXpReward(120);
        scenario.setCoinReward(60);
        scenario.setDifficulty(2);
        scenario.setUnlocked(true);
        scenario.setAvailableSupplies(createFloodSupplies());
        
        ScenarioStep warningStep = new ScenarioStep("step_flood_warning", ScenarioPhase.WARNING,
                "FLOOD WARNING", "Water levels rising rapidly. Move to higher ground.");
        warningStep.setTerminal(true);
        scenario.addStep(warningStep);
        scenario.setFirstStepId("step_flood_warning");
        
        return scenario;
    }

    private ImmersiveScenario createFireScenario() {
        ImmersiveScenario scenario = new ImmersiveScenario();
        scenario.setId("fire_escape");
        scenario.setType(DisasterSimulationType.FIRE);
        scenario.setTitle("Fire Escape");
        scenario.setDescription("Escape a building fire safely.");
        scenario.setWarningMessage("Fire alarm activated. Evacuate immediately.");
        scenario.setWarningAnimationUrl("fire_warning.json");
        scenario.setWarningSoundUrl("fire_alert.mp3");
        scenario.setMaxSuppliesAllowed(6);
        scenario.setTotalTimeLimitSeconds(180);
        scenario.setXpReward(100);
        scenario.setCoinReward(50);
        scenario.setDifficulty(2);
        scenario.setUnlocked(true);
        scenario.setAvailableSupplies(createFireSupplies());
        
        ScenarioStep warningStep = new ScenarioStep("step_fire_warning", ScenarioPhase.WARNING,
                "FIRE WARNING", "Fire detected. Evacuate using stairs, not elevators.");
        warningStep.setTerminal(true);
        scenario.addStep(warningStep);
        scenario.setFirstStepId("step_fire_warning");
        
        return scenario;
    }

    private List<EmergencySupply> createTyphoonSupplies() {
        List<EmergencySupply> supplies = new ArrayList<>();
        supplies.add(new EmergencySupply("1", "Flashlight", "Essential during power outages", "", 1, 10, true, "Lighting"));
        supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for injuries", "", 2, 10, true, "Medical"));
        supplies.add(new EmergencySupply("3", "Water Bottle", "Stay hydrated", "", 1, 9, true, "Food & Water"));
        supplies.add(new EmergencySupply("4", "Radio", "Weather updates", "", 1, 8, true, "Communication"));
        supplies.add(new EmergencySupply("5", "Emergency Blanket", "Protection from wind/rain", "", 1, 7, true, "Shelter"));
        return supplies;
    }

    private List<EmergencySupply> createFloodSupplies() {
        List<EmergencySupply> supplies = new ArrayList<>();
        supplies.add(new EmergencySupply("1", "Life Jacket", "Essential for water safety", "", 1, 10, true, "Safety"));
        supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for injuries", "", 2, 10, true, "Medical"));
        supplies.add(new EmergencySupply("3", "Water Bottle", "Clean drinking water", "", 1, 9, true, "Food & Water"));
        supplies.add(new EmergencySupply("4", "Waterproof Bag", "Keep items dry", "", 1, 8, true, "Storage"));
        supplies.add(new EmergencySupply("5", "Whistle", "Signal for rescue", "", 0, 7, true, "Signaling"));
        return supplies;
    }

    private List<EmergencySupply> createFireSupplies() {
        List<EmergencySupply> supplies = new ArrayList<>();
        supplies.add(new EmergencySupply("1", "Flashlight", "Navigate through smoke", "", 1, 10, true, "Lighting"));
        supplies.add(new EmergencySupply("2", "Wet Cloth", "Cover mouth for smoke", "", 0, 10, true, "Protection"));
        supplies.add(new EmergencySupply("3", "First Aid Kit", "Treat burns", "", 2, 9, true, "Medical"));
        supplies.add(new EmergencySupply("4", "Phone", "Call emergency services", "", 1, 9, true, "Communication"));
        supplies.add(new EmergencySupply("5", "Whistle", "Signal for help", "", 0, 8, true, "Signaling"));
        return supplies;
    }
}
