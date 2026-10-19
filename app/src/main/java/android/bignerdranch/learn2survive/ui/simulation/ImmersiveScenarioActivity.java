package android.bignerdranch.learn2survive.ui.simulation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.DisasterCondition;
import android.bignerdranch.learn2survive.domain.model.DisasterSimulationType;
import android.bignerdranch.learn2survive.domain.model.EmergencySupply;
import android.bignerdranch.learn2survive.domain.model.ImmersiveScenario;
import android.bignerdranch.learn2survive.domain.model.ScenarioPhase;
import android.bignerdranch.learn2survive.domain.model.ScenarioState;
import android.bignerdranch.learn2survive.domain.model.SimulationResult;
import android.bignerdranch.learn2survive.domain.repository.SimulationRepository;
import android.bignerdranch.learn2survive.data.remote.SimulationRepositoryImpl;
import android.bignerdranch.learn2survive.ui.simulation.adapters.SupplySelectionAdapter;

public class ImmersiveScenarioActivity extends AppCompatActivity {
    public static final String EXTRA_SCENARIO_ID = "scenario_id";

    // Warning Screen Views
    private LinearLayout warningScreen;
    private TextView warningIconTextView;
    private TextView warningTitleTextView;
    private LottieAnimationView warningAnimationView;
    private TextView warningMessageTextView;
    private TextView countdownTextView;
    private Button startScenarioButton;

    // Situation Screen Views
    private LinearLayout situationScreen;
    private TextView situationTitleTextView;
    private ImageView situationImageView;
    private TextView situationDescriptionTextView;
    private TextView situationQuestionTextView;
    private Button situationChoice1Button;
    private Button situationChoice2Button;
    private Button situationChoice3Button;

    // Supplies Screen Views
    private LinearLayout suppliesScreen;
    private TextView suppliesTitleTextView;
    private TextView suppliesLimitTextView;
    private TextView suppliesCountTextView;
    private TextView backpackStatusTextView;
    private RecyclerView suppliesRecyclerView;
    private Button confirmSuppliesButton;

    // Evacuation Screen Views
    private LinearLayout evacuationScreen;
    private TextView evacuationTitleTextView;
    private TextView evacuationDescriptionTextView;
    private Button evacuationChoice1Button;
    private Button evacuationChoice2Button;
    private Button evacuationChoice3Button;

    // Conditions Screen Views
    private LinearLayout conditionsScreen;
    private TextView conditionsTitleTextView;
    private LottieAnimationView conditionsAnimationView;
    private TextView conditionsDescriptionTextView;
    private TextView conditionsQuestionTextView;
    private Button conditionsChoice1Button;
    private Button conditionsChoice2Button;

    // Consequences Screen Views
    private LinearLayout consequencesScreen;
    private TextView consequencesTitleTextView;
    private LottieAnimationView consequencesAnimationView;
    private TextView consequencesDescriptionTextView;
    private Button viewFeedbackButton;

    // Feedback Screen Views
    private LinearLayout feedbackScreen;
    private TextView feedbackTitleTextView;
    private TextView survivalStatusTextView;
    private TextView feedbackDescriptionTextView;
    private TextView rewardsTextView;
    private Button returnToDashboardButton;

    private ProgressBar progressBar;

    private SimulationRepository simulationRepository;
    private ImmersiveScenario scenario;
    private ScenarioState scenarioState;
    private SupplySelectionAdapter supplyAdapter;
    private List<EmergencySupply> selectedSupplies;

