package com.example.tastetrouve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ItemActivity extends BaseActivity {

    RecyclerView itemRecycle;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(true));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_item);
        initUI();
    }

    private void initUI() {
        itemRecycle = findViewById(R.id.itemRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        itemRecycle.setLayoutManager(gridLayoutManager);
        itemRecycle.setAdapter(new ItemRecycleAdapter(this));
    }

}