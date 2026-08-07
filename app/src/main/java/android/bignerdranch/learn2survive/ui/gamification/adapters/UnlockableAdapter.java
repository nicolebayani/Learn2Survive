package android.bignerdranch.learn2survive.ui.gamification.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.PlayerGamificationData;
import android.bignerdranch.learn2survive.domain.model.UnlockableItem;

public class UnlockableAdapter extends RecyclerView.Adapter<UnlockableAdapter.UnlockableViewHolder> {
    private List<UnlockableItem> items;
    private PlayerGamificationData playerData;
    private Consumer<UnlockableItem> onUnlockListener;

    public UnlockableAdapter(List<UnlockableItem> items, PlayerGamificationData playerData, 
                           Consumer<UnlockableItem> onUnlockListener) {
        this.items = items;
        this.playerData = playerData;
        this.onUnlockListener = onUnlockListener;
    }

    @NonNull
    @Override
    public UnlockableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unlockable, parent, false);
        return new UnlockableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnlockableViewHolder holder, int position) {
        UnlockableItem item = items.get(position);
        holder.bind(item, playerData, onUnlockListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class UnlockableViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemDescription;
        private TextView itemCost;
        private TextView itemLevel;
        private Button unlockButton;
        private View lockedOverlay;

        public UnlockableViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIcon = itemView.findViewById(R.id.itemIcon);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemCost = itemView.findViewById(R.id.itemCost);
            itemLevel = itemView.findViewById(R.id.itemLevel);
            unlockButton = itemView.findViewById(R.id.unlockButton);
            lockedOverlay = itemView.findViewById(R.id.lockedOverlay);
        }

        public void bind(UnlockableItem item, PlayerGamificationData playerData, 
                       Consumer<UnlockableItem> onUnlockListener) {
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemCost.setText(String.format("%d Coins", item.getCost()));
            itemLevel.setText(String.format("Level %d", item.getRequiredLevel()));
            
            // Set icon (in real app, load from resource ID)
            itemIcon.setImageResource(android.R.drawable.ic_menu_agenda);
            
            // Check if unlocked
            boolean isUnlocked = false;
            if (playerData != null && playerData.getUnlockedItems() != null) {
                isUnlocked = playerData.getUnlockedItems().contains(item.getId());
            }
            
            // Check if can unlock
            boolean canUnlock = false;
            if (playerData != null) {
                canUnlock = playerData.getCurrentLevel() >= item.getRequiredLevel() &&
                           playerData.getCoins() >= item.getCost();
            }
            
            if (isUnlocked) {
                lockedOverlay.setVisibility(View.GONE);
                unlockButton.setText("Owned");
                unlockButton.setEnabled(false);
                unlockButton.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.success));
            } else if (canUnlock) {
                lockedOverlay.setVisibility(View.GONE);
                unlockButton.setText("Unlock");
                unlockButton.setEnabled(true);
                unlockButton.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.primary));
                unlockButton.setOnClickListener(v -> {
                    if (onUnlockListener != null) {
                        onUnlockListener.accept(item);
                    }
                });
            } else {
                lockedOverlay.setVisibility(View.VISIBLE);
                unlockButton.setText("Locked");
                unlockButton.setEnabled(false);
                unlockButton.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.text_hint));
            }
        }
    }
}