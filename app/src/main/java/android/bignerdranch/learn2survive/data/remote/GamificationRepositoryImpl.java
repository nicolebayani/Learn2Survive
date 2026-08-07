package android.bignerdranch.learn2survive.data.remote;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;
import android.bignerdranch.learn2survive.domain.repository.GamificationRepository;

public class GamificationRepositoryImpl implements GamificationRepository {
    private FirebaseFirestore db;
    private static final String GAMIFICATION_COLLECTION = "player_gamification";

    public GamificationRepositoryImpl() {
        this.db = FirebaseFirestore.getInstance();
    }

    @Override
    public void savePlayerData(PlayerGamificationData playerData, GamificationCallback callback) {
        if (playerData.getUserId() == null) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null"));
            return;
        }

        DocumentReference docRef = db.collection(GAMIFICATION_COLLECTION)
                .document(playerData.getUserId());

        docRef.set(playerData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void loadPlayerData(String userId, GamificationDataCallback callback) {
        if (userId == null) {
            callback.onFailure(new IllegalArgumentException("User ID cannot be null"));
            return;
        }

        DocumentReference docRef = db.collection(GAMIFICATION_COLLECTION)
                .document(userId);

        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        PlayerGamificationData playerData = documentSnapshot.toObject(PlayerGamificationData.class);
                        callback.onSuccess(playerData);
                    } else {
                        // Create new player data
                        PlayerGamificationData newPlayerData = new PlayerGamificationData();
                        newPlayerData.setUserId(userId);
                        callback.onSuccess(newPlayerData);
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    @Override
    public void syncPlayerData(PlayerGamificationData playerData, GamificationCallback callback) {
        // First load the latest data from Firebase
        loadPlayerData(playerData.getUserId(), new GamificationDataCallback() {
            @Override
            public void onSuccess(PlayerGamificationData serverData) {
                // Merge the data (server data takes precedence for timestamps)
                if (serverData != null) {
                    // Preserve server-side timestamps
                    if (serverData.getLastActiveDate() != null) {
                        playerData.setLastActiveDate(serverData.getLastActiveDate());
                    }
                    if (serverData.getDailyRewardData() != null) {
                        playerData.setDailyRewardData(serverData.getDailyRewardData());
                    }
                    
                    // Merge achievement progress
                    if (serverData.getAchievementProgress() != null) {
                        for (String key : serverData.getAchievementProgress().keySet()) {
                            if (!playerData.getAchievementProgress().containsKey(key)) {
                                playerData.getAchievementProgress().put(key, serverData.getAchievementProgress().get(key));
                            }
                        }
                    }
                    
                    // Merge unlocked badges
                    if (serverData.getUnlockedBadges() != null) {
                        for (String badge : serverData.getUnlockedBadges()) {
                            if (!playerData.getUnlockedBadges().contains(badge)) {
                                playerData.getUnlockedBadges().add(badge);
                            }
                        }
                    }
                    
                    // Merge unlocked items
                    if (serverData.getUnlockedItems() != null) {
                        for (String item : serverData.getUnlockedItems()) {
                            if (!playerData.getUnlockedItems().contains(item)) {
                                playerData.getUnlockedItems().add(item);
                            }
                        }
                    }
                }
                
                // Save the merged data back to Firebase
                savePlayerData(playerData, callback);
            }

            @Override
            public void onFailure(Exception e) {
                callback.onFailure(e);
            }
        });
    }
}