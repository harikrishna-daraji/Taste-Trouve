package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.tastetrouverestaurantowner.R;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                sharedPreferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                boolean isLogin = sharedPreferences.getBoolean("signUpDone",false);
                Intent intent;
                if(isLogin) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent =  new Intent(SplashActivity.this, LogInActivity.class);
                }



                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 3000);
    }
}