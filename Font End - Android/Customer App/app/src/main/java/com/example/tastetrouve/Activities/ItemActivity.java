package com.example.tastetrouve.Activities;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.ItemRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.KidSectionModel;
import com.example.tastetrouve.Models.PopularSectionModel;
import com.example.tastetrouve.Models.SubCategoryModel;
import com.example.tastetrouve.R;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends BaseActivity {

    RecyclerView itemRecycle;
    SharedPreferences sharedPreferences;
    List<ItemProductModel> itemProductModels;
    ArrayList<PopularSectionModel> popularSectionModels;
    List<KidSectionModel> kidSectionModels;
    TextView topHeading, noResultTV;

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
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Kid.toString())) {
            topHeading.setText(GlobalObjects.ModelList.Kid.toString());
            kidSectionModels = (List) getIntent().getStringArrayListExtra(GlobalObjects.ModelList.Kid.toString());
            itemRecycle.setAdapter(new ItemRecycleAdapter(kidSectionModels,this));
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Popular.toString())) {
            topHeading.setText(GlobalObjects.ModelList.Popular.toString());
            popularSectionModels = (ArrayList) getIntent().getStringArrayListExtra(GlobalObjects.ModelList.Popular.toString());
            itemRecycle.setAdapter(new ItemRecycleAdapter(this,popularSectionModels));
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Category.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Subcategory.toString())) {
            String categoryID = getIntent().getStringExtra(GlobalObjects.ModelList.Category.toString());
            SubCategoryModel model = (SubCategoryModel) getIntent().getSerializableExtra(GlobalObjects.ModelList.Subcategory.toString());
            String restaurantID = getIntent().getStringExtra(GlobalObjects.ModelList.Restaurant.toString());
            getProductOfRestaurant(categoryID,model.get_id(),restaurantID);
            topHeading.setText(model.getName());
        }

    }

    private void initUI() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        noResultTV = findViewById(R.id.noResultTV);
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

                            if(itemProductModels.size() == 0) {
                                noResultTV.setVisibility(View.VISIBLE);
                                itemRecycle.setVisibility(View.GONE);
                            } else {
                                noResultTV.setVisibility(View.GONE);
                                itemRecycle.setVisibility(View.VISIBLE);
                            }
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


    private void getProductOfRestaurant(String categoryID, String subCategoryID, String restaurantID) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getProductOfRestaurant(restaurantID,categoryID,subCategoryID).enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            itemRecycle.setAdapter(new ItemRecycleAdapter(ItemActivity.this,itemProductModels));
                            if(itemProductModels.size() == 0) {
                                noResultTV.setVisibility(View.VISIBLE);
                                itemRecycle.setVisibility(View.GONE);
                            } else {
                                noResultTV.setVisibility(View.GONE);
                                itemRecycle.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                    Log.i("TAG","TAG "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

}