package com.example.tastetrouve.Activities;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.ItemRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends BaseActivity {

    RecyclerView itemRecycle;
    SharedPreferences sharedPreferences;
    List<ItemProductModel> itemProductModels;
    TextView topHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_item);
        initUI();
        manageIntent();
    }

    private void manageIntent() {
        if(getIntent().hasExtra("section") && getIntent().hasExtra("categoryId")) {
            topHeading.setText(getIntent().getStringExtra("section"));
            getProductsOfMainCategory(getIntent().getStringExtra("categoryId"));
        }

    }

    private void initUI() {
        topHeading = findViewById(R.id.topHeading);
        itemRecycle = findViewById(R.id.itemRecycle);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        itemRecycle.setLayoutManager(gridLayoutManager);
    }

    private void getProductsOfMainCategory(String categoryId) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getProductsOfMainCategory(categoryId).enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            itemRecycle.setAdapter(new ItemRecycleAdapter(ItemActivity.this,itemProductModels));
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure: "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }


}