package android.bignerdranch.learn2survive.ui.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.GameType;
import android.bignerdranch.learn2survive.ui.games.adapters.GameCardAdapter;
import android.bignerdranch.learn2survive.ui.games.models.GameInfo;

public class GamesListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;

    private GameCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        initViews();
        loadGames();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        emptyTextView = findViewById(R.id.emptyTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new GameCardAdapter(gameInfo -> {
            launchGame(gameInfo.getGameType());
        });
        recyclerView.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Mini Games");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadGames() {
        progressBar.setVisibility(View.VISIBLE);

        List<GameInfo> games = createGameList();
        
        progressBar.setVisibility(View.GONE);
        
        if (games.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setGames(games);
        }
    }

    private List<GameInfo> createGameList() {
        List<GameInfo> games = new ArrayList<>();

        games.add(new GameInfo(
            GameType.EMERGENCY_KIT_BUILDER,
            "Emergency Kit Builder",
            "Drag emergency items into your backpack to build the perfect survival kit.",
            "Build your emergency kit by selecting the right supplies",
            android.R.drawable.ic_menu_agenda,
            "#FF5722"
        ));

        games.add(new GameInfo(
            GameType.EVACUATION_MAZE,
            "Evacuation Maze",
            "Navigate safely through obstacles to reach the evacuation area.",
            "Find your way to safety through the maze",
            android.R.drawable.ic_menu_compass,
            "#4CAF50"
        ));

        games.add(new GameInfo(
            GameType.FIRE_ESCAPE,
            "Fire Escape",
            "Find the safest exit route during a fire emergency.",
            "Escape the fire safely by choosing the right path",
            android.R.drawable.ic_menu_directions,
            "#F44336"
        ));

        games.add(new GameInfo(
            GameType.FLOOD_RESCUE,
            "Flood Rescue",
            "Move people to higher ground before the flood waters rise.",
            "Rescue people from rising flood waters",
            android.R.drawable.ic_menu_gallery,
            "#2196F3"
        ));

        games.add(new GameInfo(
            GameType.TYPHOON_HOUSE_DEFENDER,
            "Typhoon House Defender",
            "Secure your house before the typhoon strikes.",
            "Protect your home from typhoon damage",
            android.R.drawable.ic_menu_myplaces,
            "#9C27B0"
        ));

        games.add(new GameInfo(
            GameType.EARTHQUAKE_SAFE_SPOT,
            "Earthquake Safe Spot",
            "Tap safe locations before time runs out during an earthquake.",
            "Find safe spots during an earthquake",
            android.R.drawable.ic_menu_mapmode,
            "#FF9800"
        ));

        games.add(new GameInfo(
            GameType.MEMORY_MATCH,
            "Memory Match",
            "Match emergency supplies to test your memory.",
            "Match pairs of emergency items",
            android.R.drawable.ic_menu_sort_alphabetically,
            "#00BCD4"
        ));

        return games;
    }

    private void launchGame(GameType gameType) {
        Intent intent = null;
        
        switch (gameType) {
            case EMERGENCY_KIT_BUILDER:
                intent = new Intent(this, EmergencyKitBuilderActivity.class);
                break;
            case EVACUATION_MAZE:
                intent = new Intent(this, EvacuationMazeActivity.class);
                break;
            case FIRE_ESCAPE:
                intent = new Intent(this, FireEscapeActivity.class);
                break;
            case FLOOD_RESCUE:
                intent = new Intent(this, FloodRescueActivity.class);
                break;
            case TYPHOON_HOUSE_DEFENDER:
                intent = new Intent(this, TyphoonHouseDefenderActivity.class);
                break;
            case EARTHQUAKE_SAFE_SPOT:
                intent = new Intent(this, EarthquakeSafeSpotActivity.class);
                break;
            case MEMORY_MATCH:
                intent = new Intent(this, MemoryMatchActivity.class);
                break;
        }
        
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}