package android.bignerdranch.learn2survive;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.learn2survive.ui.splash.SplashActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Start SplashActivity as the entry point
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
}