    private MediaPlayer soundPlayer;
    private CountDownTimer countdownTimer;
    private CountDownTimer conditionsTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immersive_scenario);

        String scenarioId = getIntent().getStringExtra(EXTRA_SCENARIO_ID);

        simulationRepository = new SimulationRepositoryImpl();
        selectedSupplies = new ArrayList<>();
        scenarioState = new ScenarioState(scenarioId);

        initViews();
        loadScenario(scenarioId);
    }

    private void initViews() {
        // Warning Screen
        warningScreen = findViewById(R.id.warningScreen);
        warningIconTextView = findViewById(R.id.warningIconTextView);
        warningTitleTextView = findViewById(R.id.warningTitleTextView);
        warningAnimationView = findViewById(R.id.warningAnimationView);
        warningMessageTextView = findViewById(R.id.warningMessageTextView);
        countdownTextView = findViewById(R.id.countdownTextView);
        startScenarioButton = findViewById(R.id.startScenarioButton);

        // Situation Screen
        situationScreen = findViewById(R.id.situationScreen);
        situationTitleTextView = findViewById(R.id.situationTitleTextView);
        situationImageView = findViewById(R.id.situationImageView);
        situationDescriptionTextView = findViewById(R.id.situationDescriptionTextView);
        situationQuestionTextView = findViewById(R.id.situationQuestionTextView);
        situationChoice1Button = findViewById(R.id.situationChoice1Button);
        situationChoice2Button = findViewById(R.id.situationChoice2Button);
        situationChoice3Button = findViewById(R.id.situationChoice3Button);

        // Supplies Screen
        suppliesScreen = findViewById(R.id.suppliesScreen);
        suppliesTitleTextView = findViewById(R.id.suppliesTitleTextView);
        suppliesLimitTextView = findViewById(R.id.suppliesLimitTextView);
        suppliesCountTextView = findViewById(R.id.suppliesCountTextView);
        backpackStatusTextView = findViewById(R.id.backpackStatusTextView);
        suppliesRecyclerView = findViewById(R.id.suppliesRecyclerView);
        confirmSuppliesButton = findViewById(R.id.confirmSuppliesButton);

        // Evacuation Screen
        evacuationScreen = findViewById(R.id.evacuationScreen);
        evacuationTitleTextView = findViewById(R.id.evacuationTitleTextView);
        evacuationDescriptionTextView = findViewById(R.id.evacuationDescriptionTextView);
        evacuationChoice1Button = findViewById(R.id.evacuationChoice1Button);
        evacuationChoice2Button = findViewById(R.id.evacuationChoice2Button);
        evacuationChoice3Button = findViewById(R.id.evacuationChoice3Button);

        // Conditions Screen
        conditionsScreen = findViewById(R.id.conditionsScreen);
        conditionsTitleTextView = findViewById(R.id.conditionsTitleTextView);
        conditionsAnimationView = findViewById(R.id.conditionsAnimationView);
        conditionsDescriptionTextView = findViewById(R.id.conditionsDescriptionTextView);
        conditionsQuestionTextView = findViewById(R.id.conditionsQuestionTextView);
        conditionsChoice1Button = findViewById(R.id.conditionsChoice1Button);
        conditionsChoice2Button = findViewById(R.id.conditionsChoice2Button);

        // Consequences Screen
        consequencesScreen = findViewById(R.id.consequencesScreen);
        consequencesTitleTextView = findViewById(R.id.consequencesTitleTextView);
        consequencesAnimationView = findViewById(R.id.consequencesAnimationView);
        consequencesDescriptionTextView = findViewById(R.id.consequencesDescriptionTextView);
        viewFeedbackButton = findViewById(R.id.viewFeedbackButton);

        // Feedback Screen
        feedbackScreen = findViewById(R.id.feedbackScreen);
        feedbackTitleTextView = findViewById(R.id.feedbackTitleTextView);
        survivalStatusTextView = findViewById(R.id.survivalStatusTextView);
        feedbackDescriptionTextView = findViewById(R.id.feedbackDescriptionTextView);
        rewardsTextView = findViewById(R.id.rewardsTextView);
        returnToDashboardButton = findViewById(R.id.returnToDashboardButton);

        progressBar = findViewById(R.id.progressBar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Setup listeners
        startScenarioButton.setOnClickListener(v -> startScenario());
        confirmSuppliesButton.setOnClickListener(v -> confirmSupplies());
        viewFeedbackButton.setOnClickListener(v -> showFeedback());
        returnToDashboardButton.setOnClickListener(v -> returnToDashboard());
    }

    private void loadScenario(String scenarioId) {
        progressBar.setVisibility(View.VISIBLE);

        // For now, load the specific Earthquake scenario "The First 60 Seconds"
        // In production, this would load from Firestore
        scenario = createEarthquakeScenario();
        getSupportActionBar().setTitle(scenario.getTitle());
        progressBar.setVisibility(View.GONE);
        showWarningScreen();
    }

    private ImmersiveScenario createEarthquakeScenario() {
        ImmersiveScenario earthquakeScenario = new ImmersiveScenario();
        earthquakeScenario.setId("earthquake_first_60_seconds");
        earthquakeScenario.setType(DisasterSimulationType.EARTHQUAKE);
        earthquakeScenario.setTitle("The First 60 Seconds");
        earthquakeScenario.setWarningMessage("A strong earthquake has started in your area. DROP, COVER, and HOLD ON!");
        earthquakeScenario.setSituationDescription("You are inside your home when the ground suddenly begins shaking violently.");
        earthquakeScenario.setXpReward(100);
        earthquakeScenario.setCoinReward(50);
        earthquakeScenario.setDifficulty(2); // 1=Easy, 2=Medium, 3=Hard
        earthquakeScenario.setTotalTimeLimitSeconds(300);
        earthquakeScenario.setMaxSuppliesAllowed(8);
        earthquakeScenario.setAvailableSupplies(createDefaultSupplies(DisasterSimulationType.EARTHQUAKE));
        earthquakeScenario.setWarningAnimationUrl("earthquake_warning.json");
        earthquakeScenario.setWarningSoundUrl("earthquake_alert.mp3");
        earthquakeScenario.setImageUrl("https://example.com/earthquake_scene.jpg");

        return earthquakeScenario;
    }

    private ImmersiveScenario convertToImmersiveScenario(android.bignerdranch.learn2survive.domain.model.DisasterScenario oldScenario) {
        ImmersiveScenario immersiveScenario = new ImmersiveScenario();
        immersiveScenario.setId(oldScenario.getId());
        immersiveScenario.setType(oldScenario.getType());
        immersiveScenario.setTitle(oldScenario.getTitle());
        immersiveScenario.setWarningMessage("A " + oldScenario.getType().name().toLowerCase() + " has started in your area!");
        immersiveScenario.setSituationDescription(oldScenario.getDescription());
        immersiveScenario.setXpReward(oldScenario.getXpReward());
        immersiveScenario.setCoinReward(oldScenario.getCoinReward());
        immersiveScenario.setDifficulty(oldScenario.getDifficulty());
        immersiveScenario.setTotalTimeLimitSeconds(oldScenario.getTotalTimeLimitSeconds());
        immersiveScenario.setMaxSuppliesAllowed(8);
        immersiveScenario.setAvailableSupplies(createDefaultSupplies(oldScenario.getType()));
        return immersiveScenario;
    }

    private List<EmergencySupply> createDefaultSupplies(DisasterSimulationType type) {
        List<EmergencySupply> supplies = new ArrayList<>();

        switch (type) {
            case EARTHQUAKE:
                // Essential items
                supplies.add(new EmergencySupply("1", "Flashlight", "Essential for seeing in dark conditions after power outage", "", 1, 10, true, "Lighting"));
                supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for treating injuries from falling debris", "", 2, 10, true, "Medical"));
                supplies.add(new EmergencySupply("3", "Water Bottle", "Stay hydrated during evacuation and aftermath", "", 1, 9, true, "Food & Water"));
                supplies.add(new EmergencySupply("4", "Whistle", "Signal for help when trapped or injured", "", 0, 8, true, "Signaling"));
                supplies.add(new EmergencySupply("5", "Dust Mask", "Protection from dust and debris in collapsed areas", "", 0, 7, true, "Protection"));
                // Useful but not essential
                supplies.add(new EmergencySupply("6", "Emergency Blanket", "Protection from elements if stranded", "", 1, 6, false, "Shelter"));
                supplies.add(new EmergencySupply("7", "Radio", "Receive emergency broadcasts and updates", "", 1, 5, false, "Communication"));
                supplies.add(new EmergencySupply("8", "Phone Charger", "Keep your phone powered for communication", "", 1, 5, false, "Communication"));
                supplies.add(new EmergencySupply("9", "Power Bank", "Backup power for extended communication", "", 1, 4, false, "Communication"));
                supplies.add(new EmergencySupply("10", "Important Documents", "ID, insurance, and medical information", "", 0, 3, false, "Documents"));
                // Unnecessary items
                supplies.add(new EmergencySupply("11", "Laptop", "Not essential - heavy and can be replaced", "", 3, -5, false, "Electronics"));
                supplies.add(new EmergencySupply("12", "Umbrella", "Not useful during earthquake evacuation", "", 1, -3, false, "Protection"));
                supplies.add(new EmergencySupply("13", "Jewelry Box", "Valuable but not essential for survival", "", 2, -4, false, "Personal"));
                supplies.add(new EmergencySupply("14", "Gaming Console", "Completely unnecessary during emergency", "", 2, -5, false, "Electronics"));
                break;
            case FLOOD:
                // Essential items
                supplies.add(new EmergencySupply("1", "Life Jacket", "Essential for water safety during evacuation", "", 1, 10, true, "Safety"));
                supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for treating injuries during flood", "", 2, 10, true, "Medical"));
                supplies.add(new EmergencySupply("3", "Water Bottle", "Clean drinking water (flood water is contaminated)", "", 1, 9, true, "Food & Water"));
                supplies.add(new EmergencySupply("4", "Waterproof Bag", "Keep essential items dry during evacuation", "", 1, 8, true, "Storage"));
                supplies.add(new EmergencySupply("5", "Whistle", "Signal for rescue when stranded", "", 0, 7, true, "Signaling"));
                // Useful but not essential
                supplies.add(new EmergencySupply("6", "Flashlight", "See in dark flood conditions and at night", "", 1, 6, false, "Lighting"));
                supplies.add(new EmergencySupply("7", "Radio", "Weather updates and evacuation instructions", "", 1, 5, false, "Communication"));
                supplies.add(new EmergencySupply("8", "Warm Clothes", "Protection from cold water and hypothermia", "", 1, 5, false, "Clothing"));
                supplies.add(new EmergencySupply("9", "Phone Charger", "Keep communication possible", "", 1, 4, false, "Communication"));
                supplies.add(new EmergencySupply("10", "Important Documents", "Waterproof ID and important papers", "", 0, 3, false, "Documents"));
                // Unnecessary items
                supplies.add(new EmergencySupply("11", "Umbrella", "Not useful in flood conditions", "", 1, -4, false, "Protection"));
                supplies.add(new EmergencySupply("12", "Heavy Books", "Not essential and add weight", "", 3, -3, false, "Personal"));
                supplies.add(new EmergencySupply("13", "Television", "Completely unnecessary during flood", "", 5, -5, false, "Electronics"));
                supplies.add(new EmergencySupply("14", "Desktop Computer", "Heavy and not essential for survival", "", 4, -5, false, "Electronics"));
                break;
            case TYPHOON:
                // Essential items
                supplies.add(new EmergencySupply("1", "Flashlight", "Essential during power outages", "", 1, 10, true, "Lighting"));
                supplies.add(new EmergencySupply("2", "First Aid Kit", "Critical for injuries from wind debris", "", 2, 10, true, "Medical"));
                supplies.add(new EmergencySupply("3", "Water Bottle", "Stay hydrated during extended power outage", "", 1, 9, true, "Food & Water"));
                supplies.add(new EmergencySupply("4", "Radio", "Weather updates and typhoon warnings", "", 1, 8, true, "Communication"));
                supplies.add(new EmergencySupply("5", "Emergency Blanket", "Protection from wind and rain", "", 1, 7, true, "Shelter"));
                // Useful but not essential
                supplies.add(new EmergencySupply("6", "Phone Charger", "Keep communication possible", "", 1, 6, false, "Communication"));
                supplies.add(new EmergencySupply("7", "Waterproof Bag", "Keep items dry during storm", "", 1, 5, false, "Storage"));
                supplies.add(new EmergencySupply("8", "Canned Food", "Non-perishable food supply", "", 2, 5, false, "Food & Water"));
                supplies.add(new EmergencySupply("9", "Power Bank", "Backup power for extended outage", "", 1, 4, false, "Communication"));
                supplies.add(new EmergencySupply("10", "Important Documents", "ID and important papers in waterproof container", "", 0, 3, false, "Documents"));
                // Unnecessary items
                supplies.add(new EmergencySupply("11", "Umbrella", "Useless in typhoon winds", "", 1, -5, false, "Protection"));
                supplies.add(new EmergencySupply("12", "Lawn Furniture", "Dangerous in high winds", "", 5, -4, false, "Outdoor"));
                supplies.add(new EmergencySupply("13", "Outdoor Decorations", "Become dangerous projectiles", "", 3, -3, false, "Outdoor"));
                supplies.add(new EmergencySupply("14", "Large Mirror", "Dangerous if shattered by wind", "", 4, -4, false, "Household"));
                break;
            case FIRE:
                // Essential items
                supplies.add(new EmergencySupply("1", "Flashlight", "Navigate through smoke and dark areas", "", 1, 10, true, "Lighting"));
                supplies.add(new EmergencySupply("2", "Wet Cloth", "Cover mouth for smoke protection", "", 0, 10, true, "Protection"));
                supplies.add(new EmergencySupply("3", "First Aid Kit", "Treat burns and smoke inhalation", "", 2, 9, true, "Medical"));
                supplies.add(new EmergencySupply("4", "Phone", "Call emergency services immediately", "", 1, 9, true, "Communication"));
                supplies.add(new EmergencySupply("5", "Whistle", "Signal for help when trapped", "", 0, 8, true, "Signaling"));
                // Useful but not essential
                supplies.add(new EmergencySupply("6", "Water Bottle", "Stay hydrated and for firefighting", "", 1, 6, false, "Food & Water"));
                supplies.add(new EmergencySupply("7", "Emergency Blanket", "Protection from heat if trapped", "", 1, 5, false, "Shelter"));
                supplies.add(new EmergencySupply("8", "Dust Mask", "Additional protection from smoke", "", 0, 4, false, "Protection"));
                supplies.add(new EmergencySupply("9", "Power Bank", "Keep phone powered for emergency calls", "", 1, 3, false, "Communication"));
                supplies.add(new EmergencySupply("10", "Important Documents", "ID if evacuated to shelter", "", 0, 2, false, "Documents"));
                // Unnecessary items
                supplies.add(new EmergencySupply("11", "Umbrella", "Not useful during fire evacuation", "", 1, -4, false, "Protection"));
                supplies.add(new EmergencySupply("12", "Heavy Coat", "Can slow down evacuation", "", 2, -3, false, "Clothing"));
                supplies.add(new EmergencySupply("13", "Valuables", "Not worth risking life for", "", 1, -5, false, "Personal"));
                supplies.add(new EmergencySupply("14", "Large Electronics", "Heavy and can impede escape", "", 4, -4, false, "Electronics"));
                break;
        }

        return supplies;
    }

    private void showWarningScreen() {
        hideAllScreens();
        warningScreen.setVisibility(View.VISIBLE);

        warningTitleTextView.setText(scenario.getType().name() + " WARNING");
        warningMessageTextView.setText(scenario.getWarningMessage());

        // Play warning sound
        playSoundEffect(scenario.getWarningSoundUrl());

        // Load warning animation
        if (scenario.getWarningAnimationUrl() != null && !scenario.getWarningAnimationUrl().isEmpty()) {
            warningAnimationView.setAnimationFromUrl(scenario.getWarningAnimationUrl());
            warningAnimationView.playAnimation();
        }

        // Start countdown
        startCountdown(5);
    }

    private void startCountdown(int seconds) {
        countdownTextView.setVisibility(View.VISIBLE);
        startScenarioButton.setVisibility(View.GONE);

        countdownTimer = new CountDownTimer(seconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTextView.setText("Scenario starting in: " + (millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                countdownTextView.setVisibility(View.GONE);
                startScenarioButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void startScenario() {
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
        scenarioState.setWarningReceived(true);
        scenarioState.setCurrentPhase(ScenarioPhase.SITUATION_EVALUATION);
        showSituationScreen();
    }

    private void showSituationScreen() {
        hideAllScreens();
        situationScreen.setVisibility(View.VISIBLE);

        // Provide immersive scenario description with visual details
        String immersiveDescription = getImmersiveSituationDescription();
        situationDescriptionTextView.setText(immersiveDescription);

        // Load scenario image
        if (scenario.getImageUrl() != null && !scenario.getImageUrl().isEmpty()) {
            com.bumptech.glide.Glide.with(this)
                    .load(scenario.getImageUrl())
                    .into(situationImageView);
        }

        // Setup situation choices based on disaster type
        setupSituationChoices();
    }

    private String getImmersiveSituationDescription() {
        switch (scenario.getType()) {
            case EARTHQUAKE:
                return "You are inside your home when the ground suddenly begins shaking violently.\n\n" +
                       "VISUAL SITUATION:\n" +
                       "• Books and picture frames are falling from shelves\n" +
                       "• Furniture is sliding across the floor\n" +
                       "• Glass from a broken window is scattered nearby\n" +
                       "• Your emergency bag is visible in the corner\n" +
                       "• The main exit door is to your left\n" +
                       "• A heavy cabinet is teetering above you\n" +
                       "• The lights are flickering on and off\n\n" +
                       "The shaking is intensifying. You have seconds to react.";
            case FLOOD:
                return "You are in your apartment when you hear water rushing in from below.\n\n" +
                       "VISUAL SITUATION:\n" +
                       "• Water is rapidly rising from the ground floor\n" +
                       "• Furniture is beginning to float\n" +
                       "• Electrical outlets are sparking near the water\n" +
                       "• Your emergency supplies are on a high shelf\n" +
                       "• The stairs to the roof are to your right\n" +
                       "• Windows are showing water levels outside\n" +
                       "• Your phone shows a flood warning alert\n\n" +
                       "The water level is rising quickly. Time is critical.";
            case TYPHOON:
                return "You are at home when strong winds begin battering your area.\n\n" +
                       "VISUAL SITUATION:\n" +
                       "• Windows are rattling violently\n" +
                       "• Tree branches are breaking outside\n" +
                       "• Debris is flying past windows\n" +
                       "• Emergency kit is in the hallway closet\n" +
                       "• Interior rooms are available for shelter\n" +
                       "• Power lines are swaying dangerously\n" +
                       "• Radio is broadcasting emergency updates\n\n" +
                       "Wind speeds are increasing. You need to secure your position.";
            case FIRE:
                return "You are in your office building when the fire alarm sounds.\n\n" +
                       "VISUAL SITUATION:\n" +
                       "• Smoke is visible under the door\n" +
                       "• Emergency exit signs are glowing\n" +
                       "• Fire extinguisher is mounted on the wall\n" +
                       "• Wet cloth supplies are in the breakroom\n" +
                       "• Stairs are to your left, elevator to your right\n" +
                       "• Colleagues are confused and looking for guidance\n" +
                       "• Your phone shows emergency services number\n\n" +
                       "Smoke is thickening. You must act immediately.";
            default:
                return scenario.getSituationDescription();
        }
    }

    private void setupSituationChoices() {
        switch (scenario.getType()) {
            case EARTHQUAKE:
                situationQuestionTextView.setText("What should you do first in this situation?");
                situationChoice1Button.setText("DROP, COVER, and HOLD ON under sturdy furniture");
                situationChoice2Button.setText("RUN outside immediately to get away");
                situationChoice3Button.setText("GRAB your emergency bag and head for the door");
                break;
            case FLOOD:
                situationQuestionTextView.setText("What should you do first in this situation?");
                situationChoice1Button.setText("MOVE immediately to higher ground via the stairs");
                situationChoice2Button.setText("GRAB your emergency supplies from the high shelf");
                situationChoice3Button.setText("TRY to save important belongings from the water");
                break;
            case TYPHOON:
                situationQuestionTextView.setText("What should you do first in this situation?");
                situationChoice1Button.setText("SECURE the area and move to an interior room");
                situationChoice2Button.setText("GO outside to assess the damage and check conditions");
                situationChoice3Button.setText("GATHER colleagues and plan an evacuation route");
                break;
            case FIRE:
                situationQuestionTextView.setText("What should you do first in this situation?");
                situationChoice1Button.setText("ALERT others and move toward the emergency exit");
                situationChoice2Button.setText("OPEN windows to let the smoke escape");
                situationChoice3Button.setText("HIDE in the breakroom and wait for help");
                break;
        }

        situationChoice1Button.setOnClickListener(v -> onSituationChoice(1));
        situationChoice2Button.setOnClickListener(v -> onSituationChoice(2));
        situationChoice3Button.setOnClickListener(v -> onSituationChoice(3));
    }

    private void onSituationChoice(int choice) {
        boolean isCorrect = (choice == 1); // First choice is always correct for now
        scenarioState.addPhaseDecision("situation", String.valueOf(choice));
        scenarioState.setEvaluatedSituation(true);

        if (isCorrect) {
            scenarioState.addPhaseDecision("situation_result", "correct");
        } else {
            scenarioState.addPhaseDecision("situation_result", "wrong");
            scenarioState.decreaseHealth(20);
        }

        // Show immediate feedback for the decision
        showDecisionFeedback(isCorrect, getSituationChoiceExplanation(choice));
    }

    private String getSituationChoiceExplanation(int choice) {
        switch (scenario.getType()) {
            case EARTHQUAKE:
                switch (choice) {
                    case 1: return "Correct! DROP, COVER, and HOLD ON is the standard earthquake safety procedure. This protects you from falling objects and debris.";
                    case 2: return "Incorrect! Running outside during shaking is dangerous due to falling debris and collapsing structures.";
                    case 3: return "Incorrect! Doorways are not safer than other areas in modern buildings. DROP, COVER, and HOLD ON is safer.";
                }
                break;
            case FLOOD:
                switch (choice) {
                    case 1: return "Correct! Moving to higher ground immediately is the most important action during flooding. Every second counts.";
                    case 2: return "Incorrect! Belongings can be replaced, but your life cannot. Prioritize safety over possessions.";
                    case 3: return "Incorrect! In rapidly rising floodwaters, waiting for instructions can be fatal. Move to higher ground immediately.";
                }
                break;
            case TYPHOON:
                switch (choice) {
                    case 1: return "Correct! Securing the area and moving to an interior room protects you from wind and debris.";
                    case 2: return "Incorrect! Going outside during a typhoon is extremely dangerous due to flying debris and high winds.";
                    case 3: return "Incorrect! Continuing normal activities during a typhoon warning puts you at unnecessary risk.";
                }
                break;
            case FIRE:
                switch (choice) {
                    case 1: return "Correct! Alerting others and moving to the emergency exit is the safest action during a fire.";
                    case 2: return "Incorrect! Opening windows can actually feed the fire and spread smoke more quickly.";
                    case 3: return "Incorrect! Hiding during a fire is dangerous. You need to evacuate to safety.";
                }
                break;
        }
        return "";
    }

    private void showDecisionFeedback(boolean isCorrect, String explanation) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(isCorrect ? "Good Decision!" : "Poor Decision");
        builder.setMessage(explanation);
        builder.setPositiveButton("Continue", (dialog, which) -> {
            scenarioState.setCurrentPhase(ScenarioPhase.SUPPLIES_SELECTION);
            showSuppliesScreen();
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showSuppliesScreen() {
        hideAllScreens();
        suppliesScreen.setVisibility(View.VISIBLE);

        suppliesLimitTextView.setText("Select up to " + scenario.getMaxSuppliesAllowed() + " items");

        // Setup supplies recycler view
        supplyAdapter = new SupplySelectionAdapter(scenario.getAvailableSupplies(), selectedSupplies, scenario.getMaxSuppliesAllowed());
        supplyAdapter.setOnSupplySelectionListener(this::updateConfirmButton);
        suppliesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        suppliesRecyclerView.setAdapter(supplyAdapter);

        updateConfirmButton();
    }

    private void updateConfirmButton() {
        confirmSuppliesButton.setEnabled(selectedSupplies.size() > 0 && selectedSupplies.size() <= scenario.getMaxSuppliesAllowed());

        // Update supplies count display
        if (suppliesCountTextView != null) {
            suppliesCountTextView.setText("Selected: " + selectedSupplies.size() + "/" + scenario.getMaxSuppliesAllowed());
        }

        // Update backpack status
        int totalWeight = selectedSupplies.stream().mapToInt(EmergencySupply::getWeight).sum();
        String backpackStatus;
        if (selectedSupplies.size() == 0) {
            backpackStatus = "Backpack Status: Empty";
        } else if (totalWeight <= 3) {
            backpackStatus = "Backpack Status: Light";
        } else if (totalWeight <= 6) {
            backpackStatus = "Backpack Status: Moderate";
        } else {
            backpackStatus = "Backpack Status: Heavy";
        }
        if (backpackStatusTextView != null) {
            backpackStatusTextView.setText(backpackStatus);
        }
    }

    private void confirmSupplies() {
        scenarioState.setSelectedSupplies(selectedSupplies);
        scenarioState.setSelectedSupplies(true);

        // Provide feedback on supply selection
        showSuppliesFeedback();
    }

    private void showSuppliesFeedback() {
        int essentialSelected = (int) selectedSupplies.stream().filter(EmergencySupply::isEssential).count();
        int totalEssential = (int) scenario.getAvailableSupplies().stream().filter(EmergencySupply::isEssential).count();
        int unnecessarySelected = (int) selectedSupplies.stream().filter(s -> s.getImportance() < 0).count();
        int totalWeight = selectedSupplies.stream().mapToInt(EmergencySupply::getWeight).sum();

        StringBuilder feedback = new StringBuilder();
        feedback.append("EMERGENCY SUPPLIES SELECTION REVIEW\n\n");

        if (essentialSelected == totalEssential) {
            feedback.append("✓ EXCELLENT! You selected all essential items.\n");
            scenarioState.addPhaseDecision("supplies_essential", "perfect");
        } else if (essentialSelected >= totalEssential - 1) {
            feedback.append("✓ GOOD! You selected most essential items.\n");
            scenarioState.addPhaseDecision("supplies_essential", "good");
        } else {
            feedback.append("✗ POOR! You missed several essential items.\n");
            scenarioState.addPhaseDecision("supplies_essential", "poor");
            scenarioState.decreaseHealth(10);
        }

        feedback.append("Essential items selected: ").append(essentialSelected).append("/").append(totalEssential).append("\n\n");

        if (unnecessarySelected > 0) {
            feedback.append("⚠ WARNING: You selected ").append(unnecessarySelected).append(" unnecessary item(s).\n");
            feedback.append("These items may slow you down or be dangerous.\n");
            scenarioState.addPhaseDecision("supplies_unnecessary", "yes");
            scenarioState.decreaseHealth(5);
        } else {
            feedback.append("✓ Good! No unnecessary items selected.\n");
            scenarioState.addPhaseDecision("supplies_unnecessary", "no");
        }

        feedback.append("\nTotal weight: ").append(totalWeight).append(" units\n");
        feedback.append("This will affect your mobility during evacuation.\n\n");

        // List selected items
        feedback.append("YOUR SELECTION:\n");
        for (EmergencySupply supply : selectedSupplies) {
            String status = supply.isEssential() ? "[ESSENTIAL]" : (supply.getImportance() < 0 ? "[UNNECESSARY]" : "[USEFUL]");
            feedback.append("• ").append(supply.getName()).append(" ").append(status).append("\n");
        }

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Supply Selection Analysis");
        builder.setMessage(feedback.toString());
        builder.setPositiveButton("Continue to Evacuation", (dialog, which) -> {
            scenarioState.setCurrentPhase(ScenarioPhase.EVACUATION_ACTIONS);
            showEvacuationScreen();
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void showEvacuationScreen() {
        hideAllScreens();
        evacuationScreen.setVisibility(View.VISIBLE);

        // Setup evacuation choices based on disaster type
        setupEvacuationChoices();
    }

    private void setupEvacuationChoices() {
        switch (scenario.getType()) {
            case EARTHQUAKE:
                evacuationDescriptionTextView.setText("The shaking has stopped but aftershocks are possible. The building structure may be compromised. You can see cracks in the walls and debris blocking some paths. The stairs are to your left, elevator to your right. The designated safe zone is 3 blocks away.");
                evacuationChoice1Button.setText("Take the stairs, watching for debris and structural damage");
                evacuationChoice2Button.setText("Use the elevator to reach the ground floor quickly");
                evacuationChoice3Button.setText("Wait inside until officials confirm it's safe to move");
                break;
            case FLOOD:
                evacuationDescriptionTextView.setText("Water levels continue to rise around you. You can see the evacuation route is partially flooded but still passable. The designated high ground is 1 mile away. Time is critical. You have your emergency supplies.");
                evacuationChoice1Button.setText("Follow the designated evacuation route through shallow water");
                evacuationChoice2Button.setText("Take shortcuts through deeper water to save time");
                evacuationChoice3Button.setText("Wait for rescue personnel to arrive at your location");
                break;
            case TYPHOON:
                evacuationDescriptionTextView.setText("The evacuation center is 2 miles away. Wind speeds are dangerous and visibility is poor. You can see flying debris outside. The designated route passes through open areas. You have your emergency supplies with you.");
                evacuationChoice1Button.setText("Travel in a group, staying close to buildings for cover");
                evacuationChoice2Button.setText("Travel alone to move faster through the storm");
                evacuationChoice3Button.setText("Wait until the storm passes before evacuating");
                break;
            case FIRE:
                evacuationDescriptionTextView.setText("Smoke is thick and the fire is spreading. You can see the emergency exit sign glowing ahead. The hallway is becoming difficult to navigate. You can smell smoke coming from the elevator shaft. The safe meeting point is outside.");
                evacuationChoice1Button.setText("Move quickly toward the emergency exit, staying low to avoid smoke");
                evacuationChoice2Button.setText("Use the elevator to reach the ground floor");
                evacuationChoice3Button.setText("Wait in a safe room for firefighters to rescue you");
                break;
        }

        evacuationChoice1Button.setOnClickListener(v -> onEvacuationChoice(1));
        evacuationChoice2Button.setOnClickListener(v -> onEvacuationChoice(2));
        evacuationChoice3Button.setOnClickListener(v -> onEvacuationChoice(3));
    }

    private void onEvacuationChoice(int choice) {
        scenarioState.addPhaseDecision("evacuation", String.valueOf(choice));
        scenarioState.setEvacuated(true);

        // Store the evacuation choice to influence the next phase
        scenarioState.addPhaseDecision("evacuation_choice", String.valueOf(choice));

        // Move to changing conditions phase without immediate feedback
        scenarioState.setCurrentPhase(ScenarioPhase.CHANGING_CONDITIONS);
        showConditionsScreen();
    }

    private void showConditionsScreen() {
        hideAllScreens();
        conditionsScreen.setVisibility(View.VISIBLE);

        // Create dramatic changing conditions based on disaster type AND previous evacuation choice
        DisasterCondition condition = createChangingConditionBasedOnChoice();
        scenarioState.addCondition(condition);

        // Display dramatic environmental changes
        conditionsTitleTextView.setText("⚠️ CONDITIONS ARE CHANGING!");
        conditionsTitleTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));

        // Show detailed environmental description
        String environmentalDescription = buildEnvironmentalDescription(condition);
        conditionsDescriptionTextView.setText(environmentalDescription);

        conditionsQuestionTextView.setText("⏱️ TIME CRITICAL: " + condition.getTimeToImpact() + " seconds to decide\n\n" + condition.getEffectOnPlayer());

        // Load conditions animation
        conditionsAnimationView.setAnimation("warning.json");
        conditionsAnimationView.playAnimation();

        // Start countdown timer for urgency
        startConditionsCountdown(condition.getTimeToImpact(), condition);

        setupConditionsChoices(condition);
    }

    private void startConditionsCountdown(int seconds, DisasterCondition condition) {
        if (conditionsTimer != null) {
            conditionsTimer.cancel();
        }

        final DisasterCondition currentCondition = condition;

        conditionsTimer = new CountDownTimer(seconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remaining = (int) (millisUntilFinished / 1000) + 1;
                conditionsQuestionTextView.setText("⏱️ TIME CRITICAL: " + remaining + " seconds to decide\n\n" + currentCondition.getEffectOnPlayer());

                // Make text turn red as time runs out
                if (remaining <= 5) {
                    conditionsQuestionTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                } else {
                    conditionsQuestionTextView.setTextColor(getResources().getColor(android.R.color.black));
                }
            }

            @Override
            public void onFinish() {
                // Time's up - auto-select a random choice or apply penalty
                onConditionsChoice(false);
            }
        }.start();
    }

    private String buildEnvironmentalDescription(DisasterCondition condition) {
        StringBuilder description = new StringBuilder();
        description.append("SITUATION UPDATE:\n\n");
        description.append(condition.getDescription()).append("\n\n");

        // Add dramatic environmental details based on disaster type
        switch (scenario.getType()) {
            case EARTHQUAKE:
                description.append("ENVIRONMENTAL CHANGES:\n");
                description.append("• Building is groaning and shaking again\n");
                description.append("• Cracks are appearing in walls\n");
                description.append("• Ceiling tiles are falling\n");
                description.append("• Lights are flickering erratically\n");
                description.append("• Alarms are sounding throughout the building\n");
                if (condition.getSeverity().equals("CRITICAL")) {
                    description.append("• Structural failure imminent\n");
                }
                break;
            case FLOOD:
                description.append("ENVIRONMENTAL CHANGES:\n");
                description.append("• Water level has risen significantly\n");
                description.append("• Current is becoming much stronger\n");
                description.append("• Debris is floating past rapidly\n");
                description.append("• Electrical lines are sparking in water\n");
                description.append("• Evacuation route is partially submerged\n");
                if (condition.getSeverity().equals("CRITICAL")) {
                    description.append("• Water is entering the building rapidly\n");
                }
                break;
            case TYPHOON:
                description.append("ENVIRONMENTAL CHANGES:\n");
                description.append("• Wind speed has increased dramatically\n");
                description.append("• Visibility has dropped to near zero\n");
                description.append("• Large debris is flying through the air\n");
                description.append("• Buildings are showing structural damage\n");
                description.append("• Power lines are down and arcing\n");
                if (condition.getSeverity().equals("CRITICAL")) {
                    description.append("• Storm surge is approaching\n");
                }
                break;
            case FIRE:
                description.append("ENVIRONMENTAL CHANGES:\n");
                description.append("• Smoke has become much thicker\n");
                description.append("• Temperature is rising rapidly\n");
                description.append("• Flames are visible and spreading\n");
                description.append("• Emergency exit is now blocked\n");
                description.append("• Breathing is becoming difficult\n");
                if (condition.getSeverity().equals("CRITICAL")) {
                    description.append("• Flashover conditions developing\n");
                }
                break;
        }

        description.append("\n⚠️ SEVERITY: ").append(condition.getSeverity());
        return description.toString();
    }

    private DisasterCondition createChangingConditionBasedOnChoice() {
        String evacuationChoice = scenarioState.getPhaseDecisions().get("evacuation_choice");

        switch (scenario.getType()) {
            case EARTHQUAKE:
                if ("1".equals(evacuationChoice)) {
                    // Good choice - stairs
                    return new DisasterCondition("1", "STRONG AFTERSHOCK - Electricity goes out, exit becomes blocked by debris", "HIGH", 25,
                            "The stairs are shaking violently and the path ahead is blocked by fallen ceiling tiles", "Find alternate route or clear debris", true);
                } else if ("2".equals(evacuationChoice)) {
                    // Bad choice - elevator
                    return new DisasterCondition("1", "CRITICAL POWER FAILURE - Trapped in dark elevator, shaft collapsing", "CRITICAL", 10,
                            "Elevator is stuck between floors, lights are out, and you can hear the metal groaning under stress", "Use emergency phone and brace for impact", true);
                } else {
                    // Bad choice - wait inside
                    return new DisasterCondition("1", "STRUCTURAL COLLAPSE IMMINENT - Building groaning, cracks widening rapidly", "CRITICAL", 15,
                            "Large cracks appear in load-bearing walls, ceiling debris falling around you, exits may be compromised", "Emergency evacuation immediately - ignore aftershocks", true);
                }
            case FLOOD:
                if ("1".equals(evacuationChoice)) {
                    // Good choice - designated route
                    return new DisasterCondition("1", "SUDDEN WATER SURGE - Current becomes dangerous, route changes", "HIGH", 20,
                            "Water level has risen to chest height, current is strong enough to sweep you away, your original route is now flooded", "Move to highest point in immediate area", true);
                } else if ("2".equals(evacuationChoice)) {
                    // Bad choice - shortcuts
                    return new DisasterCondition("1", "DEADLY CURRENT - Swept away, electrical hazards in water", "CRITICAL", 10,
                            "Water is moving too fast to stand, electrical lines are arcing nearby, you're being carried toward dangerous debris", "Grab onto solid object immediately, avoid electrical contact", true);
                } else {
                    // Bad choice - wait
                    return new DisasterCondition("1", "FLASH FLOOD - Water enters building, evacuation route changes", "CRITICAL", 5,
                            "Water has burst through windows and doors, your safe location is flooding, all ground-level exits are blocked", "Move to roof or upper floors immediately", true);
                }
            case TYPHOON:
                if ("1".equals(evacuationChoice)) {
                    // Good choice - group travel
                    return new DisasterCondition("1", "EXTREME WIND GUSTS - Trees falling, visibility near zero", "HIGH", 20,
                            "Wind speed has increased to dangerous levels, large trees are falling across your path, you can barely see 10 feet ahead", "Find immediate shelter behind building, wait for gust to pass", true);
                } else if ("2".equals(evacuationChoice)) {
                    // Bad choice - alone
                    return new DisasterCondition("1", "DIRECT DEBRIS IMPACT - Alone in exposed area, multiple injuries", "CRITICAL", 15,
                            "You've been hit by flying debris multiple times, visibility is zero, wind is pushing you toward dangerous areas", "Seek shelter immediately, assess injuries when safe", true);
                } else {
                    // Bad choice - wait
                    return new DisasterCondition("1", "BUILDING FAILURE - Windows shattering, roof lifting off", "CRITICAL", 25,
                            "Windows are breaking outward from pressure, you can see daylight through roof damage, wind is entering the building", "Move to interior room away from windows immediately", true);
                }
            case FIRE:
                if ("1".equals(evacuationChoice)) {
                    // Good choice - stairs
                    return new DisasterCondition("1", "FLASHOVER DEVELOPING - Exit blocked, smoke extreme", "CRITICAL", 15,
                            "Fire has spread to block your primary exit, smoke is so thick you can't see your hand in front of your face, temperature is life-threatening", "Find alternate exit or use fire extinguisher to clear path", true);
                } else if ("2".equals(evacuationChoice)) {
                    // Bad choice - elevator
                    return new DisasterCondition("1", "SMOKE FILLED SHAFT - Trapped, air running out, panic setting in", "CRITICAL", 10,
                            "Elevator shaft is completely filled with thick black smoke, you're coughing and struggling to breathe, emergency button not responding", "Stay low, cover mouth with cloth, conserve oxygen", true);
                } else {
                    // Bad choice - wait
                    return new DisasterCondition("1", "FIRE SPREAD - Safe room no longer safe, emergency exit blocked", "CRITICAL", 12,
                            "Flames have entered through the door, smoke is filling the room rapidly, your only exit is now blocked by fire", "Escape through window if possible, break glass if needed", true);
                }
            default:
                return new DisasterCondition("1", "CATASTROPHIC CONDITIONS - Multiple systems failing", "CRITICAL", 30,
                        "Everything is going wrong simultaneously - power, communications, and structural integrity are compromised", "Follow emergency protocols, prioritize immediate survival", true);
        }
    }

    private void setupConditionsChoices(DisasterCondition condition) {
        // Create adaptive decision choices based on the specific condition
        switch (scenario.getType()) {
            case EARTHQUAKE:
                if (condition.getDescription().contains("AFTERSHOCK")) {
                    conditionsChoice1Button.setText("Hold onto railing and continue moving despite shaking");
                    conditionsChoice2Button.setText("Stop and wait for shaking to stop completely");
                } else if (condition.getDescription().contains("POWER FAILURE")) {
                    conditionsChoice1Button.setText("Use emergency phone and brace for impact");
                    conditionsChoice2Button.setText("Try to force elevator doors open");
                } else {
                    conditionsChoice1Button.setText("Emergency evacuation immediately - ignore aftershocks");
                    conditionsChoice2Button.setText("Seek shelter under desk and wait for help");
                }
                break;
            case FLOOD:
                if (condition.getDescription().contains("WATER SURGE")) {
                    conditionsChoice1Button.setText("Move to highest point in immediate area");
                    conditionsChoice2Button.setText("Try to swim through current to original route");
                } else if (condition.getDescription().contains("DEADLY CURRENT")) {
                    conditionsChoice1Button.setText("Grab onto solid object immediately, avoid electrical contact");
                    conditionsChoice2Button.setText("Try to swim against current to reach shore");
                } else {
                    conditionsChoice1Button.setText("Move to roof or upper floors immediately");
                    conditionsChoice2Button.setText("Try to exit through ground floor doors");
                }
                break;
            case TYPHOON:
                if (condition.getDescription().contains("EXTREME WIND")) {
                    conditionsChoice1Button.setText("Find immediate shelter behind building, wait for gust to pass");
                    conditionsChoice2Button.setText("Continue pushing forward through wind and debris");
                } else if (condition.getDescription().contains("DEBRIS IMPACT")) {
                    conditionsChoice1Button.setText("Seek shelter immediately, assess injuries when safe");
                    conditionsChoice2Button.setText("Continue moving despite injuries");
                } else {
                    conditionsChoice1Button.setText("Move to interior room away from windows immediately");
                    conditionsChoice2Button.setText("Try to secure windows and doors");
                }
                break;
            case FIRE:
                if (condition.getDescription().contains("FLASHOVER")) {
                    conditionsChoice1Button.setText("Find alternate exit or use fire extinguisher to clear path");
                    conditionsChoice2Button.setText("Try to run through the flames");
                } else if (condition.getDescription().contains("SMOKE FILLED")) {
                    conditionsChoice1Button.setText("Stay low, cover mouth with cloth, conserve oxygen");
                    conditionsChoice2Button.setText("Try to force elevator doors open");
                } else {
                    conditionsChoice1Button.setText("Escape through window if possible, break glass if needed");
                    conditionsChoice2Button.setText("Try to clear fire blocking the door");
                }
                break;
            default:
                conditionsChoice1Button.setText(condition.getRecommendedAction());
                conditionsChoice2Button.setText("Ignore and continue current plan");
        }

        conditionsChoice1Button.setOnClickListener(v -> onConditionsChoice(true));
        conditionsChoice2Button.setOnClickListener(v -> onConditionsChoice(false));
    }

    private void onConditionsChoice(boolean adapted) {
        scenarioState.addPhaseDecision("conditions_adapted", String.valueOf(adapted));

        // Evaluate both evacuation choice and conditions response together
        evaluateEvacuationAndConditions(adapted);

        scenarioState.setCurrentPhase(ScenarioPhase.CONSEQUENCES);
        showConsequencesScreen();
    }

    private void evaluateEvacuationAndConditions(boolean adaptedToConditions) {
        String evacuationChoice = scenarioState.getPhaseDecisions().get("evacuation_choice");
        boolean evacuationWasCorrect = "1".equals(evacuationChoice);

        if (evacuationWasCorrect && adaptedToConditions) {
            // Both decisions were good
            scenarioState.addPhaseDecision("evacuation_result", "correct");
            scenarioState.addPhaseDecision("conditions_result", "correct");
        } else if (evacuationWasCorrect && !adaptedToConditions) {
            // Good evacuation but poor adaptation
            scenarioState.addPhaseDecision("evacuation_result", "correct");
            scenarioState.addPhaseDecision("conditions_result", "wrong");
            scenarioState.decreaseHealth(15);
        } else if (!evacuationWasCorrect && adaptedToConditions) {
            // Poor evacuation but good adaptation
            scenarioState.addPhaseDecision("evacuation_result", "wrong");
            scenarioState.addPhaseDecision("conditions_result", "correct");
            scenarioState.decreaseHealth(15);
        } else {
            // Both decisions were poor
            scenarioState.addPhaseDecision("evacuation_result", "wrong");
            scenarioState.addPhaseDecision("conditions_result", "wrong");
            scenarioState.decreaseHealth(30);
        }
    }

    private void showConsequencesScreen() {
        hideAllScreens();
        consequencesScreen.setVisibility(View.VISIBLE);

        // Determine survival based on health status
        boolean survived = scenarioState.getHealthStatus() > 0;
        scenarioState.setSurvived(survived);

        // Build detailed consequences based on decisions
        String consequencesText = buildConsequencesText();
        consequencesDescriptionTextView.setText(consequencesText);

        // Load appropriate animation
        if (survived) {
            consequencesAnimationView.setAnimation("success.json");
        } else {
            consequencesAnimationView.setAnimation("failure.json");
        }
        consequencesAnimationView.playAnimation();
    }

    private String buildConsequencesText() {
        StringBuilder consequences = new StringBuilder();
        String evacuationChoice = scenarioState.getPhaseDecisions().get("evacuation_choice");
        String conditionsResult = scenarioState.getPhaseDecisions().get("conditions_result");

        consequences.append("DECISION CONSEQUENCES:\n\n");

        // Explain evacuation decision consequence
        switch (scenario.getType()) {
            case EARTHQUAKE:
                if ("1".equals(evacuationChoice)) {
                    consequences.append("✓ Taking the stairs: During the aftershock, you were able to hold onto the railing and maintain balance. Stairs are specifically designed to be safer during structural movement than elevators.\n\n");
                    consequences.append("WHY THIS MATTERED: Elevators can become death traps during earthquakes due to power failure, shaft collapse, or being stuck between floors. Your choice prioritized structural safety over speed.");
                } else if ("2".equals(evacuationChoice)) {
                    consequences.append("✗ Using the elevator: When power failed during the aftershock, you were trapped between floors in darkness. The elevator car was shaking violently, and you couldn't see if the shaft was collapsing.\n\n");
                    consequences.append("WHY THIS MATTERED: Elevators are specifically dangerous during earthquakes because they rely on power and can be affected by structural damage. Your choice prioritized speed over the fundamental safety principle of avoiding enclosed spaces during seismic events.");
                } else {
                    consequences.append("✗ Waiting inside: As aftershocks continued, the building structure showed signs of collapse with widening cracks and falling debris. You were forced to make a dangerous, hurried evacuation.\n\n");
                    consequences.append("WHY THIS MATTERED: Waiting during earthquakes can be fatal because buildings can collapse or become structurally compromised. Your choice prioritized perceived safety over the critical need to reach designated safe zones.");
                }
                break;
            case FLOOD:
                if ("1".equals(evacuationChoice)) {
                    consequences.append("✓ Following designated route: When water levels rose suddenly, the designated route remained passable because it was designed for flood conditions. You were able to continue to higher ground safely.\n\n");
                    consequences.append("WHY THIS MATTERED: Designated evacuation routes are specifically engineered to remain safe during flooding and are monitored by authorities. Your choice followed expert planning rather than improvising in dangerous conditions.");
                } else if ("2".equals(evacuationChoice)) {
                    consequences.append("✗ Taking shortcuts: The water current became too strong and swept you away. You encountered electrical hazards from downed power lines and required rescue.\n\n");
                    consequences.append("WHY THIS MATTERED: Flood currents are deceptive and can carry people much faster than they can swim. Shortcuts often have hidden dangers like submerged obstacles or electrical hazards. Your choice prioritized time savings over following established safety procedures.");
                } else {
                    consequences.append("✗ Waiting for rescue: Water rose faster than expected and reached your location. You were forced to move through increasingly dangerous conditions with no safe route planned.\n\n");
                    consequences.append("WHY THIS MATTERED: During flash floods, water levels can rise dramatically fast. Waiting for rescue assumes that help will arrive in time, which is not guaranteed. Your choice failed to account for the unpredictable speed of flood conditions.");
                }
                break;
            case TYPHOON:
                if ("1".equals(evacuationChoice)) {
                    consequences.append("✓ Traveling in a group: When flying debris blocked your path, you were able to work together to clear it and continue safely. Being in a group provided mutual protection and assistance.\n\n");
                    consequences.append("WHY THIS MATTERED: Group travel during typhoons provides safety in numbers and allows for collective problem-solving. Your choice recognized that individual vulnerability increases during extreme weather events.");
                } else if ("2".equals(evacuationChoice)) {
                    consequences.append("✗ Traveling alone: Caught in an exposed area, flying debris struck you directly. With no one to help, you sustained injuries and had no one to assist with your condition.\n\n");
                    consequences.append("WHY THIS MATTERED: Being alone during typhoons increases your vulnerability to flying debris and makes rescue harder if something goes wrong. Your choice prioritized speed over the safety of group protection and mutual assistance.");
                } else {
                    consequences.append("✗ Waiting inside: The building sustained severe damage with windows breaking and the roof beginning to lift. You were forced to evacuate in extremely dangerous conditions.\n\n");
                    consequences.append("WHY THIS MATTERED: Typhoon winds can cause catastrophic structural damage. Staying inside assumes the building will remain intact, which is not guaranteed during extreme wind events. Your choice failed to recognize the building's vulnerability.");
                }
                break;
            case FIRE:
                if ("1".equals(evacuationChoice)) {
                    consequences.append("✓ Using the stairs: When fire blocked the stairwell, you were able to adapt and find an alternate exit. The stairs provided a safe route that didn't fill with smoke like the elevator shaft.\n\n");
                    consequences.append("WHY THIS MATTERED: Stairs are designed as fire-safe routes, while elevator shafts act as chimneys for smoke and flames. Your choice followed the fundamental fire safety principle of avoiding vertical shafts during building fires.");
                } else if ("2".equals(evacuationChoice)) {
                    consequences.append("✗ Using the elevator: The elevator shaft filled with thick smoke quickly. You were trapped in a confined space with limited oxygen and couldn't see the buttons to call for help.\n\n");
                    consequences.append("WHY THIS MATTERED: Elevator shafts are natural chimneys for smoke and fire during building fires. Your choice prioritized speed over the basic safety principle of avoiding vertical shafts and using designated fire escape routes.");
                } else {
                    consequences.append("✗ Waiting in safe room: Fire spread to your room, filling it with smoke and extreme heat. You were forced to make a desperate escape attempt through a window.\n\n");
                    consequences.append("WHY THIS MATTERED: Fire spreads unpredictably and can bypass fire doors and walls. Waiting assumes your location will remain safe, which is not guaranteed. Your choice failed to account for the rapid and unpredictable nature of fire spread.");
                }
                break;
        }

        // Add consequences of conditions response
        consequences.append("\nADAPTIVE DECISION CONSEQUENCES:\n\n");
        if ("correct".equals(conditionsResult)) {
            consequences.append("✓ Your adaptive response to changing conditions was appropriate. You recognized that the situation had evolved and adjusted your actions accordingly. This flexibility is crucial in disaster survival.");
        } else {
            consequences.append("✗ Your response to changing conditions was inadequate. The situation had evolved, but you tried to continue with your original plan. This inflexibility significantly increased your risk.");
        }

        consequences.append("\n\nFINAL HEALTH STATUS: ").append(scenarioState.getHealthStatus()).append("%");

        return consequences.toString();
    }

    private void showFeedback() {
        hideAllScreens();
        feedbackScreen.setVisibility(View.VISIBLE);

        // Update feedback title to be more educational
        feedbackTitleTextView.setText("LEARNING OUTCOME");

        // Update survival status
        if (scenarioState.isSurvived()) {
            survivalStatusTextView.setText("SURVIVED");
            survivalStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            survivalStatusTextView.setText("DID NOT SURVIVE");
            survivalStatusTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        // Generate feedback based on decisions
        String feedback = generateFeedback();
        feedbackDescriptionTextView.setText(feedback);

        // Calculate rewards based on health status (not just survival)
        int healthPercentage = scenarioState.getHealthStatus();
        int xpEarned = (scenario.getXpReward() * healthPercentage) / 100;
        int coinsEarned = (scenario.getCoinReward() * healthPercentage) / 100;

        rewardsTextView.setText("XP Earned: " + xpEarned + "\nCoins Earned: " + coinsEarned);

        // Save results
        saveSimulationResult(xpEarned, coinsEarned);
    }

    private String generateFeedback() {
        StringBuilder feedback = new StringBuilder();
        feedback.append("DECISION FEEDBACK:\n\n");

        // Calculate individual scores
        int situationScore = calculateSituationScore();
        int suppliesScore = calculateSuppliesScore();
        int evacuationScore = calculateEvacuationScore();
        int adaptabilityScore = calculateAdaptabilityScore();
        int outcomeScore = calculateOutcomeScore();
        int totalScore = situationScore + suppliesScore + evacuationScore + adaptabilityScore + outcomeScore;

        // Situation Evaluation
        feedback.append("SITUATION EVALUATION — ").append(situationScore).append("/20 points\n");
        String situationResult = scenarioState.getPhaseDecisions().get("situation_result");
        if ("correct".equals(situationResult)) {
            feedback.append("✓ Good initial response\n");
        } else {
            feedback.append("✗ Poor initial response\n");
        }
        feedback.append("\n");

        // Emergency Supplies
        feedback.append("EMERGENCY SUPPLIES — ").append(suppliesScore).append("/20 points\n");
        int essentialCount = (int) selectedSupplies.stream().filter(EmergencySupply::isEssential).count();
        int totalEssential = (int) scenario.getAvailableSupplies().stream().filter(EmergencySupply::isEssential).count();
        feedback.append("Essential items: ").append(essentialCount).append("/").append(totalEssential).append("\n");
        feedback.append("\n");

        // Evacuation Decision
        feedback.append("EVACUATION DECISION — ").append(evacuationScore).append("/25 points\n");
        String evacuationResult = scenarioState.getPhaseDecisions().get("evacuation_result");
        if ("correct".equals(evacuationResult)) {
            feedback.append("✓ Smart evacuation choice\n");
        } else {
            feedback.append("✗ Poor evacuation choice\n");
        }
        feedback.append("\n");

        // Adaptability
        feedback.append("ADAPTABILITY — ").append(adaptabilityScore).append("/25 points\n");
        String conditionsResult = scenarioState.getPhaseDecisions().get("conditions_result");
        if ("correct".equals(conditionsResult)) {
            feedback.append("✓ Excellent adaptive response\n");
        } else {
            feedback.append("✗ Failed to adapt to conditions\n");
        }
        feedback.append("\n");

        // Final Outcome
        feedback.append("FINAL OUTCOME — ").append(outcomeScore).append("/10 points\n");
        feedback.append("Health Status: ").append(scenarioState.getHealthStatus()).append("%\n");
        feedback.append("\n");

        // Total Score
        feedback.append("TOTAL — ").append(totalScore).append("/100 points\n\n");

        feedback.append("─────────────────────────────\n\n");

        // What You Did Well
        feedback.append("WHAT YOU DID WELL:\n");
        if ("correct".equals(situationResult)) {
            feedback.append("• Correctly assessed the immediate threat\n");
        }
        if (essentialCount >= totalEssential - 1) {
            feedback.append("• Selected most essential supplies\n");
        }
        if ("correct".equals(evacuationResult)) {
            feedback.append("• Made smart evacuation decision\n");
        }
        if ("correct".equals(conditionsResult)) {
            feedback.append("• Successfully adapted to changing conditions\n");
        }
        if (scenarioState.getHealthStatus() > 70) {
            feedback.append("• Maintained good health throughout\n");
        }
        feedback.append("\n");

        // What You Can Improve
        feedback.append("WHAT YOU CAN IMPROVE:\n");
        if ("wrong".equals(situationResult)) {
            feedback.append("• Improve initial threat assessment\n");
        }
        if (essentialCount < totalEssential - 1) {
            feedback.append("• Select more essential emergency supplies\n");
        }
        if ("wrong".equals(evacuationResult)) {
            feedback.append("• Learn proper evacuation protocols\n");
        }
        if ("wrong".equals(conditionsResult)) {
            feedback.append("• Practice adaptive decision-making\n");
        }
        if (scenarioState.getHealthStatus() <= 70) {
            feedback.append("• Make decisions that minimize health risks\n");
        }
        feedback.append("\n");

        // Preparedness Tips
        feedback.append("PREPAREDNESS TIPS:\n");
        feedback.append("• In earthquakes, DROP, COVER, and HOLD ON\n");
        feedback.append("• Always follow designated evacuation routes\n");
        feedback.append("• Expect conditions to change during disasters\n");
        feedback.append("• Group travel provides safety in numbers\n");
        feedback.append("• Practice makes perfect - repeat scenarios\n\n");

        feedback.append("KEY TAKEAWAY: Adaptive decision-making is the most important disaster survival skill.");

        return feedback.toString();
    }

    private int calculateSituationScore() {
        String situationResult = scenarioState.getPhaseDecisions().get("situation_result");
        return "correct".equals(situationResult) ? 20 : 0;
    }

    private int calculateSuppliesScore() {
        int essentialCount = (int) selectedSupplies.stream().filter(EmergencySupply::isEssential).count();
        int totalEssential = (int) scenario.getAvailableSupplies().stream().filter(EmergencySupply::isEssential).count();
        if (essentialCount == totalEssential) return 20;
        if (essentialCount >= totalEssential - 1) return 15;
        if (essentialCount >= totalEssential - 2) return 10;
        return 5;
    }

    private int calculateEvacuationScore() {
        String evacuationResult = scenarioState.getPhaseDecisions().get("evacuation_result");
        return "correct".equals(evacuationResult) ? 25 : 10;
    }

    private int calculateAdaptabilityScore() {
        String conditionsResult = scenarioState.getPhaseDecisions().get("conditions_result");
        return "correct".equals(conditionsResult) ? 25 : 10;
    }

    private int calculateOutcomeScore() {
        int health = scenarioState.getHealthStatus();
        if (health >= 90) return 10;
        if (health >= 70) return 8;
        if (health >= 50) return 6;
        if (health >= 30) return 4;
        return 2;
    }

    private void saveSimulationResult(int xpEarned, int coinsEarned) {
        SimulationResult result = new SimulationResult();
        result.setUserId(com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid());
        result.setScenarioId(scenario.getId());
        result.setScenarioTitle(scenario.getTitle());
        result.setType(scenario.getType());
        result.setCompletedAt(new Date());
        result.setSurvived(scenarioState.isSurvived());
        result.setCorrectChoices(scenarioState.isSurvived() ? 4 : 2);
        result.setTotalChoices(4);
        result.setAccuracy(scenarioState.isSurvived() ? 100.0 : 50.0);
        result.setXpEarned(xpEarned);
        result.setCoinsEarned(coinsEarned);
        result.setStars(scenarioState.isSurvived() ? 3 : 1);

        // Detailed scoring breakdown
        result.setSituationEvaluationScore(calculateSituationScore());
        result.setSuppliesSelectionScore(calculateSuppliesScore());
        result.setEvacuationDecisionScore(calculateEvacuationScore());
        result.setAdaptabilityScore(calculateAdaptabilityScore());
        result.setFinalOutcomeScore(calculateOutcomeScore());
        result.setTotalScore(calculateSituationScore() + calculateSuppliesScore() + calculateEvacuationScore() + calculateAdaptabilityScore() + calculateOutcomeScore());

        // Time tracking
        long startTime = scenarioState.getStartTime();
        long endTime = System.currentTimeMillis();
        result.setTimeTakenSeconds((int) ((endTime - startTime) / 1000));

        simulationRepository.saveSimulationResult(result, new SimulationRepository.SaveCallback() {
            @Override
            public void onSuccess(String documentId) {
                // Update gamification
                updateGamification(xpEarned, coinsEarned, result.getTotalScore());
            }

            @Override
            public void onFailure(Exception e) {
                // Handle error
            }
        });
    }

    private void updateGamification(int xpEarned, int coinsEarned, int totalScore) {
        // Use existing GamificationManager to update XP and coins
        android.bignerdranch.learn2survive.domain.manager.GamificationManager gamificationManager =
                android.bignerdranch.learn2survive.domain.manager.GamificationManager.getInstance();

        // Update player data
        android.bignerdranch.learn2survive.domain.model.PlayerGamificationData playerData =
                gamificationManager.getPlayerData();

        if (playerData != null) {
            playerData.setCurrentXP(playerData.getCurrentXP() + xpEarned);
            playerData.setTotalXP(playerData.getTotalXP() + xpEarned);
            playerData.setCoins(playerData.getCoins() + coinsEarned);

            // Check for scenario achievements
            if (totalScore >= 90) {
                // High score achievement
            }
            if (scenarioState.isSurvived()) {
                // Survival achievement
            }
        }
    }

    private void returnToDashboard() {
        finish();
    }

    private void hideAllScreens() {
        warningScreen.setVisibility(View.GONE);
        situationScreen.setVisibility(View.GONE);
        suppliesScreen.setVisibility(View.GONE);
        evacuationScreen.setVisibility(View.GONE);
        conditionsScreen.setVisibility(View.GONE);
        consequencesScreen.setVisibility(View.GONE);
        feedbackScreen.setVisibility(View.GONE);
    }

    private void playSoundEffect(String soundUrl) {
        if (soundUrl != null && !soundUrl.isEmpty()) {
            try {
                if (soundPlayer != null) {
                    soundPlayer.release();
                }
                soundPlayer = new MediaPlayer();
                // For now, we'll use a simple implementation
                // In production, you'd load the actual sound file
            } catch (Exception e) {
                // Handle error
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
        if (conditionsTimer != null) {
            conditionsTimer.cancel();
        }
        if (soundPlayer != null) {
            soundPlayer.release();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
