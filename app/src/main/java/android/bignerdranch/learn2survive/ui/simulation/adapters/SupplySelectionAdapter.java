package android.bignerdranch.learn2survive.ui.simulation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.EmergencySupply;

public class SupplySelectionAdapter extends RecyclerView.Adapter<SupplySelectionAdapter.ViewHolder> {
    private List<EmergencySupply> availableSupplies;
    private List<EmergencySupply> selectedSupplies;
    private int maxSuppliesAllowed;
    private OnSupplySelectionListener listener;

    public interface OnSupplySelectionListener {
        void onSupplySelectionChanged();
    }

    public SupplySelectionAdapter(List<EmergencySupply> availableSupplies, List<EmergencySupply> selectedSupplies, int maxSuppliesAllowed) {
        this.availableSupplies = availableSupplies;
        this.selectedSupplies = selectedSupplies;
        this.maxSuppliesAllowed = maxSuppliesAllowed;
    }

    public void setOnSupplySelectionListener(OnSupplySelectionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_supply_selection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmergencySupply supply = availableSupplies.get(position);
        holder.bind(supply);
    }

    @Override
    public int getItemCount() {
        return availableSupplies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView supplyImageView;
        private TextView supplyNameTextView;
        private TextView supplyDescriptionTextView;
        private TextView supplyCategoryTextView;
        private CheckBox supplyCheckBox;
        private TextView importanceTextView;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            supplyImageView = itemView.findViewById(R.id.supplyImageView);
            supplyNameTextView = itemView.findViewById(R.id.supplyNameTextView);
            supplyDescriptionTextView = itemView.findViewById(R.id.supplyDescriptionTextView);
            supplyCategoryTextView = itemView.findViewById(R.id.supplyCategoryTextView);
            supplyCheckBox = itemView.findViewById(R.id.supplyCheckBox);
            importanceTextView = itemView.findViewById(R.id.importanceTextView);
        }

        void bind(EmergencySupply supply) {
            supplyNameTextView.setText(supply.getName());
            supplyDescriptionTextView.setText(supply.getDescription());
            supplyCategoryTextView.setText(supply.getCategory());
            importanceTextView.setText("Importance: " + supply.getImportance() + "/10");

            // Set item category and color based on importance
            if (supply.isEssential()) {
                supplyCategoryTextView.setText(supply.getCategory() + " (ESSENTIAL)");
                supplyCategoryTextView.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
                cardView.setCardBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.holo_orange_light));
            } else if (supply.getImportance() < 0) {
                supplyCategoryTextView.setText(supply.getCategory() + " (UNNECESSARY)");
                supplyCategoryTextView.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
                cardView.setCardBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.darker_gray));
            } else {
                supplyCategoryTextView.setText(supply.getCategory() + " (USEFUL)");
                supplyCategoryTextView.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
                cardView.setCardBackgroundColor(itemView.getContext().getResources().getColor(android.R.color.white));
            }

            // Load icon if available
            if (supply.getIconUrl() != null && !supply.getIconUrl().isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(supply.getIconUrl())
                        .into(supplyImageView);
            } else {
                // Set default icon based on category
                int defaultIcon = getDefaultIconForCategory(supply.getCategory());
                supplyImageView.setImageResource(defaultIcon);
            }

            // Check if this supply is selected
            boolean isSelected = selectedSupplies.contains(supply);
            supplyCheckBox.setChecked(isSelected);

            // Visual feedback for selected items
            if (isSelected) {
                itemView.setAlpha(1.0f);
                cardView.setCardElevation(8);
            } else {
                itemView.setAlpha(0.7f);
                cardView.setCardElevation(2);
            }

            // Disable checkbox if max supplies reached and this item is not selected
            boolean canSelect = selectedSupplies.size() < maxSuppliesAllowed || isSelected;
            supplyCheckBox.setEnabled(canSelect);

            supplyCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    if (selectedSupplies.size() < maxSuppliesAllowed) {
                        selectedSupplies.add(supply);
                    } else {
                        buttonView.setChecked(false);
                        return;
                    }
                } else {
                    selectedSupplies.remove(supply);
                }
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onSupplySelectionChanged();
                }
            });
        }

        private int getDefaultIconForCategory(String category) {
            switch (category) {
                case "Lighting": return android.R.drawable.ic_menu_call;
                case "Medical": return android.R.drawable.ic_menu_info_details;
                case "Food & Water": return android.R.drawable.ic_menu_gallery;
                case "Signaling": return android.R.drawable.ic_menu_edit;
                case "Protection": return android.R.drawable.ic_menu_camera;
                case "Communication": return android.R.drawable.ic_menu_send;
                case "Shelter": return android.R.drawable.ic_menu_mylocation;
                case "Storage": return android.R.drawable.ic_menu_save;
                case "Clothing": return android.R.drawable.ic_menu_view;
                case "Documents": return android.R.drawable.ic_menu_agenda;
                case "Electronics": return android.R.drawable.ic_menu_manage;
                case "Personal": return android.R.drawable.ic_menu_my_calendar;
                case "Outdoor": return android.R.drawable.ic_menu_mapmode;
                case "Household": return android.R.drawable.ic_menu_day;
                case "Safety": return android.R.drawable.ic_menu_revert;
                default: return android.R.drawable.ic_menu_help;
            }
        }
    }
}
