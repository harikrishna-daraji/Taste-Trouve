package com.example.tastetrouve.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Adapters.KeywordRecycleAdapter;
import com.example.tastetrouve.HelperClass.AbsolutefitLayourManager;
import com.example.tastetrouve.Adapters.KidMenuRecycleAdapter;
import com.example.tastetrouve.Adapters.RestaurantRecycleAdapter;
import com.example.tastetrouve.Adapters.TopSellingRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity  {

    RecyclerView topSellingRecycle, kidMenuRecycle, restaurantRecycle, keywordRecycle;
    SharedPreferences sharedPreferences;
    HomeProductModel homeProductModel;
    List<ItemProductModel> itemProductModels;
    List<ItemProductModel> filteredItemProductModel = new ArrayList<>();
    BlurView blurView;
    RelativeLayout blurRelative;
    EditText searchEditText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_home);
        initUI();
        getHomeProducts();
        getAllProducts();
        blurBackground();
    }

    private void initUI() {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
//        bottomNavigationView.setBackground(null);
//        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        blurRelative = findViewById(R.id.blurRelative);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filteredItemProductModel.clear();
                if(!s.toString().isEmpty()) {
                    for(ItemProductModel model: itemProductModels) {
                        if(model.getName().toLowerCase().contains(s.toString().toLowerCase())){
                            filteredItemProductModel.add(model);
                        }
                    }
                }
                keywordRecycle.setAdapter(new KeywordRecycleAdapter(HomeActivity.this,filteredItemProductModel));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        findViewById(R.id.searchLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blurRelative.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.closeImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blurRelative.setVisibility(View.GONE);
                filteredItemProductModel.clear();
                searchEditText.setText("");
                searchEditText.clearFocus();
                keywordRecycle.setAdapter(new KeywordRecycleAdapter(HomeActivity.this,filteredItemProductModel));
            }
        });
        blurView = findViewById(R.id.blurLayout);
        keywordRecycle = findViewById(R.id.keywordRecycle);
        keywordRecycle.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.appetizerLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ItemActivity.class);
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
                    if(model.getName().equals("Dessert")) {
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
                intent.putStringArrayListExtra(GlobalObjects.ModelList.Kid.toString(),(ArrayList)homeProductModel.getKidsSection());
                startActivity(intent);
            }
        });

        findViewById(R.id.viewAllTopSellingTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ItemActivity.class);
                intent.putStringArrayListExtra(GlobalObjects.ModelList.Popular.toString(),(ArrayList)homeProductModel.getPopular());
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
    }

    private void blurBackground() {
        float radius = 22f;

        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
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
                            restaurantRecycle.setAdapter(new RestaurantRecycleAdapter(HomeActivity.this,homeProductModel.getRestaurants(),homeProductModel.getCategoryObject()));
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

    private void getAllProducts() {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getAllProducts().enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            Log.i("TAG","TAG: All Product Size: "+itemProductModels.size());
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                    Log.i("TAG","TAG: "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

}