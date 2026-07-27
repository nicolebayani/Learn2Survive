package android.bignerdranch.learn2survive.di;

import android.bignerdranch.learn2survive.data.repository.AuthRepositoryImpl;
import android.bignerdranch.learn2survive.domain.repository.AuthRepository;
import android.bignerdranch.learn2survive.ui.auth.AuthViewModel;

public class DependencyProvider {
    private static AuthRepository authRepository;

    public static AuthRepository provideAuthRepository() {
        if (authRepository == null) {
            authRepository = new AuthRepositoryImpl();
        }
        return authRepository;
    }

    public static AuthViewModel provideAuthViewModel() {
        return new AuthViewModel(provideAuthRepository());
    }
}
