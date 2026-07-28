package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.User;

public interface UserRepository {
    void getUserData(String userId, UserDataCallback callback);
    void updateUserData(String userId, User user, UserDataCallback callback);
    void updateXP(String userId, int xpToAdd, UserDataCallback callback);
    void updateCoins(String userId, int coinsToAdd, UserDataCallback callback);
    void updateStreak(String userId, UserDataCallback callback);
    void addAchievement(String userId, String achievementId, UserDataCallback callback);
    
    interface UserDataCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }
}
