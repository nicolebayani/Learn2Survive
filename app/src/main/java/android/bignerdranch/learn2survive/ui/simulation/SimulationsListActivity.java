package android.bignerdranch.learn2survive.ui.simulation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.domain.model.DisasterScenario;
import android.bignerdranch.learn2survive.domain.repository.SimulationRepository;
import android.bignerdranch.learn2survive.data.remote.SimulationRepositoryImpl;
import android.bignerdranch.learn2survive.ui.simulation.adapters.SimulationCardAdapter;

public class SimulationsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;

    private SimulationRepository simulationRepository;
    private SimulationCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulations_list);

        simulationRepository = new SimulationRepositoryImpl();

        initViews();
        loadSimulations();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        emptyTextView = findViewById(R.id.emptyTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SimulationCardAdapter(scenario -> {
            Intent intent = new Intent(this, SimulationActivity.class);
            intent.putExtra(SimulationActivity.EXTRA_SCENARIO_ID, scenario.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Disaster Simulations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void loadSimulations() {
        progressBar.setVisibility(View.VISIBLE);

        simulationRepository.getAllScenarios(new SimulationRepository.ScenariosCallback() {
            @Override
            public void onSuccess(List<DisasterScenario> scenarios) {
                progressBar.setVisibility(View.GONE);
                
                if (scenarios.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setScenarios(scenarios);
                }
            }

            @Override
            public void onFailure(Exception e) {
                progressBar.setVisibility(View.GONE);
                emptyTextView.setVisibility(View.VISIBLE);
                emptyTextView.setText("Failed to load simulations");
            }
        });
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
