package android.bignerdranch.learn2survive.ui.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.HouseTask;

public class TyphoonHouseDefenderActivity extends BaseGameActivity {
    private android.widget.GridLayout tasksGrid;
    private LinearLayout securedItemsContainer;
    private TextView instructionTextView;
    private TextView stormTimerTextView;
    private TextView securedTextView;
    
    private List<HouseTask> availableTasks;
    private List<HouseTask> securedTasks;
    private int totalTasks;
    private int stormCountdown;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_typhoon_house_defender;
    }

    @Override
    protected void initGame() {
        gameType = GameType.TYPHOON_HOUSE_DEFENDER;
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
                totalTasks = 5;
                stormCountdown = 60;
                break;
            case MEDIUM:
                totalTasks = 8;
                stormCountdown = 45;
                break;
            case HARD:
                totalTasks = 12;
                stormCountdown = 30;
                break;
            case EXPERT:
                totalTasks = 15;
                stormCountdown = 20;
                break;
        }
        
        availableTasks = new ArrayList<>();
        securedTasks = new ArrayList<>();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Typhoon House Defender");
        }
    }

    @Override
    protected void setupGameContent() {
        tasksGrid = findViewById(R.id.tasksGrid);
        securedItemsContainer = findViewById(R.id.securedItemsContainer);
        instructionTextView = findViewById(R.id.instructionTextView);
        stormTimerTextView = findViewById(R.id.stormTimerTextView);
        securedTextView = findViewById(R.id.securedTextView);
        
        generateTasks();
        displayTasks();
        startStormCountdown();
        updateUI();
    }
    
    private void generateTasks() {
        availableTasks.clear();
        
        availableTasks.add(new HouseTask("Board Windows", "Protect windows from flying debris", R.drawable.ic_box, 15));
        availableTasks.add(new HouseTask("Secure Doors", "Reinforce entry doors", R.drawable.ic_box, 15));
        availableTasks.add(new HouseTask("Clear Gutters", "Prevent water backup", R.drawable.ic_box, 10));
        availableTasks.add(new HouseTask("Trim Trees", "Remove weak branches", R.drawable.ic_box, 10));
        availableTasks.add(new HouseTask("Store Outdoor Items", "Secure loose objects", R.drawable.ic_box, 10));
        availableTasks.add(new HouseTask("Check Roof", "Repair loose shingles", R.drawable.ic_box, 15));
        availableTasks.add(new HouseTask("Seal Cracks", "Prevent water entry", R.drawable.ic_box, 10));
        availableTasks.add(new HouseTask("Backup Generator", "Ensure power backup", R.drawable.ic_radio, 15));
        availableTasks.add(new HouseTask("Stock Water", "Store emergency water", R.drawable.ic_water, 10));
        availableTasks.add(new HouseTask("Emergency Kit", "Prepare survival kit", R.drawable.ic_first_aid, 15));
        availableTasks.add(new HouseTask("Charge Devices", "Power up communication", R.drawable.ic_flashlight, 10));
        availableTasks.add(new HouseTask("Identify Safe Room", "Know your shelter area", R.drawable.ic_earthquake, 10));
        availableTasks.add(new HouseTask("Secure Vehicles", "Park in safe area", android.R.drawable.ic_menu_directions, 10));
        availableTasks.add(new HouseTask("Document Important Items", "Insurance and records", android.R.drawable.ic_menu_agenda, 5));
        availableTasks.add(new HouseTask("Emergency Contacts", "Update contact list", android.R.drawable.ic_menu_call, 5));
        
        // Limit tasks based on difficulty
        while (availableTasks.size() > totalTasks) {
            availableTasks.remove(availableTasks.size() - 1);
        }
        
        // Shuffle tasks
        java.util.Collections.shuffle(availableTasks);
    }
    
    private void displayTasks() {
        tasksGrid.removeAllViews();
        
        for (HouseTask task : availableTasks) {
            View taskView = createTaskView(task);
            android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
            params.width = 0;
            params.height = android.widget.GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNSPECIFIED, 1f);
            params.setMargins(8, 8, 8, 8);
            taskView.setLayoutParams(params);
            tasksGrid.addView(taskView);
        }
    }
    
    private View createTaskView(HouseTask task) {
        LinearLayout taskLayout = new LinearLayout(this);
        taskLayout.setOrientation(LinearLayout.VERTICAL);
        taskLayout.setPadding(12, 12, 12, 12);
        taskLayout.setBackgroundResource(R.drawable.option_background);
        
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(task.getIconResId());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        
        TextView nameTextView = new TextView(this);
        nameTextView.setText(task.getName());
        nameTextView.setTextSize(12);
        nameTextView.setTextColor(getResources().getColor(android.R.color.white));
        nameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        
        taskLayout.addView(imageView);
        taskLayout.addView(nameTextView);
        
        taskLayout.setOnClickListener(v -> completeTask(task));
        
        return taskLayout;
    }
    
    private void completeTask(HouseTask task) {
        if (securedTasks.contains(task)) {
            return;
        }
        
        securedTasks.add(task);
        availableTasks.remove(task);
        
        addScore(task.getPoints());
        addCoins(1);
        addXP(5);
        showCorrectAnimation();
        
        android.widget.Toast.makeText(this, "Completed: " + task.getName(), android.widget.Toast.LENGTH_SHORT).show();
        
        displayTasks();
        updateUI();
        
        // Check win condition
        if (securedTasks.size() >= totalTasks) {
            endGame(false);
        }
    }
    
    private void startStormCountdown() {
        final android.os.Handler handler = new android.os.Handler();
        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                if (!isPaused && !isGameOver) {
                    stormCountdown--;
                    updateUI();
                    
                    if (stormCountdown <= 0) {
                        endGame(true);
                    } else {
                        handler.postDelayed(this, 1000);
                    }
                }
            }
        };
        handler.postDelayed(countdownRunnable, 1000);
    }
    
    private void updateUI() {
        stormTimerTextView.setText("Storm in: " + stormCountdown + "s");
        securedTextView.setText("Secured: " + securedTasks.size() + "/" + totalTasks);
        
        // Update progress bar
        int progress = (int) ((double) securedTasks.size() / totalTasks * 100);
        progressBar.setProgress(progress);
        
        // Change storm timer color based on urgency
        if (stormCountdown <= 10) {
            stormTimerTextView.setTextColor(getResources().getColor(R.color.error));
        } else if (stormCountdown <= 20) {
            stormTimerTextView.setTextColor(getResources().getColor(R.color.warning));
        } else {
            stormTimerTextView.setTextColor(getResources().getColor(android.R.color.white));
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
        return totalTasks * 15; // Max 15 points per task
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Bonus for completing before storm
        if (stormCountdown > 20) {
            coins += 30;
            xp += 50;
        } else if (stormCountdown > 10) {
            coins += 15;
            xp += 25;
        }
    }
}