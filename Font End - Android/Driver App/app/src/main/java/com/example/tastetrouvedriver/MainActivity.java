package com.example.tastetrouvedriver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.tastetrouvedriver.Helper.Fragments.MapFragment;
import com.example.tastetrouvedriver.Helper.Fragments.PastOrderFragment;
import com.example.tastetrouvedriver.Helper.Fragments.SettingsFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    MapFragment mapFragment;
    SettingsFragment settingsFragment;
    PastOrderFragment pastOrderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = new MapFragment();
        settingsFragment = new SettingsFragment();
        pastOrderFragment = new PastOrderFragment();
        initUI();
    }

    private void initUI() {
        tabs =findViewById(R.id.tabs);
        tabs.getTabAt(1).select();

        openNavigationMenu(mapFragment);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    openNavigationMenu(pastOrderFragment);
                } else if(tab.getPosition() == 1) {
                    openNavigationMenu(mapFragment);
                } else if(tab.getPosition() == 2) {
                    openNavigationMenu(settingsFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void openNavigationMenu(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerHomeRelative, fragment).commit();
    }

}