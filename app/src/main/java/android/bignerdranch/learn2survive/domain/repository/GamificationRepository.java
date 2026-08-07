package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;

public interface GamificationRepository {
    void savePlayerData(PlayerGamificationData playerData, GamificationCallback callback);
    void loadPlayerData(String userId, GamificationDataCallback callback);
    void syncPlayerData(PlayerGamificationData playerData, GamificationCallback callback);
    
    interface GamificationCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
    
    interface GamificationDataCallback {
        void onSuccess(PlayerGamificationData playerData);
        void onFailure(Exception e);
    }
}