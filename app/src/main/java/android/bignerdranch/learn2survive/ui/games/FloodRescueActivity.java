package android.bignerdranch.learn2survive.ui.games;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.Person;

public class FloodRescueActivity extends BaseGameActivity {
    private android.widget.GridLayout peopleGrid;
    private LinearLayout highGroundContainer;
    private TextView instructionTextView;
    private TextView waterLevelTextView;
    private TextView rescuedTextView;
    
    private List<Person> people;
    private List<Person> rescuedPeople;
    private int waterLevel;
    private int maxWaterLevel;
    private int peopleToRescue;
    private int waterRiseInterval;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_flood_rescue;
    }

    @Override
    protected void initGame() {
        gameType = GameType.FLOOD_RESCUE;
        difficulty = GameDifficulty.MEDIUM;
        
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        // Set difficulty parameters
        switch (difficulty) {
            case EASY:
                peopleToRescue = 5;
                maxWaterLevel = 100;
                waterRiseInterval = 2000;
                break;
            case MEDIUM:
                peopleToRescue = 8;
                maxWaterLevel = 100;
                waterRiseInterval = 1500;
                break;
            case HARD:
                peopleToRescue = 12;
                maxWaterLevel = 100;
                waterRiseInterval = 1000;
                break;
            case EXPERT:
                peopleToRescue = 15;
                maxWaterLevel = 100;
                waterRiseInterval = 800;
                break;
        }
        
        waterLevel = 0;
        rescuedPeople = new ArrayList<>();
        people = new ArrayList<>();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Flood Rescue");
        }
    }

    @Override
    protected void setupGameContent() {
        peopleGrid = findViewById(R.id.peopleGrid);
        highGroundContainer = findViewById(R.id.highGroundContainer);
        instructionTextView = findViewById(R.id.instructionTextView);
        waterLevelTextView = findViewById(R.id.waterLevelTextView);
        rescuedTextView = findViewById(R.id.rescuedTextView);
        
        generatePeople();
        displayPeople();
        setupDropZone();
        startWaterRise();
        updateUI();
    }
    
    private void generatePeople() {
        people.clear();
        
        String[] names = {"John", "Mary", "Baby", "Elderly", "Child", "Teen", "Adult", "Senior"};
        int[] icons = {
            android.R.drawable.ic_menu_mylocation,
            android.R.drawable.ic_menu_mylocation,
            android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_agenda,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_info_details
        };
        
        Random random = new Random();
        for (int i = 0; i < peopleToRescue; i++) {
            int nameIndex = random.nextInt(names.length);
            int iconIndex = random.nextInt(icons.length);
            people.add(new Person(names[nameIndex], icons[nameIndex], i));
        }
    }
    
    private void displayPeople() {
        peopleGrid.removeAllViews();
        
        for (Person person : people) {
            View personView = createPersonView(person);
            android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
            params.width = 0;
            params.height = android.widget.GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNSPECIFIED, 1f);
            params.setMargins(8, 8, 8, 8);
            personView.setLayoutParams(params);
            peopleGrid.addView(personView);
        }
    }
    
    private View createPersonView(Person person) {
        LinearLayout personLayout = new LinearLayout(this);
        personLayout.setOrientation(LinearLayout.VERTICAL);
        personLayout.setPadding(16, 16, 16, 16);
        personLayout.setBackgroundResource(R.drawable.option_background);
        
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(person.getIconResId());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
        
        TextView nameTextView = new TextView(this);
        nameTextView.setText(person.getName());
        nameTextView.setTextSize(12);
        nameTextView.setTextColor(getResources().getColor(android.R.color.white));
        nameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        
        personLayout.addView(imageView);
        personLayout.addView(nameTextView);
        
        // Set up drag
        personLayout.setTag(person);
        personLayout.setOnLongClickListener(v -> {
            android.content.ClipData.Item clipItem = new android.content.ClipData.Item(person.getName());
            android.content.ClipData dragData = new android.content.ClipData(person.getName(),
                    new String[]{android.content.ClipDescription.MIMETYPE_TEXT_PLAIN}, clipItem);
            v.startDrag(dragData, new View.DragShadowBuilder(v), null, 0);
            return true;
        });
        
        return personLayout;
    }
    
    private void setupDropZone() {
        highGroundContainer.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(android.content.ClipDescription.MIMETYPE_TEXT_PLAIN);
                    
                case DragEvent.ACTION_DRAG_ENTERED:
                    highGroundContainer.setBackgroundColor(getResources().getColor(R.color.success));
                    return true;
                    
                case DragEvent.ACTION_DRAG_EXITED:
                    highGroundContainer.setBackgroundColor(getResources().getColor(R.color.game_bottom_bar));
                    return true;
                    
                case DragEvent.ACTION_DROP:
                    Person person = findPersonByName(event.getClipData().getItemAt(0).getText().toString());
                    if (person != null) {
                        handleRescue(person);
                    }
                    highGroundContainer.setBackgroundColor(getResources().getColor(R.color.game_bottom_bar));
                    return true;
                    
                case DragEvent.ACTION_DRAG_ENDED:
                    highGroundContainer.setBackgroundColor(getResources().getColor(R.color.game_bottom_bar));
                    return true;
            }
            return false;
        });
    }
    
    private Person findPersonByName(String name) {
        for (Person person : people) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
    
    private void handleRescue(Person person) {
        if (rescuedPeople.contains(person)) {
            return;
        }
        
        rescuedPeople.add(person);
        people.remove(person);
        
        addScore(10);
        addCoins(1);
        addXP(5);
        showCorrectAnimation();
        
        displayPeople();
        updateUI();
        
        // Check win condition
        if (rescuedPeople.size() >= peopleToRescue) {
            endGame(false);
        }
    }
    
    private void startWaterRise() {
        android.os.Handler handler = new android.os.Handler();
        Runnable waterRiseRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused && !isGameOver) {
                    waterLevel += 5;
                    updateUI();
                    
                    if (waterLevel >= maxWaterLevel) {
                        endGame(true);
                    } else {
                        handler.postDelayed(this, waterRiseInterval);
                    }
                }
            }
        };
        handler.postDelayed(waterRiseRunnable, waterRiseInterval);
    }
    
    private void updateUI() {
        waterLevelTextView.setText("Water Level: " + waterLevel + "%");
        rescuedTextView.setText("Rescued: " + rescuedPeople.size() + "/" + peopleToRescue);
        
        // Update progress bar
        int progress = (int) ((double) rescuedPeople.size() / peopleToRescue * 100);
        progressBar.setProgress(progress);
        
        // Change water level color based on danger
        if (waterLevel > 70) {
            waterLevelTextView.setTextColor(getResources().getColor(R.color.error));
        } else if (waterLevel > 50) {
            waterLevelTextView.setTextColor(getResources().getColor(R.color.warning));
        } else {
            waterLevelTextView.setTextColor(getResources().getColor(android.R.color.white));
        }
    }
    
    @Override
    protected void updateGameState() {
        updateUI();
    }
    
    @Override
    protected void onGameComplete() {
        // Game completion handled in endGame
    }
    
    @Override
    protected int getMaxPossibleScore() {
        return peopleToRescue * 10;
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Bonus for saving people before water gets too high
        if (waterLevel < 50) {
            coins += 20;
            xp += 30;
        } else if (waterLevel < 70) {
            coins += 10;
            xp += 15;
        }
    }
}