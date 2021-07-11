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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Adapters.KeywordRecycleAdapter;
import com.example.tastetrouve.Fragments.FavouriteFragment;
import com.example.tastetrouve.Fragments.HomeFragment;
import com.example.tastetrouve.Fragments.SettingsFragment;
import com.example.tastetrouve.HelperClass.AbsolutefitLayourManager;
import com.example.tastetrouve.Adapters.KidMenuRecycleAdapter;
import com.example.tastetrouve.Adapters.RestaurantRecycleAdapter;
import com.example.tastetrouve.Adapters.TopSellingRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Interfaces.AddressInterface;
import com.example.tastetrouve.Interfaces.HomeInterfaceMethods;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity implements HomeInterfaceMethods, AddressInterface {


    SharedPreferences sharedPreferences;
    List<ItemProductModel> itemProductModels;
    List<ItemProductModel> filteredItemProductModel = new ArrayList<>();
    BlurView blurView;
    RelativeLayout blurRelative;
    RecyclerView keywordRecycle;
    TabLayout tabs;
    EditText searchEditText;
    HomeFragment homeFragment;
    SettingsFragment settingsFragment;
    FavouriteFragment favouriteFragment;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_home);
        homeFragment = new HomeFragment();
        favouriteFragment = new FavouriteFragment();
        settingsFragment = new SettingsFragment();
        initUI();
        getAllProducts();
        blurBackground();


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                try {

                    sharedPreferences = getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
                    String phone = sharedPreferences.getString("phone","");
                    Log.i("TAG","phone"+phone);
                    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    apiInterface.updateUserFcmToken(phone,task.getResult()).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.code() == 200) {
                                    Log.i("TAG","TAG: Fcm token in updated");
                                }
                            } catch (Exception ex) {
                                Log.i("TAG","TAG "+ex.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("TAG","TAG: Serve failure: "+t.getMessage());
                        }
                    });
                } catch (Exception ex) {
                    Log.i("TAG","TAG "+ex.getMessage());
                }
            }
        });
    }

    private void initUI() {
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView);
//        bottomNavigationView.setBackground(null);
//        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        tabs =findViewById(R.id.tabs);
        tabs.getTabAt(1).select();
        blurRelative = findViewById(R.id.blurRelative);

        openNavigationMenu(homeFragment);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    openNavigationMenu(favouriteFragment);
                } else if(tab.getPosition() == 1) {
                    openNavigationMenu(homeFragment);
                } else if(tab.getPosition() == 2) {
                    openNavigationMenu(settingsFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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

    private void openNavigationMenu(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerHomeRelative, fragment).commit();
    }

    @Override
    public void openSearchRelative() {
        blurRelative.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        settingsFragment.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void deleteAddress(String address) {
        settingsFragment.deleteAddress(address);
    }
}