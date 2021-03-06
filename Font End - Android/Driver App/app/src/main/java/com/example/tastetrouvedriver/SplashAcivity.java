package com.example.tastetrouvedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class SplashAcivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_acivity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                String token = sharedPreferences.getString("ownerId","");
                if(!token.isEmpty()) {
                    Intent intent = new Intent(SplashAcivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SplashAcivity.this,SIgnInActivity.class));
                }
                finish();
            }
        },SPLASH_DISPLAY_LENGTH);
    }
}