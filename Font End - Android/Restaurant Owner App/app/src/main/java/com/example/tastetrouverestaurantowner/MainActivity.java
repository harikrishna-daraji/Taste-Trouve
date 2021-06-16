package com.example.tastetrouverestaurantowner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottombar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    case R.id.pending :temp=new PendingOrdersFragment();
                    break;
                    case R.id.report :temp=new ReportFragment();
                    break;
                    case R.id.accepted :temp=new AcceptedOrderFragment();
                    break;

                }
           getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,temp).commit();
                return true;
            }
        });
    }
}