package android.bignerdranch.learn2survive.ui.gamification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.Badge;
import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    private List<Badge> badges;
    private PlayerGamificationData playerData;

    public BadgeAdapter(List<Badge> badges, PlayerGamificationData playerData) {
        this.badges = badges;
        this.playerData = playerData;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Badge badge = badges.get(position);
        holder.bind(badge, playerData);
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    static class BadgeViewHolder extends RecyclerView.ViewHolder {
        private ImageView badgeIcon;
        private TextView badgeName;
        private TextView badgeDescription;
        private TextView badgeRarity;
        private View lockedOverlay;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeIcon = itemView.findViewById(R.id.badgeIcon);
            badgeName = itemView.findViewById(R.id.badgeName);
            badgeDescription = itemView.findViewById(R.id.badgeDescription);
            badgeRarity = itemView.findViewById(R.id.badgeRarity);
            lockedOverlay = itemView.findViewById(R.id.lockedOverlay);
        }

        public void bind(Badge badge, PlayerGamificationData playerData) {
            badgeName.setText(badge.getName());
            badgeDescription.setText(badge.getDescription());
            
            // Set rarity text and color
            String rarityText = badge.getRarity().toString();
            badgeRarity.setText(rarityText);
            
            int rarityColor;
            switch (badge.getRarity()) {
                case COMMON:
                    rarityColor = R.color.text_secondary;
                    break;
                case RARE:
                    rarityColor = R.color.info;
                    break;
                case EPIC:
                    rarityColor = R.color.accent;
                    break;
                case LEGENDARY:
                    rarityColor = R.color.warning;
                    break;
                default:
                    rarityColor = R.color.text_secondary;
            }
            badgeRarity.setTextColor(itemView.getContext().getResources().getColor(rarityColor));
            
            // Set icon (in real app, load from resource ID)
            badgeIcon.setImageResource(android.R.drawable.ic_menu_star);
            
            // Check if unlocked
            boolean isUnlocked = false;
            if (playerData != null && playerData.getUnlockedBadges() != null) {
                isUnlocked = playerData.getUnlockedBadges().contains(badge.getId());
            }
            
            if (isUnlocked) {
                lockedOverlay.setVisibility(View.GONE);
                badgeIcon.setAlpha(1f);
            } else {
                lockedOverlay.setVisibility(View.VISIBLE);
                badgeIcon.setAlpha(0.5f);
            }
        }
    }
}