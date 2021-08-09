package com.example.tastetrouve.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tastetrouve.R;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {

    public int loadStyle(boolean actionBar){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("theme", "light");
        if (theme.equals("dark")) {
            Log.i("TAG","TAG: Dark theme");
            if (actionBar) {
                return R.style.DarkThemeActionBar;
            } else {
                return R.style.DarkTheme;
            }
        } else{
            Log.i("TAG","TAG: Light theme");
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
