package android.bignerdranch.learn2survive.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.domain.repository.AuthRepository;

public class AuthViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> successMessageLiveData = new MutableLiveData<>();

    public AuthViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<String> getSuccessMessageLiveData() {
        return successMessageLiveData;
    }

    public void login(String email, String password) {
        loadingLiveData.setValue(true);
        authRepository.login(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(user);
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingLiveData.setValue(false);
                // Provide user-friendly error messages
                String userFriendlyMessage = errorMessage;
                if (errorMessage != null) {
                    String lowerError = errorMessage.toLowerCase();
                    if (lowerError.contains("invalid credential") ||
                        lowerError.contains("wrong password") ||
                        lowerError.contains("the password is invalid")) {
                        userFriendlyMessage = "Invalid email or password. Please try again.";
                    } else if (lowerError.contains("user not found") ||
                               lowerError.contains("there is no user record")) {
                        userFriendlyMessage = "No account found with this email. Please register first.";
                    } else if (lowerError.contains("network") ||
                               lowerError.contains("internet")) {
                        userFriendlyMessage = "Network error. Please check your internet connection.";
                    } else if (lowerError.contains("too many requests")) {
                        userFriendlyMessage = "Too many attempts. Please try again later.";
                    } else if (lowerError.contains("disabled")) {
                        userFriendlyMessage = "This account has been disabled.";
                    }
                }
                errorLiveData.setValue(userFriendlyMessage);
            }
        });
    }

    public void register(String email, String password, String fullName) {
        loadingLiveData.setValue(true);
        authRepository.register(email, password, fullName, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(user);
                successMessageLiveData.setValue("Registration successful");
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingLiveData.setValue(false);
                // Provide user-friendly error messages
                String userFriendlyMessage = errorMessage;
                if (errorMessage != null) {
                    String lowerError = errorMessage.toLowerCase();
                    if (lowerError.contains("email already in use") ||
                        lowerError.contains("already exists") ||
                        lowerError.contains("email address is already in use")) {
                        userFriendlyMessage = "This email is already registered. Please login instead.";
                    } else if (lowerError.contains("weak password") ||
                               lowerError.contains("password should be at least")) {
                        userFriendlyMessage = "Password is too weak. Please use at least 6 characters.";
                    } else if (lowerError.contains("invalid email") ||
                               lowerError.contains("malformed")) {
                        userFriendlyMessage = "Invalid email address format.";
                    } else if (lowerError.contains("network") ||
                               lowerError.contains("internet")) {
                        userFriendlyMessage = "Network error. Please check your internet connection.";
                    } else if (lowerError.contains("too many requests")) {
                        userFriendlyMessage = "Too many attempts. Please try again later.";
                    }
                }
                errorLiveData.setValue(userFriendlyMessage);
            }
        });
    }

    public void forgotPassword(String email) {
        loadingLiveData.setValue(true);
        authRepository.forgotPassword(email, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                loadingLiveData.setValue(false);
                successMessageLiveData.setValue("Password reset email sent");
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    public void logout() {
        loadingLiveData.setValue(true);
        authRepository.logout(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(null);
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    public void deleteAccount() {
        loadingLiveData.setValue(true);
        authRepository.deleteAccount(new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                loadingLiveData.setValue(false);
                userLiveData.setValue(null);
                successMessageLiveData.setValue("Account deleted successfully");
            }

            @Override
            public void onFailure(String errorMessage) {
                loadingLiveData.setValue(false);
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    public void checkCurrentUser() {
        if (authRepository.isLoggedIn()) {
            authRepository.getCurrentUser(new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    userLiveData.setValue(user);
                }

                @Override
                public void onFailure(String errorMessage) {
                    errorLiveData.setValue(errorMessage);
                }
            });
        }
    }

    public boolean validateEmail(String email) {
        return email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validatePassword(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean validatePasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}
