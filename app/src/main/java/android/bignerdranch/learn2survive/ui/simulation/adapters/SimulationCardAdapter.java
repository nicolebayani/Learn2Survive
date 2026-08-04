package android.bignerdranch.learn2survive.ui.simulation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.DisasterScenario;
import android.bignerdranch.learn2survive.domain.model.DisasterSimulationType;

public class SimulationCardAdapter extends RecyclerView.Adapter<SimulationCardAdapter.ViewHolder> {
    private List<DisasterScenario> scenarios;
    private OnScenarioClickListener listener;

    public interface OnScenarioClickListener {
        void onScenarioClick(DisasterScenario scenario);
    }

    public SimulationCardAdapter(OnScenarioClickListener listener) {
        this.scenarios = new ArrayList<>();
        this.listener = listener;
    }

    public void setScenarios(List<DisasterScenario> scenarios) {
        this.scenarios = scenarios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_simulation_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DisasterScenario scenario = scenarios.get(position);
        holder.bind(scenario, listener);
    }

    @Override
    public int getItemCount() {
        return scenarios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView typeImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView difficultyTextView;
        private TextView timeLimitTextView;
        private TextView rewardTextView;
        private Button startButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            typeImageView = itemView.findViewById(R.id.typeImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            difficultyTextView = itemView.findViewById(R.id.difficultyTextView);
            timeLimitTextView = itemView.findViewById(R.id.timeLimitTextView);
            rewardTextView = itemView.findViewById(R.id.rewardTextView);
            startButton = itemView.findViewById(R.id.startButton);
        }

        public void bind(DisasterScenario scenario, OnScenarioClickListener listener) {
            titleTextView.setText(scenario.getTitle());
            descriptionTextView.setText(scenario.getDescription());
            difficultyTextView.setText("Difficulty: " + getDifficultyText(scenario.getDifficulty()));
            timeLimitTextView.setText("Time: " + formatTime(scenario.getTotalTimeLimitSeconds()));
            rewardTextView.setText("XP: " + scenario.getXpReward() + " | Coins: " + scenario.getCoinReward());

            setTypeIcon(scenario.getType());

            startButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onScenarioClick(scenario);
                }
            });
        }

        private void setTypeIcon(DisasterSimulationType type) {
            int iconRes;
            switch (type) {
                case EARTHQUAKE:
                    iconRes = R.drawable.ic_earthquake;
                    break;
                case FLOOD:
                    iconRes = R.drawable.ic_flood;
                    break;
                case TYPHOON:
                    iconRes = R.drawable.ic_typhoon;
                    break;
                case FIRE:
                    iconRes = R.drawable.ic_fire;
                    break;
                default:
                    iconRes = R.drawable.ic_disaster;
            }
            typeImageView.setImageResource(iconRes);
        }

        private String getDifficultyText(int difficulty) {
            switch (difficulty) {
                case 1: return "Easy";
                case 2: return "Medium";
                case 3: return "Hard";
                default: return "Unknown";
            }
        }

        private String formatTime(int seconds) {
            int minutes = seconds / 60;
            int secs = seconds % 60;
            return String.format("%d:%02d", minutes, secs);
        }
    }
}
