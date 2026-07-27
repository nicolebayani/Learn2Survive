package android.bignerdranch.learn2survive.ui.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.databinding.ItemOnboardingBinding;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {
    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOnboardingBinding binding = ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new OnboardingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        OnboardingItem item = onboardingItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private ItemOnboardingBinding binding;

        public OnboardingViewHolder(ItemOnboardingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OnboardingItem item) {
            binding.titleText.setText(item.getTitleRes());
            binding.descriptionText.setText(item.getDescriptionRes());
            
            // Set Lottie animation
            try {
                binding.lottieAnimation.setAnimation(item.getLottieAnimationName());
            } catch (Exception e) {
                // If animation file doesn't exist, use a placeholder
                binding.lottieAnimation.setAnimation("splash.json");
            }
        }
    }
}
