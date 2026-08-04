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

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {
    private List<String> tips;

    public TipAdapter(List<String> tips) {
        this.tips = tips;
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        String tip = tips.get(position);
        holder.bind(tip, position + 1);
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        private TextView numberTextView;
        private TextView tipTextView;
        private ImageView checkIcon;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            tipTextView = itemView.findViewById(R.id.tipTextView);
            checkIcon = itemView.findViewById(R.id.checkIcon);
        }

        public void bind(String tip, int number) {
            numberTextView.setText(String.valueOf(number));
            tipTextView.setText(tip);
            checkIcon.setVisibility(View.GONE);
        }
    }
}
