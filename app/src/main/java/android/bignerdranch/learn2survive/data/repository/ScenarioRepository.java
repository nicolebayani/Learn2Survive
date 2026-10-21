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
        try {
            initializeScenarios();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize scenarios: " + e.getMessage(), e);
        }
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
        // Temporarily only create Earthquake scenario to debug
        try {
            ImmersiveScenario earthquake = createEarthquakeScenario();
            scenarioCache.put("earthquake_first_60_seconds", earthquake);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create earthquake scenario: " + e.getMessage(), e);
        }
        
        // Comment out other scenarios temporarily
        /*
        try {
            ImmersiveScenario typhoon = createTyphoonScenario();
            scenarioCache.put("typhoon_house_defender", typhoon);
        } catch (Exception e) {
            System.err.println("Failed to create typhoon scenario: " + e.getMessage());
        }
        
        try {
            ImmersiveScenario flood = createFloodScenario();
            scenarioCache.put("flood_rescue", flood);
        } catch (Exception e) {
            System.err.println("Failed to create flood scenario: " + e.getMessage());
        }
        
        try {
            ImmersiveScenario fire = createFireScenario();
            scenarioCache.put("fire_escape", fire);
        } catch (Exception e) {
            System.err.println("Failed to create fire scenario: " + e.getMessage());
        }
        */
    }

    private ImmersiveScenario createEarthquakeScenario() {
        ImmersiveScenario scenario = new ImmersiveScenario();
        scenario.setId("earthquake_first_60_seconds");
        scenario.setType(DisasterSimulationType.EARTHQUAKE);
        scenario.setTitle("EARTHQUAKE: THE FIRST 60 SECONDS");
        scenario.setDescription("Experience the critical first minute of an earthquake and learn the correct survival actions to stay safe.");
        scenario.setWarningMessage("⚠️ EARTHQUAKE WARNING: A strong earthquake has started in your area. DROP, COVER, and HOLD ON!");
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
        // Step 1: DISASTER WARNING
        ScenarioStep warningStep = new ScenarioStep();
        warningStep.setId("step_warning");
        warningStep.setPhase(ScenarioPhase.WARNING);
        warningStep.setTitle("⚠️ EARTHQUAKE WARNING");
        warningStep.setDescription("LOCATION: You are in your home office on the second floor of a 3-story residential building.\n\nSITUATION: The ground suddenly begins to shake violently. Books fall from shelves, furniture slides across the floor, and you can hear glass breaking nearby. The shaking is rapidly intensifying.\n\nSAFETY NOTE: During an earthquake, the first 60 seconds are critical. Your immediate action determines your safety.");
        warningStep.setAnimationUrl("earthquake_shaking.json");
        warningStep.setSoundUrl("earthquake_rumble.mp3");
        warningStep.setTimeLimitSeconds(8);
        
        ScenarioStepChoice startChoice = new ScenarioStepChoice();
        startChoice.setId("choice_start");
        startChoice.setText("I'm ready to start");
        startChoice.setCorrect(true);
        startChoice.setNextStepId("step_situation");
        warningStep.addChoice(startChoice);
        
        scenario.addStep(warningStep);
        scenario.setFirstStepId("step_warning");

        // Step 2: EVALUATE THE SITUATION
        ScenarioStep situationStep = new ScenarioStep();
        situationStep.setId("step_situation");
        situationStep.setPhase(ScenarioPhase.SITUATION_EVALUATION);
        situationStep.setTitle("What should you do NOW?");
        situationStep.setDescription("HAZARDS IDENTIFIED:\n• Heavy cabinet teetering directly above you\n• Floor is buckling, creating trip hazards\n• Picture frames falling from walls\n• Ceiling tiles beginning to crack\n• Exit door is 15 feet to your left\n• Large sturdy desk is 5 feet in front of you\n\nTIME: You have less than 10 seconds before the shaking intensifies further.\n\nQUESTION: What is your immediate protective action?");
        
        ScenarioStepChoice choice1 = new ScenarioStepChoice();
        choice1.setId("choice_drop_cover");
        choice1.setText("DROP, COVER, and HOLD ON under the sturdy desk");
        choice1.setDescription("Get under the desk immediately, cover your head and neck, hold on firmly");
        choice1.setCorrect(true);
        choice1.setExplanation("✓ CORRECT! DROP, COVER, and HOLD ON is the standard earthquake safety procedure. This protects you from falling objects and debris. The desk provides overhead protection.");
        choice1.setHealthImpact(0);
        choice1.setNextStepId("step_supplies");
        
        ScenarioStepChoice choice2 = new ScenarioStepChoice();
        choice2.setId("choice_run_outside");
        choice2.setText("RUN outside immediately to get away");
        choice2.setDescription("Try to get outside as fast as possible through the exit");
        choice2.setCorrect(false);
        choice2.setExplanation("✗ INCORRECT! Running during shaking is extremely dangerous. Falling debris, glass, and collapsing structures pose severe injury risks. You cannot move faster than the ground beneath you.");
        choice2.setHealthImpact(-35);
        choice2.setNextStepId("step_supplies");
        
        ScenarioStepChoice choice3 = new ScenarioStepChoice();
        choice3.setId("choice_grab_bag");
        choice3.setText("GRAB your emergency bag and head for the door");
        choice3.setDescription("Quickly grab your emergency supplies and evacuate");
        choice3.setCorrect(false);
        choice3.setExplanation("✗ INCORRECT! Doorways are not safer than other areas in modern buildings. Taking time to grab supplies when you need immediate protection risks injury from falling objects.");
        choice3.setHealthImpact(-25);
        choice3.setNextStepId("step_supplies");
        
        situationStep.addChoice(choice1);
        situationStep.addChoice(choice2);
        situationStep.addChoice(choice3);
        
        scenario.addStep(situationStep);

        // Step 3: SELECT EMERGENCY SUPPLIES
        ScenarioStep suppliesStep = new ScenarioStep();
        suppliesStep.setId("step_supplies");
        suppliesStep.setPhase(ScenarioPhase.SUPPLIES_SELECTION);
        suppliesStep.setTitle("Select Emergency Supplies");
        suppliesStep.setDescription("The shaking has stopped but aftershocks are possible. You have a short window to grab supplies before evacuating.\n\nINSTRUCTIONS: Select up to 8 items from the available supplies. Consider weight, importance, and essentiality.\n\nHINT: Some items are essential for survival, some are useful but not critical, and some could actually slow you down or be dangerous.");
        suppliesStep.setRequiresSupplies(true);
        suppliesStep.setMaxSuppliesAllowed(8);
        suppliesStep.setTimeLimitSeconds(45);
        
        // No choices for supplies step - handled by selectSupplies method
        // After supplies are selected, advance to evacuation
        suppliesStep.setTerminal(false);
        
        scenario.addStep(suppliesStep);

        // Step 4: CHOOSE EVACUATION ACTIONS
        ScenarioStep evacuationStep = new ScenarioStep();
        evacuationStep.setId("step_evacuation");
        evacuationStep.setPhase(ScenarioPhase.EVACUATION_ACTIONS);
        evacuationStep.setTitle("Evacuation Decision");
        evacuationStep.setDescription("BUILDING ASSESSMENT:\n• Visible cracks in load-bearing walls\n• Some ceiling tiles collapsed\n• Light fixtures swinging dangerously\n• Stairs to your left appear usable but have debris\n• Elevator to your right (not recommended during/after earthquakes)\n• Main exit leads to open area 3 blocks away\n• Windows showing damaged exterior\n\nGOAL: Reach the designated safe zone outside, away from buildings and power lines.\n\nQUESTION: How will you evacuate?");
        
        ScenarioStepChoice evacChoice1 = new ScenarioStepChoice();
        evacChoice1.setId("evac_stairs");
        evacChoice1.setText("Take the stairs carefully, watching for debris");
        evacChoice1.setDescription("Use stairs, move slowly, avoid falling debris, hold railings");
        evacChoice1.setCorrect(true);
        evacChoice1.setExplanation("✓ CORRECT! Stairs are the safest evacuation route during and after earthquakes. Elevators can fail or trap you. Moving carefully prevents injury from debris.");
        evacChoice1.setHealthImpact(0);
        evacChoice1.setNextStepId("step_conditions");
        
        ScenarioStepChoice evacChoice2 = new ScenarioStepChoice();
        evacChoice2.setId("evac_elevator");
        evacChoice2.setText("Use the elevator to reach ground floor quickly");
        evacChoice2.setDescription("Take elevator for speed to ground floor");
        evacChoice2.setCorrect(false);
        evacChoice2.setExplanation("✗ INCORRECT! Elevators are extremely dangerous during and after earthquakes. Power failure can trap you, the shaft can collapse, and doors may not open. Always use stairs.");
        evacChoice2.setHealthImpact(-45);
        evacChoice2.setNextStepId("step_conditions");
        
        ScenarioStepChoice evacChoice3 = new ScenarioStepChoice();
        evacChoice3.setId("evac_wait");
        evacChoice3.setText("Wait inside until officials confirm it's safe");
        evacChoice3.setDescription("Stay put and wait for emergency responders");
        evacChoice3.setCorrect(false);
        evacChoice3.setExplanation("✗ INCORRECT! After shaking stops, you should evacuate to an open area away from buildings. Staying inside risks injury from aftershocks and structural collapse. Move quickly but carefully.");
        evacChoice3.setHealthImpact(-25);
        evacChoice3.setNextStepId("step_conditions");
        
        evacuationStep.addChoice(evacChoice1);
        evacuationStep.addChoice(evacChoice2);
        evacuationStep.addChoice(evacChoice3);
        
        scenario.addStep(evacuationStep);

        // Step 5: FACE CHANGING CONDITIONS
        ScenarioStep conditionsStep = new ScenarioStep();
        conditionsStep.setId("step_conditions");
        conditionsStep.setPhase(ScenarioPhase.CHANGING_CONDITIONS);
        conditionsStep.setTitle("⚠️ AFTERSHOCK WARNING!");
        conditionsStep.setDescription("CONDITION UPDATE: A strong aftershock is beginning.\n\nNEW HAZARDS:\n• Intense shaking resuming\n• More ceiling debris falling\n• Some evacuation paths now blocked\n• Structural groaning sounds from above\n• Dust cloud limiting visibility\n\nTIME CRITICAL: You must decide how to respond while aftershock continues.\n\nOBJECTIVE: Continue to the safe zone while adapting to changing conditions.");
        conditionsStep.setAnimationUrl("aftershock.json");
        conditionsStep.setSoundUrl("aftershock_sound.mp3");
        
        // Introduce a new condition
        DisasterCondition aftershockCondition = new DisasterCondition();
        aftershockCondition.setId("aftershock");
        aftershockCondition.setDescription("Strong aftershock causing additional falling debris and blocked paths");
        aftershockCondition.setSeverity("HIGH");
        aftershockCondition.setTimeToImpact(0);
        aftershockCondition.setEffectOnPlayer("Limited visibility, blocked paths, falling debris risk");
        aftershockCondition.setRecommendedAction("Stay low, protect head, move carefully to alternate route");
        aftershockCondition.setLifeThreatening(true);
        conditionsStep.addCondition(aftershockCondition);
        
        ScenarioStepChoice conditionChoice1 = new ScenarioStepChoice();
        conditionChoice1.setId("condition_adapt");
        conditionChoice1.setText("Stay low, protect head, find alternate route through debris");
        conditionChoice1.setDescription("Adapt to conditions, protect yourself, find new path");
        conditionChoice1.setCorrect(true);
        conditionChoice1.setExplanation("✓ CORRECT! Adapting to changing conditions is essential. Staying low protects from falling debris. Finding alternate routes shows good situational awareness.");
        conditionChoice1.setHealthImpact(-5);
        conditionChoice1.setNextStepId("step_consequences");
        
        ScenarioStepChoice conditionChoice2 = new ScenarioStepChoice();
        conditionChoice2.setId("condition_ignore");
        conditionChoice2.setText("Continue moving quickly, ignore debris");
        conditionChoice2.setDescription("Push through regardless of conditions");
        conditionChoice2.setCorrect(false);
        conditionChoice2.setExplanation("✗ INCORRECT! Ignoring changing conditions increases injury risk. Moving quickly through debris without protection is dangerous. Adaptability is key to survival.");
        conditionChoice2.setHealthImpact(-20);
        conditionChoice2.setNextStepId("step_consequences");
        
        conditionsStep.addChoice(conditionChoice1);
        conditionsStep.addChoice(conditionChoice2);
        
        scenario.addStep(conditionsStep);

        // Step 6: EXPERIENCE CONSEQUENCES
        ScenarioStep consequencesStep = new ScenarioStep();
        consequencesStep.setId("step_consequences");
        consequencesStep.setPhase(ScenarioPhase.CONSEQUENCES);
        consequencesStep.setTitle("Consequences");
        consequencesStep.setDescription("You have reached the designated safe zone. Emergency responders are arriving and you can see emergency vehicles approaching.\n\nYour decisions throughout the scenario have determined your physical condition and ability to evacuate successfully.\n\nThe final results will show how well you handled each critical decision point.");
        consequencesStep.setAnimationUrl("safe_zone.json");
        
        ScenarioStepChoice consequenceChoice = new ScenarioStepChoice();
        consequenceChoice.setId("consequence_view_feedback");
        consequenceChoice.setText("View my detailed performance");
        consequenceChoice.setDescription("See complete analysis and scoring");
        consequenceChoice.setCorrect(true);
        consequenceChoice.setNextStepId("step_feedback");
        
        consequencesStep.addChoice(consequenceChoice);
        
        scenario.addStep(consequencesStep);

        // Step 7: RECEIVE PREPAREDNESS FEEDBACK
        ScenarioStep feedbackStep = new ScenarioStep();
        feedbackStep.setId("step_feedback");
        feedbackStep.setPhase(ScenarioPhase.FEEDBACK);
        feedbackStep.setTitle("Preparedness Feedback");
        feedbackStep.setDescription("Your complete performance analysis will show what you did well, what could be improved, and practical tips for earthquake preparedness.");
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
