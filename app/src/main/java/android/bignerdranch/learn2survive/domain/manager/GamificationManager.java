package android.bignerdranch.learn2survive.domain.manager;

import android.bignerdranch.learn2survive.domain.model.Achievement;
import android.bignerdranch.learn2survive.domain.model.Badge;
import android.bignerdranch.learn2survive.domain.model.Challenge;
import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;
import android.bignerdranch.learn2survive.domain.model.PlayerLevel;
import android.bignerdranch.learn2survive.domain.model.UnlockableItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GamificationManager {
    private static GamificationManager instance;
    private PlayerGamificationData playerData;
    private List<PlayerLevel> levelProgression;
    private List<Achievement> allAchievements;
    private List<Badge> allBadges;
    private List<UnlockableItem> allUnlockables;
    private List<Challenge> allChallenges;

    private GamificationManager() {
        initializeData();
    }

    public static synchronized GamificationManager getInstance() {
        if (instance == null) {
            instance = new GamificationManager();
        }
        return instance;
    }

    private void initializeData() {
        initializeLevelProgression();
        initializeAchievements();
        initializeBadges();
        initializeUnlockables();
        initializeChallenges();
    }

    private void initializeLevelProgression() {
        levelProgression = new ArrayList<>();
        
        // Level 1-10
        levelProgression.add(new PlayerLevel(1, "Survival Beginner", 0, 0, null));
        levelProgression.add(new PlayerLevel(2, "Safety Learner", 100, 50, "badge_safety_1"));
        levelProgression.add(new PlayerLevel(3, "Knowledge Seeker", 250, 75, null));
        levelProgression.add(new PlayerLevel(4, "Prepared Student", 500, 100, "badge_prep_1"));
        levelProgression.add(new PlayerLevel(5, "Emergency Ready", 1000, 150, null));
        levelProgression.add(new PlayerLevel(6, "Disaster Expert", 1500, 200, "badge_expert_1"));
        levelProgression.add(new PlayerLevel(7, "Crisis Leader", 2500, 250, null));
        levelProgression.add(new PlayerLevel(8, "Safety Master", 4000, 300, "badge_master_1"));
        levelProgression.add(new PlayerLevel(9, "Rescue Ranger", 6000, 400, null));
        levelProgression.add(new PlayerLevel(10, "Survival Legend", 10000, 500, "badge_legend"));
        
        // Level 11-20
        levelProgression.add(new PlayerLevel(11, "Hero Rising", 15000, 600, null));
        levelProgression.add(new PlayerLevel(12, "Guardian Spirit", 22000, 700, "badge_guardian"));
        levelProgression.add(new PlayerLevel(13, "Protector Elite", 30000, 800, null));
        levelProgression.add(new PlayerLevel(14, "Safety Champion", 40000, 900, "badge_champion"));
        levelProgression.add(new PlayerLevel(15, "Disaster Victor", 55000, 1000, null));
        levelProgression.add(new PlayerLevel(16, "Crisis Conqueror", 75000, 1200, "badge_conqueror"));
        levelProgression.add(new PlayerLevel(17, "Survival Sage", 100000, 1500, null));
        levelProgression.add(new PlayerLevel(18, "Emergency Oracle", 130000, 1800, "badge_oracle"));
        levelProgression.add(new PlayerLevel(19, "Disaster Dominator", 170000, 2200, null));
        levelProgression.add(new PlayerLevel(20, "Ultimate Survivor", 220000, 3000, "badge_ultimate"));
    }

    private void initializeAchievements() {
        allAchievements = new ArrayList<>();
        
        // LESSON ACHIEVEMENTS (10)
        allAchievements.add(new Achievement("first_lesson", "First Steps", "Complete your first lesson", 50, 25, "ic_lesson_1", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("lesson_master_5", "Quick Learner", "Complete 5 lessons", 100, 50, "ic_lesson_5", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COUNT, 5, false, null));
        allAchievements.add(new Achievement("lesson_master_10", "Dedicated Student", "Complete 10 lessons", 200, 100, "ic_lesson_10", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COUNT, 10, false, null));
        allAchievements.add(new Achievement("lesson_master_25", "Knowledge Seeker", "Complete 25 lessons", 500, 250, "ic_lesson_25", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COUNT, 25, false, "badge_learner"));
        allAchievements.add(new Achievement("lesson_master_50", "Expert Scholar", "Complete 50 lessons", 1000, 500, "ic_lesson_50", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COUNT, 50, false, null));
        allAchievements.add(new Achievement("all_earthquake", "Earthquake Expert", "Complete all earthquake lessons", 300, 150, "ic_earthquake", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("all_flood", "Flood Specialist", "Complete all flood lessons", 300, 150, "ic_flood", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("all_fire", "Fire Safety Pro", "Complete all fire lessons", 300, 150, "ic_fire", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("all_typhoon", "Typhoon Master", "Complete all typhoon lessons", 300, 150, "ic_typhoon", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("all_lessons", "Complete Scholar", "Complete all available lessons", 2000, 1000, "ic_all_lessons", Achievement.AchievementCategory.LESSONS, Achievement.AchievementType.COMPLETION, 1, false, "badge_scholar"));

        // QUIZ ACHIEVEMENTS (8)
        allAchievements.add(new Achievement("first_quiz", "Quiz Novice", "Complete your first quiz", 50, 25, "ic_quiz_1", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("perfect_quiz", "Perfect Score", "Get 100% on any quiz", 150, 75, "ic_perfect", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.SCORE, 100, false, "badge_perfect"));
        allAchievements.add(new Achievement("quiz_master_10", "Quiz Champion", "Complete 10 quizzes", 250, 125, "ic_quiz_10", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.COUNT, 10, false, null));
        allAchievements.add(new Achievement("quiz_master_25", "Quiz Expert", "Complete 25 quizzes", 500, 250, "ic_quiz_25", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.COUNT, 25, false, null));
        allAchievements.add(new Achievement("high_scorer", "High Achiever", "Score above 90% on 5 quizzes", 300, 150, "ic_high_score", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.COUNT, 5, false, null));
        allAchievements.add(new Achievement("speed_quiz", "Speed Demon", "Complete a quiz in under 1 minute", 200, 100, "ic_speed", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.TIME_BASED, 60, false, null));
        allAchievements.add(new Achievement("quiz_streak_5", "Quiz Streak", "Complete 5 quizzes in a row", 400, 200, "ic_streak_5", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.STREAK, 5, false, "badge_quiz_streak"));
        allAchievements.add(new Achievement("all_quizzes", "Quiz Master", "Complete all available quizzes", 1500, 750, "ic_all_quizzes", Achievement.AchievementCategory.QUIZZES, Achievement.AchievementType.COMPLETION, 1, false, "badge_quiz_master"));

        // GAME ACHIEVEMENTS (8)
        allAchievements.add(new Achievement("first_game", "Gamer Initiate", "Play your first mini-game", 50, 25, "ic_game_1", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("game_master_10", "Game Enthusiast", "Play 10 mini-games", 200, 100, "ic_game_10", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.COUNT, 10, false, null));
        allAchievements.add(new Achievement("game_master_25", "Game Lover", "Play 25 mini-games", 400, 200, "ic_game_25", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.COUNT, 25, false, null));
        allAchievements.add(new Achievement("high_score_game", "High Scorer", "Get a high score in any game", 150, 75, "ic_trophy", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.SCORE, 500, false, null));
        allAchievements.add(new Achievement("perfect_game", "Perfect Game", "Get 3 stars in any game", 300, 150, "ic_stars_3", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.SCORE, 3, false, "badge_perfect_game"));
        allAchievements.add(new Achievement("all_games", "Game Collector", "Play all mini-games at least once", 500, 250, "ic_all_games", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.COMPLETION, 1, false, "badge_game_collector"));
        allAchievements.add(new Achievement("expert_gamer", "Expert Gamer", "Complete a game on Expert difficulty", 400, 200, "ic_expert", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("game_streak_7", "Weekly Gamer", "Play games for 7 consecutive days", 600, 300, "ic_weekly", Achievement.AchievementCategory.GAMES, Achievement.AchievementType.STREAK, 7, false, "badge_weekly_gamer"));

        // SIMULATION ACHIEVEMENTS (4)
        allAchievements.add(new Achievement("first_sim", "Simulation Rookie", "Complete your first simulation", 75, 35, "ic_sim_1", Achievement.AchievementCategory.SIMULATIONS, Achievement.AchievementType.COMPLETION, 1, false, null));
        allAchievements.add(new Achievement("sim_master_5", "Simulation Expert", "Complete 5 simulations", 300, 150, "ic_sim_5", Achievement.AchievementCategory.SIMULATIONS, Achievement.AchievementType.COUNT, 5, false, null));
        allAchievements.add(new Achievement("perfect_sim", "Perfect Simulation", "Complete a simulation with all correct choices", 250, 125, "ic_sim_perfect", Achievement.AchievementCategory.SIMULATIONS, Achievement.AchievementType.SCORE, 100, false, "badge_sim_perfect"));
        allAchievements.add(new Achievement("all_sims", "Simulation Master", "Complete all available simulations", 1000, 500, "ic_all_sims", Achievement.AchievementCategory.SIMULATIONS, Achievement.AchievementType.COMPLETION, 1, false, "badge_sim_master"));

        // STREAK ACHIEVEMENTS (5)
        allAchievements.add(new Achievement("streak_3", "Consistent Learner", "3-day learning streak", 100, 50, "ic_streak_3", Achievement.AchievementCategory.STREAK, Achievement.AchievementType.STREAK, 3, false, null));
        allAchievements.add(new Achievement("streak_7", "Week Warrior", "7-day learning streak", 250, 125, "ic_streak_7", Achievement.AchievementCategory.STREAK, Achievement.AchievementType.STREAK, 7, false, "badge_week_warrior"));
        allAchievements.add(new Achievement("streak_14", "Fortnight Fighter", "14-day learning streak", 500, 250, "ic_streak_14", Achievement.AchievementCategory.STREAK, Achievement.AchievementType.STREAK, 14, false, null));
        allAchievements.add(new Achievement("streak_30", "Monthly Master", "30-day learning streak", 1000, 500, "ic_streak_30", Achievement.AchievementCategory.STREAK, Achievement.AchievementType.STREAK, 30, false, "badge_monthly_master"));
        allAchievements.add(new Achievement("streak_100", "Centurion", "100-day learning streak", 5000, 2500, "ic_streak_100", Achievement.AchievementCategory.STREAK, Achievement.AchievementType.STREAK, 100, false, "badge_centurion"));

        // MILESTONE ACHIEVEMENTS (5)
        allAchievements.add(new Achievement("xp_1000", "XP Pioneer", "Earn 1,000 total XP", 200, 100, "ic_xp_1k", Achievement.AchievementCategory.MILESTONE, Achievement.AchievementType.COUNT, 1000, false, null));
        allAchievements.add(new Achievement("xp_10000", "XP Expert", "Earn 10,000 total XP", 1000, 500, "ic_xp_10k", Achievement.AchievementCategory.MILESTONE, Achievement.AchievementType.COUNT, 10000, false, "badge_xp_expert"));
        allAchievements.add(new Achievement("coins_1000", "Coin Collector", "Earn 1,000 total coins", 150, 75, "ic_coins_1k", Achievement.AchievementCategory.MILESTONE, Achievement.AchievementType.COUNT, 1000, false, null));
        allAchievements.add(new Achievement("level_10", "Double Digits", "Reach level 10", 500, 250, "ic_level_10", Achievement.AchievementCategory.MILESTONE, Achievement.AchievementType.COMPLETION, 1, false, "badge_level_10"));
        allAchievements.add(new Achievement("level_20", "Maximum Level", "Reach the maximum level", 2000, 1000, "ic_level_20", Achievement.AchievementCategory.MILESTONE, Achievement.AchievementType.COMPLETION, 1, false, "badge_max_level"));
    }

    private void initializeBadges() {
        allBadges = new ArrayList<>();
        
        // Common Badges
        allBadges.add(new Badge("badge_safety_1", "Safety Aware", "Completed initial safety training", "ic_badge_safety", Badge.BadgeRarity.COMMON, 2, null));
        allBadges.add(new Badge("badge_prep_1", "Prepared", "Basic emergency preparation", "ic_badge_prep", Badge.BadgeRarity.COMMON, 4, null));
        allBadges.add(new Badge("badge_expert_1", "Expert", "Disaster expertise recognized", "ic_badge_expert", Badge.BadgeRarity.COMMON, 6, null));
        allBadges.add(new Badge("badge_master_1", "Master", "Mastery in disaster safety", "ic_badge_master", Badge.BadgeRarity.COMMON, 8, null));
        
        // Rare Badges
        allBadges.add(new Badge("badge_learner", "Dedicated Learner", "Completed 25 lessons", "ic_badge_learner", Badge.BadgeRarity.RARE, 5, "lesson_master_25"));
        allBadges.add(new Badge("badge_perfect", "Perfectionist", "Achieved perfect quiz score", "ic_badge_perfect", Badge.BadgeRarity.RARE, 3, "perfect_quiz"));
        allBadges.add(new Badge("badge_quiz_streak", "Quiz Streaker", "5 quizzes in a row", "ic_badge_quiz_streak", Badge.BadgeRarity.RARE, 7, "quiz_streak_5"));
        allBadges.add(new Badge("badge_perfect_game", "Perfect Gamer", "3 stars in a game", "ic_badge_perfect_game", Badge.BadgeRarity.RARE, 10, "perfect_game"));
        allBadges.add(new Badge("badge_sim_perfect", "Perfect Simulation", "Flawless simulation run", "ic_badge_sim_perfect", Badge.BadgeRarity.RARE, 8, "perfect_sim"));
        
        // Epic Badges
        allBadges.add(new Badge("badge_scholar", "Complete Scholar", "Completed all lessons", "ic_badge_scholar", Badge.BadgeRarity.EPIC, 10, "all_lessons"));
        allBadges.add(new Badge("badge_quiz_master", "Quiz Master", "Completed all quizzes", "ic_badge_quiz_master", Badge.BadgeRarity.EPIC, 12, "all_quizzes"));
        allBadges.add(new Badge("badge_game_collector", "Game Collector", "Played all games", "ic_badge_game_collector", Badge.BadgeRarity.EPIC, 8, "all_games"));
        allBadges.add(new Badge("badge_sim_master", "Simulation Master", "Completed all simulations", "ic_badge_sim_master", Badge.BadgeRarity.EPIC, 15, "all_sims"));
        allBadges.add(new Badge("badge_week_warrior", "Week Warrior", "7-day streak", "ic_badge_week_warrior", Badge.BadgeRarity.EPIC, 5, "streak_7"));
        allBadges.add(new Badge("badge_monthly_master", "Monthly Master", "30-day streak", "ic_badge_monthly", Badge.BadgeRarity.EPIC, 10, "streak_30"));
        
        // Legendary Badges
        allBadges.add(new Badge("badge_legend", "Survival Legend", "Reached level 10", "ic_badge_legend", Badge.BadgeRarity.LEGENDARY, 10, null));
        allBadges.add(new Badge("badge_guardian", "Guardian", "Reached level 12", "ic_badge_guardian", Badge.BadgeRarity.LEGENDARY, 12, null));
        allBadges.add(new Badge("badge_champion", "Champion", "Reached level 14", "ic_badge_champion", Badge.BadgeRarity.LEGENDARY, 14, null));
        allBadges.add(new Badge("badge_conqueror", "Conqueror", "Reached level 16", "ic_badge_conqueror", Badge.BadgeRarity.LEGENDARY, 16, null));
        allBadges.add(new Badge("badge_oracle", "Oracle", "Reached level 18", "ic_badge_oracle", Badge.BadgeRarity.LEGENDARY, 18, null));
        allBadges.add(new Badge("badge_ultimate", "Ultimate Survivor", "Reached level 20", "ic_badge_ultimate", Badge.BadgeRarity.LEGENDARY, 20, null));
        allBadges.add(new Badge("badge_centurion", "Centurion", "100-day streak", "ic_badge_centurion", Badge.BadgeRarity.LEGENDARY, 20, "streak_100"));
    }

    private void initializeUnlockables() {
        allUnlockables = new ArrayList<>();
        
        // Avatars
        allUnlockables.add(new UnlockableItem("avatar_survivor", "Survivor", "Basic survivor avatar", "ic_avatar_survivor", UnlockableItem.ItemType.AVATAR, 0, 1, null));
        allUnlockables.add(new UnlockableItem("avatar_hero", "Hero", "Hero avatar", "ic_avatar_hero", UnlockableItem.ItemType.AVATAR, 500, 5, null));
        allUnlockables.add(new UnlockableItem("avatar_expert", "Expert", "Expert avatar", "ic_avatar_expert", UnlockableItem.ItemType.AVATAR, 1000, 10, "badge_expert_1"));
        allUnlockables.add(new UnlockableItem("avatar_legend", "Legend", "Legendary avatar", "ic_avatar_legend", UnlockableItem.ItemType.AVATAR, 2500, 15, "badge_legend"));
        
        // Themes
        allUnlockables.add(new UnlockableItem("theme_default", "Default Theme", "Standard app theme", "ic_theme_default", UnlockableItem.ItemType.THEME, 0, 1, null));
        allUnlockables.add(new UnlockableItem("theme_dark", "Dark Mode", "Dark theme", "ic_theme_dark", UnlockableItem.ItemType.THEME, 300, 3, null));
        allUnlockables.add(new UnlockableItem("theme_ocean", "Ocean Theme", "Ocean blue theme", "ic_theme_ocean", UnlockableItem.ItemType.THEME, 800, 8, null));
        allUnlockables.add(new UnlockableItem("theme_fire", "Fire Theme", "Fire red theme", "ic_theme_fire", UnlockableItem.ItemType.THEME, 1200, 12, null));
        
        // Titles
        allUnlockables.add(new UnlockableItem("title_rookie", "Rookie", "New survivor title", "ic_title_rookie", UnlockableItem.ItemType.TITLE, 0, 1, null));
        allUnlockables.add(new UnlockableItem("title_trained", "Trained", "Trained survivor title", "ic_title_trained", UnlockableItem.ItemType.TITLE, 400, 4, null));
        allUnlockables.add(new UnlockableItem("title_expert", "Expert", "Expert title", "ic_title_expert", UnlockableItem.ItemType.TITLE, 900, 9, "badge_expert_1"));
        allUnlockables.add(new UnlockableItem("title_master", "Master", "Master title", "ic_title_master", UnlockableItem.ItemType.TITLE, 2000, 15, "badge_master_1"));
        
        // Backgrounds
        allUnlockables.add(new UnlockableItem("bg_default", "Default", "Default background", "ic_bg_default", UnlockableItem.ItemType.BACKGROUND, 0, 1, null));
        allUnlockables.add(new UnlockableItem("bg_mountain", "Mountain", "Mountain background", "ic_bg_mountain", UnlockableItem.ItemType.BACKGROUND, 600, 6, null));
        allUnlockables.add(new UnlockableItem("bg_city", "City", "City background", "ic_bg_city", UnlockableItem.ItemType.BACKGROUND, 1000, 10, null));
        allUnlockables.add(new UnlockableItem("bg_beach", "Beach", "Beach background", "ic_bg_beach", UnlockableItem.ItemType.BACKGROUND, 1500, 12, null));
    }

    private void initializeChallenges() {
        allChallenges = new ArrayList<>();
        
        // Daily Challenges
        allChallenges.add(new Challenge("daily_lesson_1", "Daily Lesson", "Complete 1 lesson today", 50, 25, "ic_challenge_lesson", Challenge.ChallengeType.COMPLETE_LESSONS, 1, Challenge.ChallengeFrequency.DAILY));
        allChallenges.add(new Challenge("daily_quiz_1", "Daily Quiz", "Complete 1 quiz today", 50, 25, "ic_challenge_quiz", Challenge.ChallengeType.COMPLETE_QUIZZES, 1, Challenge.ChallengeFrequency.DAILY));
        allChallenges.add(new Challenge("daily_game_1", "Daily Game", "Play 1 mini-game today", 50, 25, "ic_challenge_game", Challenge.ChallengeType.PLAY_GAMES, 1, Challenge.ChallengeFrequency.DAILY));
        allChallenges.add(new Challenge("daily_xp_100", "XP Hunter", "Earn 100 XP today", 75, 35, "ic_challenge_xp", Challenge.ChallengeType.EARN_XP, 100, Challenge.ChallengeFrequency.DAILY));
        
        // Weekly Challenges
        allChallenges.add(new Challenge("weekly_lessons_5", "Weekly Lessons", "Complete 5 lessons this week", 200, 100, "ic_challenge_weekly_lesson", Challenge.ChallengeType.COMPLETE_LESSONS, 5, Challenge.ChallengeFrequency.WEEKLY));
        allChallenges.add(new Challenge("weekly_quizzes_3", "Weekly Quizzes", "Complete 3 quizzes this week", 200, 100, "ic_challenge_weekly_quiz", Challenge.ChallengeType.COMPLETE_QUIZZES, 3, Challenge.ChallengeFrequency.WEEKLY));
        allChallenges.add(new Challenge("weekly_games_7", "Weekly Games", "Play 7 games this week", 300, 150, "ic_challenge_weekly_game", Challenge.ChallengeType.PLAY_GAMES, 7, Challenge.ChallengeFrequency.WEEKLY));
        allChallenges.add(new Challenge("weekly_xp_500", "XP Master", "Earn 500 XP this week", 400, 200, "ic_challenge_weekly_xp", Challenge.ChallengeType.EARN_XP, 500, Challenge.ChallengeFrequency.WEEKLY));
        allChallenges.add(new Challenge("weekly_perfect", "Perfect Week", "Get a perfect score this week", 500, 250, "ic_challenge_perfect", Challenge.ChallengeType.PERFECT_SCORE, 1, Challenge.ChallengeFrequency.WEEKLY));
    }

    // XP and Level Methods
    public void addXP(int amount) {
        if (playerData == null) return;
        
        playerData.setCurrentXP(playerData.getCurrentXP() + amount);
        playerData.setTotalXP(playerData.getTotalXP() + amount);
        
        checkLevelUp();
    }

    public void checkLevelUp() {
        if (playerData == null) return;
        
        int currentLevel = playerData.getCurrentLevel();
        PlayerLevel nextLevel = getLevelForXP(playerData.getCurrentXP());
        
        if (nextLevel != null && nextLevel.getLevel() > currentLevel) {
            // Level up!
            playerData.setCurrentLevel(nextLevel.getLevel());
            playerData.setCoins(playerData.getCoins() + nextLevel.getCoinsReward());
            
            // Check for badge unlock
            if (nextLevel.getBadgeUnlock() != null) {
                unlockBadge(nextLevel.getBadgeUnlock());
            }
        }
    }

    public PlayerLevel getLevelForXP(int xp) {
        for (PlayerLevel level : levelProgression) {
            if (xp < level.getXpRequired()) {
                return level;
            }
        }
        return levelProgression.get(levelProgression.size() - 1);
    }

    public int getXPToNextLevel() {
        if (playerData == null) return 0;
        
        PlayerLevel currentLevel = getLevelData(playerData.getCurrentLevel());
        PlayerLevel nextLevel = getLevelData(playerData.getCurrentLevel() + 1);
        
        if (nextLevel == null) return 0;
        
        return nextLevel.getXpRequired() - playerData.getCurrentXP();
    }

    public PlayerLevel getLevelData(int level) {
        for (PlayerLevel lvl : levelProgression) {
            if (lvl.getLevel() == level) {
                return lvl;
            }
        }
        return null;
    }

    // Coins Methods
    public void addCoins(int amount) {
        if (playerData == null) return;
        playerData.setCoins(playerData.getCoins() + amount);
    }

    public boolean spendCoins(int amount) {
        if (playerData == null || playerData.getCoins() < amount) {
            return false;
        }
        playerData.setCoins(playerData.getCoins() - amount);
        return true;
    }

    // Achievement Methods
    public void updateAchievementProgress(String achievementId, int increment) {
        if (playerData == null) return;
        
        PlayerGamificationData.AchievementProgress progress = playerData.getAchievementProgress().get(achievementId);
        
        if (progress == null) {
            progress = new PlayerGamificationData.AchievementProgress(achievementId, 0);
            playerData.getAchievementProgress().put(achievementId, progress);
        }
        
        if (!progress.isCompleted()) {
            progress.setCurrentValue(progress.getCurrentValue() + increment);
            
            Achievement achievement = getAchievementById(achievementId);
            if (achievement != null && progress.getCurrentValue() >= achievement.getTargetValue()) {
                completeAchievement(achievementId);
            }
        }
    }

    public void completeAchievement(String achievementId) {
        if (playerData == null) return;
        
        PlayerGamificationData.AchievementProgress progress = playerData.getAchievementProgress().get(achievementId);
        if (progress == null || progress.isCompleted()) return;
        
        progress.setCompleted(true);
        progress.setCompletedAt(new Date());
        
        Achievement achievement = getAchievementById(achievementId);
        if (achievement != null) {
            addXP(achievement.getXpReward());
            addCoins(achievement.getCoinsReward());
            
            // Check for badge unlock
            if (achievement.getBadgeUnlock() != null) {
                unlockBadge(achievement.getBadgeUnlock());
            }
        }
    }

    public Achievement getAchievementById(String id) {
        for (Achievement achievement : allAchievements) {
            if (achievement.getId().equals(id)) {
                return achievement;
            }
        }
        return null;
    }

    // Badge Methods
    public void unlockBadge(String badgeId) {
        if (playerData == null || playerData.getUnlockedBadges().contains(badgeId)) {
            return;
        }
        
        playerData.getUnlockedBadges().add(badgeId);
    }

    public boolean hasBadge(String badgeId) {
        return playerData != null && playerData.getUnlockedBadges().contains(badgeId);
    }

    // Streak Methods
    public void updateStreak() {
        if (playerData == null) return;
        
        Calendar today = Calendar.getInstance();
        Calendar lastActive = Calendar.getInstance();
        
        if (playerData.getLastActiveDate() != null) {
            lastActive.setTime(playerData.getLastActiveDate());
        }
        
        // Check if last active was yesterday
        if (isYesterday(lastActive, today)) {
            playerData.setStreak(playerData.getStreak() + 1);
        } 
        // Check if last active was today (already updated)
        else if (isSameDay(lastActive, today)) {
            // Streak remains the same
        }
        // Streak broken
        else {
            playerData.setStreak(1);
        }
        
        playerData.setLastActiveDate(new Date());
        
        // Update streak achievements
        updateAchievementProgress("streak_3", 0); // Check completion
        updateAchievementProgress("streak_7", 0);
        updateAchievementProgress("streak_14", 0);
        updateAchievementProgress("streak_30", 0);
        updateAchievementProgress("streak_100", 0);
    }

    private boolean isYesterday(Calendar cal1, Calendar cal2) {
        Calendar yesterday = (Calendar) cal2.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        return isSameDay(cal1, yesterday);
    }

    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    // Daily Rewards
    public boolean canClaimDailyReward() {
        if (playerData == null) return false;
        
        Calendar today = Calendar.getInstance();
        Calendar lastClaim = Calendar.getInstance();
        
        if (playerData.getDailyRewardData().getLastClaimDate() != null) {
            lastClaim.setTime(playerData.getDailyRewardData().getLastClaimDate());
        }
        
        return !isSameDay(lastClaim, today);
    }

    public int claimDailyReward() {
        if (!canClaimDailyReward()) return 0;
        
        int consecutiveDays = playerData.getDailyRewardData().getConsecutiveDays();
        
        // Check if streak is broken
        Calendar today = Calendar.getInstance();
        Calendar lastClaim = Calendar.getInstance();
        
        if (playerData.getDailyRewardData().getLastClaimDate() != null) {
            lastClaim.setTime(playerData.getDailyRewardData().getLastClaimDate());
        }
        
        if (!isYesterday(lastClaim, today)) {
            consecutiveDays = 0;
        }
        
        consecutiveDays++;
        playerData.getDailyRewardData().setConsecutiveDays(consecutiveDays);
        playerData.getDailyRewardData().setLastClaimDate(new Date());
        playerData.getDailyRewardData().setClaimedToday(true);
        
        // Calculate reward based on consecutive days
        int reward = calculateDailyReward(consecutiveDays);
        addCoins(reward);
        
        return reward;
    }

    private int calculateDailyReward(int consecutiveDays) {
        // Base reward + bonus for streak
        int baseReward = 25;
        int streakBonus = Math.min(consecutiveDays * 5, 100); // Max 100 bonus
        return baseReward + streakBonus;
    }

    // Challenge Methods
    public void updateChallengeProgress(String challengeId, int increment) {
        if (playerData == null) return;
        
        PlayerGamificationData.ChallengeProgress progress = playerData.getDailyChallenge();
        if (progress != null && progress.getChallengeId().equals(challengeId)) {
            if (!progress.isCompleted()) {
                progress.setCurrentValue(progress.getCurrentValue() + increment);
                
                Challenge challenge = getChallengeById(challengeId);
                if (challenge != null && progress.getCurrentValue() >= challenge.getTargetValue()) {
                    completeChallenge(challengeId, true);
                }
            }
        }
        
        progress = playerData.getWeeklyChallenge();
        if (progress != null && progress.getChallengeId().equals(challengeId)) {
            if (!progress.isCompleted()) {
                progress.setCurrentValue(progress.getCurrentValue() + increment);
                
                Challenge challenge = getChallengeById(challengeId);
                if (challenge != null && progress.getCurrentValue() >= challenge.getTargetValue()) {
                    completeChallenge(challengeId, false);
                }
            }
        }
    }

    public void completeChallenge(String challengeId, boolean isDaily) {
        if (playerData == null) return;
        
        PlayerGamificationData.ChallengeProgress progress = isDaily ? 
            playerData.getDailyChallenge() : playerData.getWeeklyChallenge();
        
        if (progress == null || progress.isCompleted()) return;
        
        progress.setCompleted(true);
        progress.setCompletedAt(new Date());
        
        Challenge challenge = getChallengeById(challengeId);
        if (challenge != null) {
            addXP(challenge.getXpReward());
            addCoins(challenge.getCoinsReward());
        }
    }

    public Challenge getChallengeById(String id) {
        for (Challenge challenge : allChallenges) {
            if (challenge.getId().equals(id)) {
                return challenge;
            }
        }
        return null;
    }

    // Unlockable Methods
    public boolean canUnlockItem(String itemId) {
        UnlockableItem item = getUnlockableById(itemId);
        if (item == null || playerData == null) return false;
        
        // Check if already unlocked
        if (playerData.getUnlockedItems().contains(itemId)) {
            return true;
        }
        
        // Check level requirement
        if (playerData.getCurrentLevel() < item.getRequiredLevel()) {
            return false;
        }
        
        // Check badge requirement
        if (item.getRequiredBadge() != null && !hasBadge(item.getRequiredBadge())) {
            return false;
        }
        
        // Check coins
        if (playerData.getCoins() < item.getCost()) {
            return false;
        }
        
        return true;
    }

    public boolean unlockItem(String itemId) {
        if (!canUnlockItem(itemId)) return false;
        
        UnlockableItem item = getUnlockableById(itemId);
        if (item == null || playerData == null) return false;
        
        if (spendCoins(item.getCost())) {
            playerData.getUnlockedItems().add(itemId);
            return true;
        }
        
        return false;
    }

    public UnlockableItem getUnlockableById(String id) {
        for (UnlockableItem item : allUnlockables) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    // Getters
    public PlayerGamificationData getPlayerData() {
        return playerData;
    }

    public void setPlayerData(PlayerGamificationData playerData) {
        this.playerData = playerData;
    }

    public List<PlayerLevel> getLevelProgression() {
        return levelProgression;
    }

    public List<Achievement> getAllAchievements() {
        return allAchievements;
    }

    public List<Badge> getAllBadges() {
        return allBadges;
    }

    public List<UnlockableItem> getAllUnlockables() {
        return allUnlockables;
    }

    public List<Challenge> getAllChallenges() {
        return allChallenges;
    }
}