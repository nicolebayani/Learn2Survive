package android.bignerdranch.learn2survive.ui.games;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.MemoryCard;

public class MemoryMatchActivity extends BaseGameActivity {
    private android.widget.GridLayout gridLayout;
    private TextView instructionTextView;
    private TextView matchesTextView;
    
    private List<MemoryCard> cards;
    private List<ImageView> cardViews;
    private MemoryCard firstFlippedCard;
    private MemoryCard secondFlippedCard;
    private boolean isProcessing;
    private int matchesFound;
    private int totalPairs;
    private int moves;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_memory_match;
    }
    
    @Override
    protected void initGame() {
        gameType = GameType.MEMORY_MATCH;
        difficulty = GameDifficulty.MEDIUM;
        
        // Get difficulty from intent if provided
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        cards = new ArrayList<>();
        cardViews = new ArrayList<>();
        firstFlippedCard = null;
        secondFlippedCard = null;
        isProcessing = false;
        matchesFound = 0;
        moves = 0;
        
        // Set grid size based on difficulty
        switch (difficulty) {
            case EASY:
                totalPairs = 4;
                break;
            case MEDIUM:
                totalPairs = 6;
                break;
            case HARD:
                totalPairs = 8;
                break;
            case EXPERT:
                totalPairs = 12;
                break;
        }
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Memory Match");
        }
    }
    
    @Override
    protected void setupGameContent() {
        gridLayout = findViewById(R.id.gridLayout);
        instructionTextView = findViewById(R.id.instructionTextView);
        matchesTextView = findViewById(R.id.matchesTextView);
        
        setupGrid();
        generateCards();
        displayCards();
        updateUI();
    }
    
    private void setupGrid() {
        int columns;
        switch (difficulty) {
            case EASY:
                columns = 2;
                break;
            case MEDIUM:
                columns = 3;
                break;
            case HARD:
                columns = 4;
                break;
            case EXPERT:
                columns = 4;
                break;
            default:
                columns = 3;
        }
        
        gridLayout.setColumnCount(columns);
    }
    
    private void generateCards() {
        cards.clear();
        
        // Define emergency item pairs
        int[][] itemPairs = {
            {R.drawable.ic_water, R.drawable.ic_water},
            {R.drawable.ic_food, R.drawable.ic_food},
            {R.drawable.ic_flashlight, R.drawable.ic_flashlight},
            {R.drawable.ic_first_aid, R.drawable.ic_first_aid},
            {R.drawable.ic_radio, R.drawable.ic_radio},
            {R.drawable.ic_whistle, R.drawable.ic_whistle},
            {R.drawable.ic_mask, R.drawable.ic_mask},
            {R.drawable.ic_can_opener, R.drawable.ic_can_opener},
            {android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_gallery},
            {android.R.drawable.ic_menu_mapmode, android.R.drawable.ic_menu_mapmode},
            {android.R.drawable.ic_menu_call, android.R.drawable.ic_menu_call},
            {android.R.drawable.ic_menu_agenda, android.R.drawable.ic_menu_agenda}
        };
        
        // Add pairs based on difficulty
        for (int i = 0; i < totalPairs && i < itemPairs.length; i++) {
            int iconRes = itemPairs[i][0];
            cards.add(new MemoryCard(iconRes, i));
            cards.add(new MemoryCard(iconRes, i));
        }
        
        // Shuffle cards
        Collections.shuffle(cards);
    }
    
    private void displayCards() {
        gridLayout.removeAllViews();
        cardViews.clear();
        
        for (int i = 0; i < cards.size(); i++) {
            MemoryCard card = cards.get(i);
            ImageView cardView = createCardView(card, i);
            cardViews.add(cardView);
            gridLayout.addView(cardView);
        }
    }
    
    private ImageView createCardView(MemoryCard card, int position) {
        ImageView cardView = new ImageView(this);
        
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNSPECIFIED, 1f);
        params.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(params);
        
        cardView.setAdjustViewBounds(true);
        cardView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        cardView.setMinimumHeight(120);
        
        // Set card back (hidden state)
        cardView.setImageResource(android.R.drawable.ic_menu_help);
        cardView.setColorFilter(getResources().getColor(R.color.primary));
        
        cardView.setTag(position);
        cardView.setOnClickListener(v -> onCardClick(position));
        
        return cardView;
    }
    
    private void onCardClick(int position) {
        if (isProcessing) return;
        
        MemoryCard clickedCard = cards.get(position);
        ImageView clickedView = cardViews.get(position);
        
        if (clickedCard.isFlipped() || clickedCard.isMatched()) return;
        
        // Flip the card
        flipCard(clickedCard, clickedView);
        
        if (firstFlippedCard == null) {
            firstFlippedCard = clickedCard;
        } else {
            secondFlippedCard = clickedCard;
            moves++;
            checkForMatch();
        }
    }
    
    private void flipCard(MemoryCard card, ImageView cardView) {
        card.setFlipped(true);
        
        ScaleAnimation flipAnimation = new ScaleAnimation(
            1f, 0f, 1f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        flipAnimation.setDuration(200);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            
            @Override
            public void onAnimationEnd(Animation animation) {
                // Show card face
                cardView.setImageResource(card.getIconResId());
                cardView.clearColorFilter();
                
                ScaleAnimation flipBack = new ScaleAnimation(
                    0f, 1f, 1f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                );
                flipBack.setDuration(200);
                cardView.startAnimation(flipBack);
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        
        cardView.startAnimation(flipAnimation);
    }
    
    private void checkForMatch() {
        isProcessing = true;
        
        if (firstFlippedCard.getPairId() == secondFlippedCard.getPairId()) {
            // Match found
            handleMatch();
        } else {
            // No match
            handleMismatch();
        }
    }
    
    private void handleMatch() {
        firstFlippedCard.setMatched(true);
        secondFlippedCard.setMatched(true);
        matchesFound++;
        
        addScore(10);
        addCoins(1);
        addXP(5);
        showCorrectAnimation();
        
        // Reset for next turn
        firstFlippedCard = null;
        secondFlippedCard = null;
        isProcessing = false;
        
        updateUI();
        
        // Check win condition
        if (matchesFound == totalPairs) {
            endGame(false);
        }
    }
    
    private void handleMismatch() {
        // Brief pause before flipping back
        cardViews.get(cards.indexOf(firstFlippedCard)).postDelayed(() -> {
            flipBackCard(firstFlippedCard, cardViews.get(cards.indexOf(firstFlippedCard)));
            flipBackCard(secondFlippedCard, cardViews.get(cards.indexOf(secondFlippedCard)));
            
            firstFlippedCard.setFlipped(false);
            secondFlippedCard.setFlipped(false);
            
            firstFlippedCard = null;
            secondFlippedCard = null;
            isProcessing = false;
            
            showWrongAnimation();
        }, 1000);
    }
    
    private void flipBackCard(MemoryCard card, ImageView cardView) {
        ScaleAnimation flipAnimation = new ScaleAnimation(
            1f, 0f, 1f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        );
        flipAnimation.setDuration(200);
        flipAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            
            @Override
            public void onAnimationEnd(Animation animation) {
                // Show card back
                cardView.setImageResource(android.R.drawable.ic_menu_help);
                cardView.setColorFilter(getResources().getColor(R.color.primary));
                
                ScaleAnimation flipBack = new ScaleAnimation(
                    0f, 1f, 1f, 1f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
                );
                flipBack.setDuration(200);
                cardView.startAnimation(flipBack);
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        
        cardView.startAnimation(flipAnimation);
    }
    
    private void updateUI() {
        matchesTextView.setText(String.format("Matches: %d/%d", matchesFound, totalPairs));
        
        // Update progress bar
        int progress = (int) ((double) matchesFound / totalPairs * 100);
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
        return totalPairs * 10; // 10 points per match
    }
    
    @Override
    protected void calculateFinalRewards() {
        super.calculateFinalRewards();
        
        // Bonus for efficiency (fewer moves)
        int efficiencyBonus = Math.max(0, (totalPairs * 2 - moves) * 2);
        coins += efficiencyBonus;
        xp += efficiencyBonus;
    }
}