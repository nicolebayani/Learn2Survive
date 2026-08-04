package android.bignerdranch.learn2survive.ui.learning.fragments.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.ui.learning.adapters.TipAdapter;

public class DuringDisasterFragment extends Fragment {
    private LottieAnimationView animationView;
    private TextView titleTextView;
    private RecyclerView tipsRecyclerView;
    private TipAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_section_with_tips, container, false);
        
        initViews(view);
        loadContent();
        
        return view;
    }

    private void initViews(View view) {
        animationView = view.findViewById(R.id.animationView);
        titleTextView = view.findViewById(R.id.titleTextView);
        tipsRecyclerView = view.findViewById(R.id.tipsRecyclerView);
    }

    private void loadContent() {
        titleTextView.setText("During Disaster");
        
        animationView.setAnimation("during_disaster.json");
        animationView.playAnimation();

        List<String> tips = new ArrayList<>();
        tips.add("Stay calm and follow your emergency plan");
        tips.add("Drop, Cover, and Hold On");
        tips.add("Stay away from windows and glass");
        tips.add("If outdoors, move to an open area");
        tips.add("If in a vehicle, stop safely and stay inside");
        tips.add("Do not use elevators during earthquakes");
        tips.add("Stay indoors until shaking stops");
        tips.add("Check for injuries and provide first aid");

        adapter = new TipAdapter(tips);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tipsRecyclerView.setAdapter(adapter);
    }
}
