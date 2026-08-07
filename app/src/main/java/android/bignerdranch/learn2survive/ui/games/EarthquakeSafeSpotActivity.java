package android.bignerdranch.learn2survive.ui.games;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.SafeSpot;

public class EarthquakeSafeSpotActivity extends BaseGameActivity {
    private GridLayout spotsGrid;
    private TextView instructionTextView;
    private TextView spotsFoundTextView;
    
    private List<SafeSpot> spots;
    private int totalSafeSpots;
    private int foundSafeSpots;
    private int gridRows;
    private int gridCols;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_earthquake_safe_spot;
    }

    @Override
    protected void initGame() {
        gameType = GameType.EARTHQUAKE_SAFE_SPOT;
        difficulty = GameDifficulty.MEDIUM;
        
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        // Set grid size and safe spots based on difficulty
        switch (difficulty) {
            case EASY:
                gridRows = 3;
                gridCols = 3;
                totalSafeSpots = 3;
                break;
            case MEDIUM:
                gridRows = 4;
                gridCols = 4;
                totalSafeSpots = 5;
                break;
            case HARD:
                gridRows = 5;
                gridCols = 5;
                totalSafeSpots = 8;
                break;
            case EXPERT:
                gridRows = 6;
                gridCols = 6;
                totalSafeSpots = 12;
                break;
        }
        
        foundSafeSpots = 0;
        spots = new ArrayList<>();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Earthquake Safe Spot");
        }
    }

    @Override
    protected void setupGameContent() {
        spotsGrid = findViewById(R.id.spotsGrid);
        instructionTextView = findViewById(R.id.instructionTextView);
        spotsFoundTextView = findViewById(R.id.spotsFoundTextView);
        
        setupGrid();
        generateSpots();
        displaySpots();
        updateUI();
    }
    
    private void setupGrid() {
        spotsGrid.setRowCount(gridRows);
        spotsGrid.setColumnCount(gridCols);
    }
    
    private void generateSpots() {
        spots.clear();
        
        // Define spot types
        String[] safeTypes = {"Under sturdy table", "Door frame", "Corner room", "Away from windows"};
        String[] dangerTypes = {"Near windows", "Under heavy objects", "Near exterior walls", "Near glass"};
        
        int[] safeIcons = {
            android.R.drawable.ic_menu_add,
            android.R.drawable.ic_menu_agenda,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass
        };
        
        int[] dangerIcons = {
            android.R.drawable.ic_menu_close_clear_cancel,
            android.R.drawable.ic_menu_delete,
            android.R.drawable.ic_menu_gallery,
            android.R.drawable.ic_menu_info_details
        };
        
        Random random = new Random();
        int totalCells = gridRows * gridCols;
        
        // Place safe spots
        for (int i = 0; i < totalSafeSpots; i++) {
            int row = random.nextInt(gridRows);
            int col = random.nextInt(gridCols);
            int typeIndex = random.nextInt(safeTypes.length);
            
            SafeSpot spot = new SafeSpot(row, col, safeTypes[typeIndex], safeIcons[typeIndex], true);
            spots.add(spot);
        }
        
        // Fill remaining cells with danger spots
        while (spots.size() < totalCells) {
            int row = random.nextInt(gridRows);
            int col = random.nextInt(gridCols);
            
            // Check if spot already exists
            boolean exists = false;
            for (SafeSpot spot : spots) {
                if (spot.getRow() == row && spot.getCol() == col) {
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                int typeIndex = random.nextInt(dangerTypes.length);
                SafeSpot spot = new SafeSpot(row, col, dangerTypes[typeIndex], dangerIcons[typeIndex], false);
                spots.add(spot);
            }
        }
        
        // Shuffle spots
        java.util.Collections.shuffle(spots);
    }
    
    private void displaySpots() {
        spotsGrid.removeAllViews();
        
        for (SafeSpot spot : spots) {
            View spotView = createSpotView(spot);
            spotsGrid.addView(spotView);
        }
    }
    
    private View createSpotView(SafeSpot spot) {
        ImageView spotView = new ImageView(this);
        
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNSPECIFIED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNSPECIFIED, 1f);
        params.setMargins(4, 4, 4, 4);
        spotView.setLayoutParams(params);
        
        spotView.setAdjustViewBounds(true);
        spotView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        spotView.setMinimumHeight(100);
        
        // Show question mark initially
        spotView.setImageResource(android.R.drawable.ic_menu_help);
        spotView.setColorFilter(getResources().getColor(R.color.primary));
        
        spotView.setTag(spot);
        spotView.setOnClickListener(v -> onSpotClick(spot, spotView));
        
        return spotView;
    }
    
    private void onSpotClick(SafeSpot spot, ImageView spotView) {
        if (spot.isRevealed()) {
            return;
        }
        
        spot.setRevealed(true);
        
        if (spot.isSafe()) {
            // Found safe spot
            foundSafeSpots++;
            addScore(15);
            addCoins(2);
            addXP(10);
            showCorrectAnimation();
            
            spotView.setImageResource(spot.getIconResId());
            spotView.setColorFilter(getResources().getColor(R.color.success));
            
            android.widget.Toast.makeText(this, "Safe! " + spot.getType(), android.widget.Toast.LENGTH_SHORT).show();
        } else {
            // Found danger spot
            addScore(-5);
            showWrongAnimation();
            
            spotView.setImageResource(spot.getIconResId());
            spotView.setColorFilter(getResources().getColor(R.color.error));
            
            android.widget.Toast.makeText(this, "Danger! " + spot.getType(), android.widget.Toast.LENGTH_SHORT).show();
        }
        
        updateUI();
        
        // Check win condition
        if (foundSafeSpots >= totalSafeSpots) {
            endGame(false);
        }
    }
    
    private void updateUI() {
        spotsFoundTextView.setText("Safe Spots: " + foundSafeSpots + "/" + totalSafeSpots);
        
        // Update progress bar
        int progress = (int) ((double) foundSafeSpots / totalSafeSpots * 100);
        progressBar.setProgress(progress);
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
        return totalSafeSpots * 15; // 15 points per safe spot
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Bonus for finding all safe spots quickly
        if (timeRemaining > totalTime * 0.5) {
            coins += 20;
            xp += 30;
        } else if (timeRemaining > totalTime * 0.25) {
            coins += 10;
            xp += 15;
        }
    }
}