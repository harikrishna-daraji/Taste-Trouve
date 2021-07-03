package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.tastetrouve.R;

public class LanguageActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor  editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setContentView(R.layout.activity_language);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.englishTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("code","en");
                editor.commit();
                editor.apply();

                Intent intent = new Intent(LanguageActivity.this, OnBoardingScreen.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.frenchTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("code","fr");
                editor.commit();
                editor.apply();

                Intent intent = new Intent(LanguageActivity.this, OnBoardingScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

}