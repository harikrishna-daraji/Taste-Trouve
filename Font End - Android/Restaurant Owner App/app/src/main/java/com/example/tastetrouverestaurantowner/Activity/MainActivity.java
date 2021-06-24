package com.example.tastetrouverestaurantowner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.tastetrouverestaurantowner.Fragment.HomeFragment;
import com.example.tastetrouverestaurantowner.Fragment.SettingsFragment;
import com.example.tastetrouverestaurantowner.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment()).commit();

        bottombar=(BottomNavigationView)findViewById(R.id.bottomNavigation);

        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;
                switch (item.getItemId()){
                    case R.id.home :temp=new HomeFragment();
                    break;
                    case R.id.settings :temp=new SettingsFragment();
                    break;

                }
           getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,temp).commit();
                return true;
            }
        });
    }
}