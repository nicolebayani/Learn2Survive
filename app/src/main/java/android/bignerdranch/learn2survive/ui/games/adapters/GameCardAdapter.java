package android.bignerdranch.learn2survive.ui.games.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.ui.games.models.GameInfo;

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.GameViewHolder> {
    private List<GameInfo> games = new ArrayList<>();
    private OnGameClickListener listener;

    public interface OnGameClickListener {
        void onGameClick(GameInfo gameInfo);
    }

    public GameCardAdapter(OnGameClickListener listener) {
        this.listener = listener;
    }

    public void setGames(List<GameInfo> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_card, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        GameInfo game = games.get(position);
        holder.bind(game, listener);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        private ImageView gameIcon;
        private TextView gameTitle;
        private TextView gameDescription;
        private TextView gameInstruction;
        private View cardContainer;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameIcon = itemView.findViewById(R.id.gameIcon);
            gameTitle = itemView.findViewById(R.id.gameTitle);
            gameDescription = itemView.findViewById(R.id.gameDescription);
            gameInstruction = itemView.findViewById(R.id.gameInstruction);
            cardContainer = itemView.findViewById(R.id.cardContainer);
        }

        public void bind(GameInfo game, OnGameClickListener listener) {
            gameIcon.setImageResource(game.getIconResId());
            gameTitle.setText(game.getTitle());
            gameDescription.setText(game.getDescription());
            gameInstruction.setText(game.getInstruction());

            try {
                int color = android.graphics.Color.parseColor(game.getColorHex());
                cardContainer.setBackgroundColor(color);
            } catch (Exception e) {
                cardContainer.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.primary));
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onGameClick(game);
                }
            });
        }
    }
}