package android.bignerdranch.learn2survive.data.repository;

import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.domain.repository.AuthRepository;
import android.bignerdranch.learn2survive.Learn2SurviveApplication;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AuthRepositoryImpl implements AuthRepository {
    private static final String TAG = "AuthRepositoryImpl";
    private static final String USERS_COLLECTION = "users";
    
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore firestore;

    public AuthRepositoryImpl() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    private boolean isNetworkAvailable() {
        return Learn2SurviveApplication.getInstance().isNetworkAvailable();
    }

    @Override
    public void login(String email, String password, final AuthCallback callback) {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No network connection available");
            callback.onFailure("No internet connection. Please check your network and try again.");
            return;
        }

        Log.d(TAG, "Attempting login for email: " + email);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase Auth login successful");
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            Log.d(TAG, "User ID: " + firebaseUser.getUid());
                            getUserFromFirestore(firebaseUser.getUid(), email, firebaseUser.getDisplayName(), callback);
                        } else {
                            Log.e(TAG, "Firebase user is null after successful login");
                            callback.onFailure("User not found");
                        }
                    } else {
                        Exception exception = task.getException();
                        String errorMessage = exception != null ? exception.getMessage() : "Login failed";
                        Log.e(TAG, "Firebase Auth login failed: " + errorMessage, exception);
                        callback.onFailure(errorMessage);
                    }
                });
    }

    @Override
    public void register(String email, String password, String fullName, final AuthCallback callback) {
        if (!isNetworkAvailable()) {
            Log.e(TAG, "No network connection available");
            callback.onFailure("No internet connection. Please check your network and try again.");
            return;
        }

        Log.d(TAG, "Attempting registration for email: " + email + ", name: " + fullName);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase Auth registration successful");
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            Log.d(TAG, "User ID: " + firebaseUser.getUid());
                            saveUserToFirestore(firebaseUser.getUid(), email, fullName, callback);
                        } else {
                            Log.e(TAG, "Firebase user is null after successful registration");
                            callback.onFailure("Registration failed");
                        }
                    } else {
                        Exception exception = task.getException();
                        String errorMessage = exception != null ? exception.getMessage() : "Registration failed";
                        Log.e(TAG, "Firebase Auth registration failed: " + errorMessage, exception);
                        callback.onFailure(errorMessage);
                    }
                });
    }

    @Override
    public void forgotPassword(String email, final AuthCallback callback) {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(null);
                    } else {
                        String errorMessage = task.getException() != null 
                                ? task.getException().getMessage() 
                                : "Failed to send reset email";
                        callback.onFailure(errorMessage);
                    }
                });
    }

    @Override
    public void logout(final AuthCallback callback) {
        firebaseAuth.signOut();
        callback.onSuccess(null);
    }

    @Override
    public void getCurrentUser(final AuthCallback callback) {
        try {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                getUserFromFirestore(firebaseUser.getUid(), firebaseUser.getEmail(), firebaseUser.getDisplayName(), callback);
            } else {
                callback.onFailure("No user logged in");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting current user", e);
            callback.onFailure("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public void deleteAccount(final AuthCallback callback) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            callback.onFailure("No user logged in");
            return;
        }

        // First delete from Firestore
        firestore.collection(USERS_COLLECTION).document(firebaseUser.getUid())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    // Then delete from Firebase Auth
                    firebaseUser.delete()
                            .addOnSuccessListener(aVoid2 -> {
                                callback.onSuccess(null);
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Error deleting user from Auth", e);
                                callback.onFailure("Failed to delete account: " + e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting user from Firestore", e);
                    callback.onFailure("Failed to delete user data: " + e.getMessage());
                });
    }

    private void saveUserToFirestore(String userId, String email, String fullName, final AuthCallback callback) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);
        userMap.put("email", email);
        userMap.put("fullName", fullName);
        userMap.put("profileImageUrl", "");
        userMap.put("createdAt", System.currentTimeMillis());
        
        // Add game-related fields with default values
        userMap.put("level", 1);
        userMap.put("currentXP", 0);
        userMap.put("maxXP", 100);
        userMap.put("coins", 0);
        userMap.put("dailyStreak", 0);
        userMap.put("lastActiveDate", System.currentTimeMillis());
        userMap.put("achievements", new ArrayList<>());
        userMap.put("statistics", new HashMap<>());
        userMap.put("completedLessons", 0);
        userMap.put("completedQuizzes", 0);

        firestore.collection(USERS_COLLECTION)
                .document(userId)
                .set(userMap)
                .addOnSuccessListener(aVoid -> {
                    User user = new User(userId, email, fullName, "", System.currentTimeMillis());
                    callback.onSuccess(user);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error saving user to Firestore", e);
                    callback.onFailure("Failed to save user data: " + e.getMessage());
                });
    }

    private void getUserFromFirestore(String userId, String email, String displayName, final AuthCallback callback) {
        DocumentReference docRef = firestore.collection(USERS_COLLECTION).document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    User user = document.toObject(User.class);
                    callback.onSuccess(user);
                } else {
                    // User exists in Firebase Auth but not in Firestore, create Firestore document
                    Log.d(TAG, "User not found in Firestore, creating document");
                    String fullName = displayName != null ? displayName : email.split("@")[0];
                    saveUserToFirestore(userId, email, fullName, callback);
                }
            } else {
                String errorMessage = task.getException() != null 
                        ? task.getException().getMessage() 
                        : "Failed to fetch user data";
                callback.onFailure(errorMessage);
            }
        });
    }
}
