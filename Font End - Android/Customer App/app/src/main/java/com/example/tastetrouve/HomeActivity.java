package com.example.tastetrouve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Adapters.KidMenuRecycleAdapter;
import com.example.tastetrouve.Adapters.RestaurantRecycleAdapter;
import com.example.tastetrouve.Adapters.TopSellingRecycleAdapter;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.HomeProductModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    RecyclerView topSellingRecycle, kidMenuRecycle, restaurantRecycle;
    SharedPreferences sharedPreferences;
    HomeProductModel homeProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(true));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_home);
        initUI();
        getHomeProducts();
    }

    private void initUI() {
        findViewById(R.id.appetizerLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.appetizer.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Appetizers")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });


        findViewById(R.id.main_courseLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.main_course.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Main Course")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        findViewById(R.id.dessertLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.dessert.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Drinks")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        findViewById(R.id.viewAllKidTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.viewAllTopSellingTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                startActivity(intent);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        topSellingRecycle = findViewById(R.id.topSellingRecycle);
        topSellingRecycle.setLayoutManager(gridLayoutManager);


        AbsolutefitLayourManager absolutefitLayourManager = new AbsolutefitLayourManager(this,1,GridLayoutManager.HORIZONTAL,false);
        kidMenuRecycle = findViewById(R.id.kidsMenuRecycle);
        kidMenuRecycle.setLayoutManager(absolutefitLayourManager);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        restaurantRecycle = findViewById(R.id.restaurantRecycle);
        restaurantRecycle.setLayoutManager(linearLayoutManager);
        restaurantRecycle.setAdapter(new RestaurantRecycleAdapter(this));
    }

    private void getHomeProducts() {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getHomeProduct().enqueue(new Callback<HomeProductModel>() {
                @Override
                public void onResponse(Call<HomeProductModel> call, Response<HomeProductModel> response) {
                    try {
                        if(response.code() == 200) {
                            homeProductModel = response.body();
                            topSellingRecycle.setAdapter(new TopSellingRecycleAdapter(HomeActivity.this,homeProductModel.getPopular()));
                            kidMenuRecycle.setAdapter(new KidMenuRecycleAdapter(HomeActivity.this,homeProductModel.getKidsSection()));
                        } else {
                            Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<HomeProductModel> call, Throwable t) {
                    Log.i("TAG","TAG: Server failure: "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

}