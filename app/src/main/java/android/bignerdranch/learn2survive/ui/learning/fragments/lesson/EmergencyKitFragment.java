package android.bignerdranch.learn2survive.ui.learning.fragments.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.ui.learning.adapters.EmergencyItemAdapter;

public class EmergencyKitFragment extends Fragment {
    private LottieAnimationView animationView;
    private TextView titleTextView;
    private RecyclerView itemsRecyclerView;
    private EmergencyItemAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_section_with_items, container, false);
        
        initViews(view);
        loadContent();
        
        return view;
    }

    private void initViews(View view) {
        animationView = view.findViewById(R.id.animationView);
        titleTextView = view.findViewById(R.id.titleTextView);
        itemsRecyclerView = view.findViewById(R.id.itemsRecyclerView);
    }

    private void loadContent() {
        titleTextView.setText("Emergency Kit");
        
        animationView.setAnimation("emergency_kit.json");
        animationView.playAnimation();

        List<EmergencyItem> items = new ArrayList<>();
        items.add(new EmergencyItem("Water", "1 gallon per person per day", R.drawable.ic_water));
        items.add(new EmergencyItem("Food", "Non-perishable items", R.drawable.ic_food));
        items.add(new EmergencyItem("First Aid Kit", "Bandages, antiseptic, medications", R.drawable.ic_first_aid));
        items.add(new EmergencyItem("Flashlight", "With extra batteries", R.drawable.ic_flashlight));
        items.add(new EmergencyItem("Radio", "Battery or hand-crank", R.drawable.ic_radio));
        items.add(new EmergencyItem("Whistle", "To signal for help", R.drawable.ic_whistle));
        items.add(new EmergencyItem("Dust Mask", "For air quality protection", R.drawable.ic_mask));
        items.add(new EmergencyItem("Manual Can Opener", "For canned food", R.drawable.ic_can_opener));

        adapter = new EmergencyItemAdapter(items);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        itemsRecyclerView.setAdapter(adapter);
    }

    static class EmergencyItem {
        String name;
        String description;
        int iconRes;

        EmergencyItem(String name, String description, int iconRes) {
            this.name = name;
            this.description = description;
            this.iconRes = iconRes;
        }
    }
}
