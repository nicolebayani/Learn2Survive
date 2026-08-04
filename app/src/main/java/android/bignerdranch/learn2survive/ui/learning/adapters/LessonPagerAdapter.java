package android.bignerdranch.learn2survive.ui.learning.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.IntroductionFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.BeforeDisasterFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.DuringDisasterFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.AfterDisasterFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.EmergencyKitFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.EvacuationTipsFragment;
import android.bignerdranch.learn2survive.ui.learning.fragments.lesson.SafetyTipsFragment;

public class LessonPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 7;

    public LessonPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new IntroductionFragment();
            case 1:
                return new BeforeDisasterFragment();
            case 2:
                return new DuringDisasterFragment();
            case 3:
                return new AfterDisasterFragment();
            case 4:
                return new EmergencyKitFragment();
            case 5:
                return new EvacuationTipsFragment();
            case 6:
                return new SafetyTipsFragment();
            default:
                return new IntroductionFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
