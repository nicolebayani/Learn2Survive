package android.bignerdranch.learn2survive.ui.games;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameDifficulty;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.models.EmergencyItem;

public class EmergencyKitBuilderActivity extends BaseGameActivity {
    private android.widget.GridLayout itemsContainer;
    private LinearLayout backpackContainer;
    private TextView instructionTextView;
    private TextView itemsCollectedTextView;
    
    private List<EmergencyItem> availableItems;
    private List<EmergencyItem> collectedItems;
    private int totalItemsToCollect;
    private int maxItems;
    
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_emergency_kit_builder;
    }
    
    @Override
    protected void initGame() {
        gameType = GameType.EMERGENCY_KIT_BUILDER;
        difficulty = GameDifficulty.MEDIUM; // Default difficulty
        
        // Get difficulty from intent if provided
        String difficultyStr = getIntent().getStringExtra("difficulty");
        if (difficultyStr != null) {
            try {
                difficulty = GameDifficulty.valueOf(difficultyStr);
            } catch (Exception e) {
                difficulty = GameDifficulty.MEDIUM;
            }
        }
        
        availableItems = new ArrayList<>();
        collectedItems = new ArrayList<>();
        
        // Set difficulty parameters
        switch (difficulty) {
            case EASY:
                maxItems = 5;
                break;
            case MEDIUM:
                maxItems = 8;
                break;
            case HARD:
                maxItems = 12;
                break;
            case EXPERT:
                maxItems = 15;
                break;
        }
        
        totalItemsToCollect = maxItems;
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Emergency Kit Builder");
        }
    }
    
    @Override
    protected void setupGameContent() {
        itemsContainer = findViewById(R.id.itemsContainer);
        backpackContainer = findViewById(R.id.backpackContainer);
        instructionTextView = findViewById(R.id.instructionTextView);
        itemsCollectedTextView = findViewById(R.id.itemsCollectedTextView);
        
        generateEmergencyItems();
        displayAvailableItems();
        updateInstruction();
        updateItemsCollected();
    }
    
    private void generateEmergencyItems() {
        availableItems.clear();
        
        // Essential items
        availableItems.add(new EmergencyItem("Water", "3-day supply", R.drawable.ic_water, true, 10));
        availableItems.add(new EmergencyItem("Food", "Non-perishable", R.drawable.ic_food, true, 10));
        availableItems.add(new EmergencyItem("Flashlight", "With batteries", R.drawable.ic_flashlight, true, 10));
        availableItems.add(new EmergencyItem("First Aid Kit", "Medical supplies", R.drawable.ic_first_aid, true, 15));
        availableItems.add(new EmergencyItem("Radio", "Battery-powered", R.drawable.ic_radio, true, 10));
        
        // Additional items based on difficulty
        if (difficulty.getLevel() >= 2) {
            availableItems.add(new EmergencyItem("Whistle", "For signaling", R.drawable.ic_whistle, true, 5));
            availableItems.add(new EmergencyItem("Mask", "N95 respirator", R.drawable.ic_mask, true, 5));
            availableItems.add(new EmergencyItem("Can Opener", "For food cans", R.drawable.ic_can_opener, true, 5));
        }
        
        if (difficulty.getLevel() >= 3) {
            availableItems.add(new EmergencyItem("Blanket", "Warm blanket", android.R.drawable.ic_menu_gallery, true, 5));
            availableItems.add(new EmergencyItem("Maps", "Local area maps", android.R.drawable.ic_menu_mapmode, true, 5));
            availableItems.add(new EmergencyItem("Cash", "Emergency cash", android.R.drawable.ic_menu_call, true, 5));
        }
        
        if (difficulty.getLevel() >= 4) {
            availableItems.add(new EmergencyItem("Medications", "Prescription meds", android.R.drawable.ic_menu_agenda, true, 10));
            availableItems.add(new EmergencyItem("Multi-tool", "Swiss army knife", android.R.drawable.ic_menu_edit, true, 5));
            availableItems.add(new EmergencyItem("Sanitizer", "Hand sanitizer", android.R.drawable.ic_menu_manage, true, 5));
        }
        
        // Add some non-essential items as distractions
        availableItems.add(new EmergencyItem("Toy", "Not essential", android.R.drawable.ic_menu_slideshow, false, -5));
        availableItems.add(new EmergencyItem("Laptop", "Not essential", android.R.drawable.ic_menu_computer, false, -5));
        
        // Shuffle and limit items
        java.util.Collections.shuffle(availableItems);
        while (availableItems.size() > maxItems + 2) { // +2 for distractions
            availableItems.remove(availableItems.size() - 1);
        }
    }
    
    private void displayAvailableItems() {
        itemsContainer.removeAllViews();
        
        for (EmergencyItem item : availableItems) {
            View itemView = createItemView(item);
            android.widget.GridLayout.LayoutParams params = new android.widget.GridLayout.LayoutParams();
            params.width = 0;
            params.height = android.widget.GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNSPECIFIED, 1f);
            params.setMargins(8, 8, 8, 8);
            itemView.setLayoutParams(params);
            itemsContainer.addView(itemView);
        }
    }
    
    private View createItemView(EmergencyItem item) {
        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setPadding(16, 16, 16, 16);
        itemLayout.setBackgroundResource(R.drawable.option_background);
        
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(item.getIconResId());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
        
        TextView nameTextView = new TextView(this);
        nameTextView.setText(item.getName());
        nameTextView.setTextSize(14);
        nameTextView.setTextColor(Color.WHITE);
        nameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        
        TextView descTextView = new TextView(this);
        descTextView.setText(item.getDescription());
        descTextView.setTextSize(12);
        descTextView.setTextColor(Color.parseColor("#CCCCCC"));
        descTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        
        itemLayout.addView(imageView);
        itemLayout.addView(nameTextView);
        itemLayout.addView(descTextView);
        
        // Set up drag
        itemLayout.setTag(item);
        itemLayout.setOnLongClickListener(v -> {
            ClipData.Item clipItem = new ClipData.Item((String) v.getTag());
            ClipData dragData = new ClipData((String) v.getTag(),
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, clipItem);
            v.startDrag(dragData, new View.DragShadowBuilder(v), null, 0);
            return true;
        });
        
        return itemLayout;
    }
    
    private void updateInstruction() {
        instructionTextView.setText("Drag essential items into the backpack!");
    }
    
    private void updateItemsCollected() {
        itemsCollectedTextView.setText(String.format("Items: %d/%d", collectedItems.size(), totalItemsToCollect));
        
        // Update progress bar
        int progress = (int) ((double) collectedItems.size() / totalItemsToCollect * 100);
        progressBar.setProgress(progress);
    }
    
    @Override
    protected void setupListeners() {
        super.setupListeners();
        
        // Set up backpack drop zone
        backpackContainer.setOnDragListener((v, event) -> {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                    
                case DragEvent.ACTION_DRAG_ENTERED:
                    backpackContainer.setBackgroundColor(Color.parseColor("#4CAF50"));
                    return true;
                    
                case DragEvent.ACTION_DRAG_EXITED:
                    backpackContainer.setBackgroundColor(Color.parseColor("#16213E"));
                    return true;
                    
                case DragEvent.ACTION_DROP:
                    EmergencyItem droppedItem = findItemByName(event.getClipData().getItemAt(0).getText().toString());
                    if (droppedItem != null) {
                        handleItemDrop(droppedItem);
                    }
                    backpackContainer.setBackgroundColor(Color.parseColor("#16213E"));
                    return true;
                    
                case DragEvent.ACTION_DRAG_ENDED:
                    backpackContainer.setBackgroundColor(Color.parseColor("#16213E"));
                    return true;
            }
            return false;
        });
    }
    
    private EmergencyItem findItemByName(String name) {
        for (EmergencyItem item : availableItems) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
    
    private void handleItemDrop(EmergencyItem item) {
        if (collectedItems.contains(item)) {
            Toast.makeText(this, "Already collected!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        collectedItems.add(item);
        availableItems.remove(item);
        
        if (item.isEssential()) {
            addScore(item.getPoints());
            addCoins(1);
            addXP(2);
            showCorrectAnimation();
            Toast.makeText(this, "Great! " + item.getName() + " added!", Toast.LENGTH_SHORT).show();
        } else {
            addScore(item.getPoints()); // Negative points
            showWrongAnimation();
            Toast.makeText(this, item.getName() + " is not essential!", Toast.LENGTH_SHORT).show();
        }
        
        displayAvailableItems();
        updateItemsCollected();
        
        // Check win condition
        long essentialCollected = collectedItems.stream().filter(EmergencyItem::isEssential).count();
        long totalEssential = availableItems.stream().filter(EmergencyItem::isEssential).count() + essentialCollected;
        
        if (essentialCollected >= totalEssential || collectedItems.size() >= totalItemsToCollect) {
            endGame(false);
        }
    }
    
    @Override
    protected void updateGameState() {
        updateItemsCollected();
    }
    
    @Override
    protected void onGameComplete() {
        // Game completion handled in endGame
    }
    
    @Override
    protected int getMaxPossibleScore() {
        return totalItemsToCollect * 15; // Max 15 points per item
    }
}