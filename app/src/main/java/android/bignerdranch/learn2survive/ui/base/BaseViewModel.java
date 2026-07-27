package android.bignerdranch.learn2survive.ui.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BaseViewModel extends ViewModel {
    
    public static class Factory implements ViewModelProvider.Factory {
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            try {
                return modelClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
    }
}
