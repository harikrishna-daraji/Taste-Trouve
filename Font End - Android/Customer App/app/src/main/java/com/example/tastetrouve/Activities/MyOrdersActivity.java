package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.tastetrouve.Adapters.MyOrderAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.Models.UserModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrdersActivity extends AppCompatActivity {

    List<MyOrderModel> myOrderModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        recyclerView = findViewById(R.id.myOrderREcycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences("AuthenticationTypes",MODE_PRIVATE);
            String token = sharedPreferences.getString("token","");



        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getMyOrders(token).enqueue(new Callback<List<MyOrderModel>>() {
                @Override
                public void onResponse(Call<List<MyOrderModel>> call, Response<List<MyOrderModel>> response) {
                    try {

                        if(response.code() == 200) {
                            myOrderModels = response.body();
                            MyOrderAdapter customAdapter= new MyOrderAdapter(myOrderModels,MyOrdersActivity.this);
                            recyclerView.setAdapter(customAdapter);
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<MyOrderModel>> call, Throwable t) {
                    Log.i("TAG","TAG: "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }
}