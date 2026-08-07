package android.bignerdranch.learn2survive.ui.gamification;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.manager.GamificationManager;
import android.bignerdranch.learn2survive.domain.model.Achievement;
import android.bignerdranch.learn2survive.domain.model.Badge;
import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;
import android.bignerdranch.learn2survive.domain.model.UnlockableItem;
import android.bignerdranch.learn2survive.ui.gamification.adapters.AchievementAdapter;
import android.bignerdranch.learn2survive.ui.gamification.adapters.BadgeAdapter;
import android.bignerdranch.learn2survive.ui.gamification.adapters.UnlockableAdapter;

public class RewardScreenActivity extends AppCompatActivity {
    private GamificationManager gamificationManager;
    
    // Header elements
    private TextView levelTextView;
    private TextView levelTitleTextView;
    private ProgressBar xpProgressBar;
    private TextView xpTextView;
    private TextView coinsTextView;
    private TextView streakTextView;
    
    // Tabs
    private Button achievementsTab;
    private Button badgesTab;
    private Button shopTab;
    private Button challengesTab;
    
    // Content areas
    private RecyclerView achievementsRecyclerView;
    private RecyclerView badgesRecyclerView;
    private RecyclerView shopRecyclerView;
    private LinearLayout challengesContainer;
    
    // Adapters
    private AchievementAdapter achievementAdapter;
    private BadgeAdapter badgeAdapter;
    private UnlockableAdapter unlockableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_screen);
        
        gamificationManager = GamificationManager.getInstance();
        
        initViews();
        setupListeners();
        loadGamificationData();
    }
    
    private void initViews() {
        // Header
        levelTextView = findViewById(R.id.levelTextView);
        levelTitleTextView = findViewById(R.id.levelTitleTextView);
        xpProgressBar = findViewById(R.id.xpProgressBar);
        xpTextView = findViewById(R.id.xpTextView);
        coinsTextView = findViewById(R.id.coinsTextView);
        streakTextView = findViewById(R.id.streakTextView);
        
        // Tabs
        achievementsTab = findViewById(R.id.achievementsTab);
        badgesTab = findViewById(R.id.badgesTab);
        shopTab = findViewById(R.id.shopTab);
        challengesTab = findViewById(R.id.challengesTab);
        
        // Content areas
        achievementsRecyclerView = findViewById(R.id.achievementsRecyclerView);
        badgesRecyclerView = findViewById(R.id.badgesRecyclerView);
        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        challengesContainer = findViewById(R.id.challengesContainer);
        
        // Setup RecyclerViews
        achievementsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        badgesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Rewards");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    private void setupListeners() {
        achievementsTab.setOnClickListener(v -> showTab("achievements"));
        badgesTab.setOnClickListener(v -> showTab("badges"));
        shopTab.setOnClickListener(v -> showTab("shop"));
        challengesTab.setOnClickListener(v -> showTab("challenges"));
    }
    
    private void showTab(String tabName) {
        // Hide all content areas
        achievementsRecyclerView.setVisibility(View.GONE);
        badgesRecyclerView.setVisibility(View.GONE);
        shopRecyclerView.setVisibility(View.GONE);
        challengesContainer.setVisibility(View.GONE);
        
        // Reset tab styles
        achievementsTab.setSelected(false);
        badgesTab.setSelected(false);
        shopTab.setSelected(false);
        challengesTab.setSelected(false);
        
        // Show selected tab
        switch (tabName) {
            case "achievements":
                achievementsRecyclerView.setVisibility(View.VISIBLE);
                achievementsTab.setSelected(true);
                break;
            case "badges":
                badgesRecyclerView.setVisibility(View.VISIBLE);
                badgesTab.setSelected(true);
                break;
            case "shop":
                shopRecyclerView.setVisibility(View.VISIBLE);
                shopTab.setSelected(true);
                break;
            case "challenges":
                challengesContainer.setVisibility(View.VISIBLE);
                challengesTab.setSelected(true);
                break;
        }
    }
    
    private void loadGamificationData() {
        PlayerGamificationData playerData = gamificationManager.getPlayerData();
        
        if (playerData != null) {
            // Update header
            levelTextView.setText(String.valueOf(playerData.getCurrentLevel()));
            
            var levelData = gamificationManager.getLevelData(playerData.getCurrentLevel());
            if (levelData != null) {
                levelTitleTextView.setText(levelData.getTitle());
            }
            
            xpTextView.setText(String.format("%d / %d XP", 
                playerData.getCurrentXP(), 
                gamificationManager.getXPToNextLevel()));
            
            xpProgressBar.setMax(gamificationManager.getXPToNextLevel());
            xpProgressBar.setProgress(playerData.getCurrentXP());
            
            coinsTextView.setText(String.valueOf(playerData.getCoins()));
            streakTextView.setText(String.format("%d day streak", playerData.getStreak()));
        }
        
        // Load achievements
        loadAchievements();
        
        // Load badges
        loadBadges();
        
        // Load shop items
        loadShop();
        
        // Load challenges
        loadChallenges();
        
        // Show achievements tab by default
        showTab("achievements");
    }
    
    private void loadAchievements() {
        List<Achievement> allAchievements = gamificationManager.getAllAchievements();
        PlayerGamificationData playerData = gamificationManager.getPlayerData();
        
        List<Achievement> displayAchievements = new ArrayList<>();
        for (Achievement achievement : allAchievements) {
            if (!achievement.isHidden() || (playerData != null && 
                playerData.getAchievementProgress().containsKey(achievement.getId()))) {
                displayAchievements.add(achievement);
            }
        }
        
        achievementAdapter = new AchievementAdapter(displayAchievements, playerData);
        achievementsRecyclerView.setAdapter(achievementAdapter);
    }
    
    private void loadBadges() {
        List<Badge> allBadges = gamificationManager.getAllBadges();
        PlayerGamificationData playerData = gamificationManager.getPlayerData();
        
        badgeAdapter = new BadgeAdapter(allBadges, playerData);
        badgesRecyclerView.setAdapter(badgeAdapter);
    }
    
    private void loadShop() {
        List<UnlockableItem> allItems = gamificationManager.getAllUnlockables();
        PlayerGamificationData playerData = gamificationManager.getPlayerData();
        
        unlockableAdapter = new UnlockableAdapter(allItems, playerData, this::onUnlockItem);
        shopRecyclerView.setAdapter(unlockableAdapter);
    }
    
    private void loadChallenges() {
        PlayerGamificationData playerData = gamificationManager.getPlayerData();
        
        if (playerData != null) {
            // Daily challenge
            if (playerData.getDailyChallenge() != null) {
                View dailyChallengeView = createChallengeView(playerData.getDailyChallenge(), true);
                challengesContainer.addView(dailyChallengeView);
            }
            
            // Weekly challenge
            if (playerData.getWeeklyChallenge() != null) {
                View weeklyChallengeView = createChallengeView(playerData.getWeeklyChallenge(), false);
                challengesContainer.addView(weeklyChallengeView);
            }
        }
    }
    
    private View createChallengeView(PlayerGamificationData.ChallengeProgress challenge, boolean isDaily) {
        View view = getLayoutInflater().inflate(R.layout.item_challenge, challengesContainer, false);
        
        TextView titleTextView = view.findViewById(R.id.challengeTitle);
        TextView descriptionTextView = view.findViewById(R.id.challengeDescription);
        TextView progressTextView = view.findViewById(R.id.challengeProgress);
        ProgressBar progressBar = view.findViewById(R.id.challengeProgressBar);
        TextView rewardTextView = view.findViewById(R.id.challengeReward);
        
        titleTextView.setText(isDaily ? "Daily Challenge" : "Weekly Challenge");
        descriptionTextView.setText(challenge.getChallengeId());
        progressTextView.setText(String.format("%d / %d", challenge.getCurrentValue(), challenge.getTargetValue()));
        progressBar.setMax(challenge.getTargetValue());
        progressBar.setProgress(challenge.getCurrentValue());
        rewardTextView.setText(String.format("Rewards: %d XP, %d Coins", 50, 25));
        
        return view;
    }
    
    private void onUnlockItem(UnlockableItem item) {
        if (gamificationManager.unlockItem(item.getId())) {
            // Refresh the shop
            loadShop();
            // Update coins display
            loadGamificationData();
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