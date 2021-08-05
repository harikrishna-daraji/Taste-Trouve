package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.tastetrouve.Adapters.FavouriteAdapter;
import com.example.tastetrouve.Adapters.OfferAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferContainerActivity extends BaseActivity {

    List<ItemProductModel> itemProductModels;
    RecyclerView offerRecycler;
    String offer;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_offer_container);


         offer = getIntent().getStringExtra("offer");

        offerRecycler = findViewById(R.id.specialOffersRecycler);

        offerRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        getSpecialOfferProducts();
    }


    private void getSpecialOfferProducts() {
        String token = getUserToken();
        Log.i("TAG","TAG: Token id: "+token);
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getSpecialProducts(token,offer).enqueue(new Callback<List<ItemProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                        try {
                            if(response.code() == 200) {
                                itemProductModels = response.body();

                                offerRecycler.setAdapter(new OfferAdapter(itemProductModels,getApplicationContext()));

                            } else {
                                Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

    private String getUserToken() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("AuthenticationTypes",getApplicationContext().MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }
}