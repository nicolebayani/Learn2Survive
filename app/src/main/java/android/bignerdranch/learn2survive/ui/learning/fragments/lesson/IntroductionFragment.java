package android.bignerdranch.learn2survive.ui.learning.fragments.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import android.bignerdranch.learn2survive.R;

public class IntroductionFragment extends Fragment {
    private LottieAnimationView animationView;
    private TextView titleTextView;
    private TextView contentTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson_section, container, false);
        
        initViews(view);
        loadContent();
        
        return view;
    }

    private void initViews(View view) {
        animationView = view.findViewById(R.id.animationView);
        titleTextView = view.findViewById(R.id.titleTextView);
        contentTextView = view.findViewById(R.id.contentTextView);
    }

    private void loadContent() {
        titleTextView.setText("Introduction");
        contentTextView.setText("Learn about disaster preparedness and survival strategies. Understanding the risks and knowing how to respond can save lives.");
        
        animationView.setAnimation("earthquake_intro.json");
        animationView.playAnimation();
    }
}
