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

public class AfterDisasterFragment extends Fragment {
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
        titleTextView.setText("After Disaster");
        
        animationView.setAnimation("after_disaster.json");
        animationView.playAnimation();

        List<String> tips = new ArrayList<>();
        tips.add("Check for injuries and provide assistance");
        tips.add("Inspect your home for damage");
        tips.add("Check for gas leaks and electrical damage");
        tips.add("Stay away from damaged buildings");
        tips.add("Listen to emergency broadcasts");
        tips.add("Use text messages to communicate");
        tips.add("Document damage with photos");
        tips.add("Contact your insurance company");

        adapter = new TipAdapter(tips);
        tipsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tipsRecyclerView.setAdapter(adapter);
    }
}
