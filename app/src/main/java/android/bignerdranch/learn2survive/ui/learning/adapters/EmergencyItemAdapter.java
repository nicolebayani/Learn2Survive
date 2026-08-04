package android.bignerdranch.learn2survive.ui.learning.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.EmergencyKitFragment;

public class EmergencyItemAdapter extends RecyclerView.Adapter<EmergencyItemAdapter.ItemViewHolder> {
    private List<EmergencyKitFragment.EmergencyItem> items;

    public EmergencyItemAdapter(List<EmergencyKitFragment.EmergencyItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emergency_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        EmergencyKitFragment.EmergencyItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconImageView;
        private TextView nameTextView;
        private TextView descriptionTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bind(EmergencyKitFragment.EmergencyItem item) {
            nameTextView.setText(item.name);
            descriptionTextView.setText(item.description);
            iconImageView.setImageResource(item.iconRes);
        }
    }
}
