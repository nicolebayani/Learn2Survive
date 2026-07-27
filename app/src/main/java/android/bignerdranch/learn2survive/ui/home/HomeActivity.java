package android.bignerdranch.learn2survive.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import android.bignerdranch.learn2survive.R;
import android.bignerdranch.learn2survive.databinding.ActivityHomeBinding;
import android.bignerdranch.learn2survive.di.DependencyProvider;
import android.bignerdranch.learn2survive.domain.model.User;
import android.bignerdranch.learn2survive.ui.auth.AuthViewModel;
import android.bignerdranch.learn2survive.ui.auth.LoginActivity;
import android.bignerdranch.learn2survive.ui.base.BaseActivity;
import android.bignerdranch.learn2survive.ui.profile.ProfileActivity;
import android.bignerdranch.learn2survive.ui.settings.SettingsActivity;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> {
    private AuthViewModel viewModel;
    private ActionBarDrawerToggle toggle;

    @Override
    protected ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        viewModel = DependencyProvider.provideAuthViewModel();

        setSupportActionBar(binding.toolbar);
        
        setupDrawer();
        setupBottomNavigation();
        
        // Load home fragment by default
        loadFragment(new HomeFragment());
    }

    private void setupDrawer() {
        toggle = new ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home_drawer) {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
            } else if (itemId == R.id.nav_profile_drawer) {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_profile);
            } else if (itemId == R.id.nav_settings_drawer) {
                binding.bottomNavigation.setSelectedItemId(R.id.nav_settings);
            } else if (itemId == R.id.nav_logout) {
                logout();
            }
            
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_home) {
                loadFragment(new HomeFragment());
                return true;
            } else if (itemId == R.id.nav_learn) {
                // TODO: Implement Learn fragment
                Toast.makeText(this, "Learn module coming soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;
            } else if (itemId == R.id.nav_settings) {
                loadFragment(new SettingsFragment());
                return true;
            }
            
            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void logout() {
        viewModel.logout();
    }

    @Override
    protected void observeData() {
        viewModel.getUserLiveData().observe(this, user -> {
            if (user == null) {
                // User logged out, navigate to login
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                // Update navigation header with user info
                updateNavHeader(user);
            }
        });

        // Check current user
        viewModel.checkCurrentUser();
    }

    private void updateNavHeader(User user) {
        android.view.View headerView = binding.navigationView.getHeaderView(0);
        android.widget.TextView userNameText = headerView.findViewById(R.id.userNameText);
        android.widget.TextView userEmailText = headerView.findViewById(R.id.userEmailText);
        
        userNameText.setText(user.getFullName());
        userEmailText.setText(user.getEmail());
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toggle != null) {
            binding.drawerLayout.removeDrawerListener(toggle);
        }
    }
}
