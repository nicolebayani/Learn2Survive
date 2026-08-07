package android.bignerdranch.learn2survive.ui.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;

public class EvacuationMazeActivity extends BaseGameActivity {
    private GridLayout mazeGrid;
    private TextView instructionTextView;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    
    private int[][] maze;
    private int playerX;
    private int playerY;
    private int exitX;
    private int exitY;
    private int mazeSize;
    private int steps;
    private List<int[]> obstacles;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_evacuation_maze;
    }

    @Override
    protected void initGame() {
        gameType = GameType.EVACUATION_MAZE;
        difficulty = GameDifficulty.MEDIUM;
        
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        // Set maze size based on difficulty
        switch (difficulty) {
            case EASY:
                mazeSize = 5;
                break;
            case MEDIUM:
                mazeSize = 7;
                break;
            case HARD:
                mazeSize = 9;
                break;
            case EXPERT:
                mazeSize = 11;
                break;
        }
        
        steps = 0;
        obstacles = new ArrayList<>();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Evacuation Maze");
        }
    }

    @Override
    protected void setupGameContent() {
        mazeGrid = findViewById(R.id.mazeGrid);
        instructionTextView = findViewById(R.id.instructionTextView);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        
        instructionTextView.setText("Navigate through the maze to reach the evacuation point!");
        
        setupMazeGrid();
        generateMaze();
        displayMaze();
        setupControls();
    }
    
    private void setupMazeGrid() {
        mazeGrid.setColumnCount(mazeSize);
        mazeGrid.setRowCount(mazeSize);
    }
    
    private void generateMaze() {
        maze = new int[mazeSize][mazeSize];
        
        // Initialize maze with empty spaces
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                maze[i][j] = 0; // 0 = empty
            }
        }
        
        // Set player start position (top-left)
        playerX = 0;
        playerY = 0;
        maze[playerY][playerX] = 1; // 1 = player
        
        // Set exit position (bottom-right)
        exitX = mazeSize - 1;
        exitY = mazeSize - 1;
        maze[exitY][exitX] = 2; // 2 = exit
        
        // Generate obstacles based on difficulty
        int obstacleCount;
        switch (difficulty) {
            case EASY:
                obstacleCount = mazeSize * 2;
                break;
            case MEDIUM:
                obstacleCount = mazeSize * 3;
                break;
            case HARD:
                obstacleCount = mazeSize * 4;
                break;
            case EXPERT:
                obstacleCount = mazeSize * 5;
                break;
            default:
                obstacleCount = mazeSize * 3;
        }
        
        Random random = new Random();
        while (obstacles.size() < obstacleCount) {
            int x = random.nextInt(mazeSize);
            int y = random.nextInt(mazeSize);
            
            // Don't place obstacles on player or exit
            if ((x == playerX && y == playerY) || (x == exitX && y == exitY)) {
                continue;
            }
            
            // Don't place obstacle if already exists
            boolean exists = false;
            for (int[] obs : obstacles) {
                if (obs[0] == x && obs[1] == y) {
                    exists = true;
                    break;
                }
            }
            
            if (!exists) {
                maze[y][x] = 3; // 3 = obstacle
                obstacles.add(new int[]{x, y});
            }
        }
    }
    
    private void displayMaze() {
        mazeGrid.removeAllViews();
        
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                ImageView cellView = createCellView(maze[y][x], x, y);
                mazeGrid.addView(cellView);
            }
        }
    }
    
    private ImageView createCellView(int cellType, int x, int y) {
        ImageView cellView = new ImageView(this);
        
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNSPECIFIED, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNSPECIFIED, 1f);
        params.setMargins(2, 2, 2, 2);
        cellView.setLayoutParams(params);
        
        cellView.setAdjustViewBounds(true);
        cellView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cellView.setMinimumHeight(80);
        
        switch (cellType) {
            case 0: // Empty
                cellView.setImageResource(android.R.drawable.ic_menu_add);
                cellView.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                break;
            case 1: // Player
                cellView.setImageResource(android.R.drawable.ic_menu_mylocation);
                cellView.setColorFilter(getResources().getColor(R.color.primary));
                break;
            case 2: // Exit
                cellView.setImageResource(android.R.drawable.ic_menu_directions);
                cellView.setColorFilter(getResources().getColor(R.color.success));
                break;
            case 3: // Obstacle
                cellView.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                cellView.setColorFilter(getResources().getColor(R.color.error));
                break;
        }
        
        return cellView;
    }
    
    private void setupControls() {
        upButton.setOnClickListener(v -> movePlayer(0, -1));
        downButton.setOnClickListener(v -> movePlayer(0, 1));
        leftButton.setOnClickListener(v -> movePlayer(-1, 0));
        rightButton.setOnClickListener(v -> movePlayer(1, 0));
    }
    
    private void movePlayer(int dx, int dy) {
        if (isGameOver) return;
        
        int newX = playerX + dx;
        int newY = playerY + dy;
        
        // Check bounds
        if (newX < 0 || newX >= mazeSize || newY < 0 || newY >= mazeSize) {
            return;
        }
        
        // Check obstacle
        if (maze[newY][newX] == 3) {
            showWrongAnimation();
            addScore(-5); // Penalty for hitting obstacle
            return;
        }
        
        // Update player position
        maze[playerY][playerX] = 0; // Clear old position
        playerX = newX;
        playerY = newY;
        
        // Check if reached exit
        if (playerX == exitX && playerY == exitY) {
            maze[playerY][playerX] = 1;
            displayMaze();
            addScore(50);
            showCorrectAnimation();
            endGame(false);
            return;
        }
        
        maze[playerY][playerX] = 1;
        steps++;
        
        // Add score for each step (efficiency bonus)
        addScore(1);
        
        displayMaze();
        updateUI();
    }
    
    @Override
    protected void updateGameState() {
        // Update step counter if needed
    }
    
    @Override
    protected void onGameComplete() {
        // Game completion handled in endGame
    }
    
    @Override
    protected int getMaxPossibleScore() {
        return 1000;
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Efficiency bonus: fewer steps = more rewards
        int maxSteps = mazeSize * mazeSize;
        int efficiencyBonus = Math.max(0, (maxSteps - steps) * 2);
        coins += efficiencyBonus;
        xp += efficiencyBonus;
    }
}