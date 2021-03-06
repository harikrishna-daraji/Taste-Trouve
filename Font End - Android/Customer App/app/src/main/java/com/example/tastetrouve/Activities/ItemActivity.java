package com.example.tastetrouve.Activities;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends BaseActivity {

    RecyclerView itemRecycle;
    SharedPreferences sharedPreferences;
    ItemRecycleAdapter itemRecycleAdapter;
    List<ItemProductModel> itemProductModels;
    List<ItemProductModel> categorizedList = new ArrayList<>();
    ArrayList<PopularSectionModel> popularSectionModels;
    List<KidSectionModel> kidSectionModels;
    TextView topHeading, noResultTV;
    String price;
    String sort;
    String radio;
    boolean iskid,popular;

   // String categoryId;
    int pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        iskid = false;
        popular = false;
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        price = getIntent().getStringExtra("price");
        sort = getIntent().getStringExtra("sort");
        radio = getIntent().getStringExtra("radio");
        if(radio == "For Kids"){
            iskid = true;
        }
        if(radio == "Popular"){
            popular = true;
        }
       // categoryId = getIntent().getStringExtra("categoryId");
        setContentView(R.layout.activity_item);
        initUI();
        manageIntent();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG","TAG On Restart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG","TAG On Resume called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("TAG","TAG On Start called");
    }

    private void manageIntent() {
        if(getIntent().hasExtra("section") && getIntent().hasExtra("categoryId") && getIntent().hasExtra("price") && getIntent().hasExtra("sort") && getIntent().hasExtra("radio")){
            topHeading.setText(getIntent().getStringExtra("section"));
            getProductsOfFilter(getIntent().getStringExtra("categoryId"));
        }else if(getIntent().hasExtra("section") && getIntent().hasExtra("categoryId")) {
            topHeading.setText(getIntent().getStringExtra("section"));
            getProductsOfMainCategory(getIntent().getStringExtra("categoryId"));
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Kid.toString())) {
            topHeading.setText(GlobalObjects.ModelList.Kid.toString());
            kidSectionModels = (List) getIntent().getStringArrayListExtra(GlobalObjects.ModelList.Kid.toString());
            if(kidSectionModels.size() > 0) {
                noResultTV.setVisibility(View.GONE);
                itemRecycle.setVisibility(View.VISIBLE);
            } else {
                noResultTV.setVisibility(View.VISIBLE);
                itemRecycle.setVisibility(View.GONE);
            }
            itemRecycleAdapter = new ItemRecycleAdapter(kidSectionModels,this);
            itemRecycle.setAdapter(itemRecycleAdapter);
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Popular.toString())) {
            topHeading.setText(GlobalObjects.ModelList.Popular.toString());
            popularSectionModels = (ArrayList) getIntent().getStringArrayListExtra(GlobalObjects.ModelList.Popular.toString());
            if(popularSectionModels.size() > 0) {
                noResultTV.setVisibility(View.GONE);
                itemRecycle.setVisibility(View.VISIBLE);
            } else {
                noResultTV.setVisibility(View.VISIBLE);
                itemRecycle.setVisibility(View.GONE);
            }
            itemRecycleAdapter = new ItemRecycleAdapter(this,popularSectionModels);
            itemRecycle.setAdapter(itemRecycleAdapter);
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Category.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Subcategory.toString())) {
            String categoryID = getIntent().getStringExtra(GlobalObjects.ModelList.Category.toString());
            SubCategoryModel model = (SubCategoryModel) getIntent().getSerializableExtra(GlobalObjects.ModelList.Subcategory.toString());
            String restaurantID = getIntent().getStringExtra(GlobalObjects.ModelList.Restaurant.toString());
            getProductOfRestaurant(categoryID,model.get_id(),restaurantID);
            topHeading.setText(model.getName());
        } else if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString())) {
            topHeading.setText("Drinks");
            String restaurantID = getIntent().getStringExtra(GlobalObjects.ModelList.Restaurant.toString());
            loadDrinks(restaurantID);
        }

    }


    private void getProductsOfFilter(String categoryId) {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getProductsOfMainCategory(categoryId,token ).enqueue(new Callback<List<ItemProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                        try {
                            Log.i("TAG", "TAG: Code: " + response.code() + " Message: " + response.message());
                            if (response.code() == 200) {
                                itemProductModels = response.body();
                                pr = Integer.parseInt(price.trim());
                                categorizedList.clear();
                                for (ItemProductModel model : itemProductModels) {
                                    if (model.getCategoryId().contains(categoryId) && model.getPrice() <= pr && radio == "None") {
                                        if(sort.equals("Select...")) {
                                            categorizedList.add(model);
                                            Log.i("TAG", "onResponse: " + categorizedList.size());
                                        }
                                        if(sort.equals("Low To High")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice());
                                                }
                                            });
                                             categorizedList.add(model);
                                        }
                                        if(sort.equals("High To Low")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o2.getPrice()).compareTo(o1.getPrice());
                                                }
                                            });
                                            categorizedList.add(model);
                                        }
                                    }
                                    if (model.getCategoryId().contains(categoryId) && model.getPrice() <= pr && model.isPopular() == popular) {
                                        if(sort.equals("Select...")) {
                                            categorizedList.add(model);
                                            Log.i("TAG", "onResponse: " + categorizedList.size());
                                        }
                                        if(sort.equals("Low To High")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice());
                                                }
                                            });
                                            categorizedList.add(model);
                                        }
                                        if(sort.equals("High To Low")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o2.getPrice()).compareTo(o1.getPrice());
                                                }
                                            });
                                            categorizedList.add(model);
                                        }
                                    }
                                    if (model.getCategoryId().contains(categoryId) && model.getPrice() <= pr && model.isKidSection() == iskid) {
                                        if(sort.equals("Select...")) {
                                            categorizedList.add(model);
                                            Log.i("TAG", "onResponse: " + categorizedList.size());
                                        }
                                        if(sort.equals("Low To High")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o1.getPrice()).compareTo(o2.getPrice());
                                                }
                                            });
                                            categorizedList.add(model);
                                        }
                                        if(sort.equals("High To Low")){
                                            Collections.sort(categorizedList, new Comparator<ItemProductModel>() {
                                                @Override
                                                public int compare(ItemProductModel o1, ItemProductModel o2) {
                                                    return Integer.valueOf(o2.getPrice()).compareTo(o1.getPrice());
                                                }
                                            });
                                            categorizedList.add(model);
                                        }
                                    }
                                }
                                itemRecycleAdapter = new ItemRecycleAdapter(ItemActivity.this, categorizedList);
                                itemRecycle.setAdapter(itemRecycleAdapter);
                                if (itemProductModels.size() == 0) {
                                    noResultTV.setVisibility(View.VISIBLE);
                                    itemRecycle.setVisibility(View.GONE);
                                } else {
                                    noResultTV.setVisibility(View.GONE);
                                    itemRecycle.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (Exception ex) {
                            Log.i("TAG", "TAG " + ex.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                        Log.i("TAG", "TAG: Server Failure: " + t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG", "TAG " + ex.getMessage());
            }
        }
    }


    private void loadDrinks(String restaurantID) {
        String token = getUserToken();
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getDrinksProducts(restaurantID,token).enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            itemRecycleAdapter = new ItemRecycleAdapter(ItemActivity.this,itemProductModels);
                            itemRecycle.setAdapter(itemRecycleAdapter);

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
        String token = getUserToken();
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getProductsOfMainCategory(categoryId,token).enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            itemRecycleAdapter =  new ItemRecycleAdapter(ItemActivity.this,itemProductModels);
                            itemRecycle.setAdapter(itemRecycleAdapter);

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

    private String getUserToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("AuthenticationTypes",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }


    private void getProductOfRestaurant(String categoryID, String subCategoryID, String restaurantID) {
        String token = getUserToken();
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getProductOfRestaurant(restaurantID,categoryID,subCategoryID,token).enqueue(new Callback<List<ItemProductModel>>() {
                @Override
                public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            itemProductModels = response.body();
                            itemRecycleAdapter = new ItemRecycleAdapter(ItemActivity.this,itemProductModels);
                            itemRecycle.setAdapter(itemRecycleAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == GlobalObjects.ITEM_LIST_REFRESH_CODE) {
            Log.i("TAG","TAG item list");
            ItemProductModel itemProductModel = (ItemProductModel) data.getSerializableExtra("model");
            int index=data.getIntExtra("index",0);
            itemRecycleAdapter.updateItemProduct(index,itemProductModel);
        } else if(requestCode == GlobalObjects.POPULAR_LIST_REFRESH) {
            Log.i("TAG","TAG Popuilar list");
            PopularSectionModel popularSectionModel = (PopularSectionModel) data.getSerializableExtra("model");
            int index=data.getIntExtra("index",0);
            itemRecycleAdapter.updatePopularProduct(index,popularSectionModel);
        } else if(resultCode == GlobalObjects.RESTAURANT_REFRESH_CODE) {
            Log.i("TAG","TAG Restaurant list");
        } else if(resultCode == GlobalObjects.KIDS_LIST_REFRESH) {
            Log.i("TAG","TAG Kid list");
            KidSectionModel kidSectionModel = (KidSectionModel) data.getSerializableExtra("model");
            int index=data.getIntExtra("index",0);
            itemRecycleAdapter.updateKidProduct(index,kidSectionModel);
        }
    }
}