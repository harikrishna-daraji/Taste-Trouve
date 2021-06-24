package com.example.tastetrouve.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tastetrouve.R;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    public int loadStyle(boolean actionBar){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("STYLE", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("theme", "light").equals("dark")) {
            if (actionBar) {
                return R.style.DarkThemeActionBar;
            } else {
                return R.style.DarkTheme;
            }
        } else{
            if (actionBar) {
                return R.style.LightThemeActionBar;
            } else {
                return R.style.LightTheme;
            }
        }
    }

    public void setLanguage(String codeString){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();
        // String code =sharedPreferences.getString("code","en");
        Locale locale = new Locale(codeString);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

}
