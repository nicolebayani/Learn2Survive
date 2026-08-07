package android.bignerdranch.learn2survive.ui.gamification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Achievement;
import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {
    private List<Achievement> achievements;
    private PlayerGamificationData playerData;

    public AchievementAdapter(List<Achievement> achievements, PlayerGamificationData playerData) {
        this.achievements = achievements;
        this.playerData = playerData;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_achievement, parent, false);
        return new AchievementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        Achievement achievement = achievements.get(position);
        holder.bind(achievement, playerData);
    }

    @Override
    public int getItemCount() {
        return achievements.size();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {
        private ImageView achievementIcon;
        private TextView achievementTitle;
        private TextView achievementDescription;
        private TextView rewardText;
        private ProgressBar progressBar;
        private TextView progressText;
        private View completedOverlay;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            achievementIcon = itemView.findViewById(R.id.achievementIcon);
            achievementTitle = itemView.findViewById(R.id.achievementTitle);
            achievementDescription = itemView.findViewById(R.id.achievementDescription);
            rewardText = itemView.findViewById(R.id.rewardText);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressText = itemView.findViewById(R.id.progressText);
            completedOverlay = itemView.findViewById(R.id.completedOverlay);
        }

        public void bind(Achievement achievement, PlayerGamificationData playerData) {
            achievementTitle.setText(achievement.getTitle());
            achievementDescription.setText(achievement.getDescription());
            rewardText.setText(String.format("+%d XP  +%d Coins", achievement.getXpReward(), achievement.getCoinsReward()));
            
            // Set icon (in real app, load from resource ID)
            achievementIcon.setImageResource(android.R.drawable.ic_menu_star);
            
            // Check completion status
            boolean isCompleted = false;
            int currentProgress = 0;
            
            if (playerData != null && playerData.getAchievementProgress() != null) {
                PlayerGamificationData.AchievementProgress progress = 
                    playerData.getAchievementProgress().get(achievement.getId());
                if (progress != null) {
                    isCompleted = progress.isCompleted();
                    currentProgress = progress.getCurrentValue();
                }
            }
            
            if (isCompleted) {
                completedOverlay.setVisibility(View.VISIBLE);
                progressBar.setProgress(progressBar.getMax());
                progressText.setText("Completed!");
            } else {
                completedOverlay.setVisibility(View.GONE);
                progressBar.setMax(achievement.getTargetValue());
                progressBar.setProgress(currentProgress);
                progressText.setText(String.format("%d / %d", currentProgress, achievement.getTargetValue()));
            }
        }
    }
}