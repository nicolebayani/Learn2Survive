package android.bignerdranch.learn2survive.domain.repository;

import android.bignerdranch.learn2survive.domain.model.User;

public interface AuthRepository {
    void login(String email, String password, AuthCallback callback);
    void register(String email, String password, String fullName, AuthCallback callback);
    void forgotPassword(String email, AuthCallback callback);
    void logout(AuthCallback callback);
    void getCurrentUser(AuthCallback callback);
    boolean isLoggedIn();
    
    interface AuthCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }
}
