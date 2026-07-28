package android.bignerdranch.learn2survive.data.repository;

import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.domain.repository.UserRepository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private FirebaseFirestore firestore;
    private static final String USERS_COLLECTION = "users";

    public UserRepositoryImpl() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void getUserData(String userId, final UserDataCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    if (user != null) {
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure("Failed to parse user data");
                    }
                } else {
                    // User doesn't exist, create new user
                    createNewUser(userId, callback);
                }
            } else {
                callback.onFailure(task.getException().getMessage());
            }
        });
    }

    private void createNewUser(String userId, final UserDataCallback callback) {
        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setCreatedAt(System.currentTimeMillis());
        
        firestore.collection(USERS_COLLECTION).document(userId)
                .set(newUser)
                .addOnSuccessListener(aVoid -> callback.onSuccess(newUser))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void updateUserData(String userId, User user, final UserDataCallback callback) {
        firestore.collection(USERS_COLLECTION).document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> callback.onSuccess(user))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void updateXP(String userId, final int xpToAdd, final UserDataCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        
        firestore.runTransaction((transaction) -> {
            DocumentSnapshot snapshot = transaction.get(docRef);
            User user = snapshot.toObject(User.class);
            
            if (user == null) {
                throw new Exception("User not found");
            }
            
            int currentXP = user.getCurrentXP() + xpToAdd;
            int maxXP = user.getMaxXP();
            int level = user.getLevel();
            
            // Level up logic
            while (currentXP >= maxXP) {
                currentXP -= maxXP;
                level++;
                maxXP = (int) (maxXP * 1.5); // Increase max XP by 50% each level
            }
            
            transaction.update(docRef, "currentXP", currentXP);
            transaction.update(docRef, "maxXP", maxXP);
            transaction.update(docRef, "level", level);
            
            return null;
        }).addOnSuccessListener(aVoid -> {
            getUserData(userId, callback);
        }).addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void updateCoins(String userId, final int coinsToAdd, final UserDataCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        
        docRef.update("coins", FieldValue.increment(coinsToAdd))
                .addOnSuccessListener(aVoid -> getUserData(userId, callback))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void updateStreak(String userId, final UserDataCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        
        docRef.get().addOnSuccessListener(document -> {
            if (document.exists()) {
                User user = document.toObject(User.class);
                if (user != null) {
                    long lastActive = user.getLastActiveDate();
                    long now = System.currentTimeMillis();
                    
                    // Check if last active was yesterday
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(now);
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    long todayStart = cal.getTimeInMillis();
                    
                    cal.add(Calendar.DAY_OF_MONTH, -1);
                    long yesterdayStart = cal.getTimeInMillis();
                    
                    int newStreak = user.getDailyStreak();
                    
                    if (lastActive >= yesterdayStart && lastActive < todayStart) {
                        // Last active was yesterday, increment streak
                        newStreak++;
                    } else if (lastActive < yesterdayStart) {
                        // Streak broken, reset to 1
                        newStreak = 1;
                    }
                    // If last active was today, keep current streak
                    
                    docRef.update("dailyStreak", newStreak, "lastActiveDate", now)
                            .addOnSuccessListener(aVoid -> getUserData(userId, callback))
                            .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
                } else {
                    callback.onFailure("User data is null");
                }
            } else {
                callback.onFailure("User not found");
            }
        }).addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    @Override
    public void addAchievement(String userId, String achievementId, final UserDataCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        
        docRef.update("achievements", FieldValue.arrayUnion(achievementId))
                .addOnSuccessListener(aVoid -> getUserData(userId, callback))
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}
