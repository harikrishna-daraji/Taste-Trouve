package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.DriverAdapter;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.DriverModal;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriversActivity extends AppCompatActivity {

    ApiInterface api;

    ArrayList<DriverModal> driverModalArrayList;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        Intent intent = getIntent();
        String orderId = intent.getStringExtra("orderId");

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        recyclerView = findViewById(R.id.avaliabledriverrecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Call<List<DriverModal>> call = APIClient.getInstance().getApi().getDrivers();

        call.enqueue(new Callback<List<DriverModal>>() {
            @Override
            public void onResponse(Call<List<DriverModal>> call, Response<List<DriverModal>> response) {

                List<DriverModal> productList = response.body();


                driverModalArrayList = new ArrayList<>();
                for(int i=0; i <productList.size(); i++) {
                    driverModalArrayList.add(new DriverModal(productList.get(i).get_id(),productList.get(i).getDisplayname(),productList.get(i).getEmail(),
                            productList.get(i).getPassword(),productList.get(i).getPhoneNumber()
                    ));

                }



                DriverAdapter customAdapter= new DriverAdapter(driverModalArrayList,DriversActivity.this,orderId,ownerId);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<DriverModal>> call, Throwable t) {

            }




        });
    }
}