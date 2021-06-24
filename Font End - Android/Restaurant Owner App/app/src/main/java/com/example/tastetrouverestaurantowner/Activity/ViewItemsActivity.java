package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewItemsActivity extends AppCompatActivity {

    ApiInterface api;
    ArrayList<ProductModal> productModalArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        recyclerView = findViewById(R.id.productList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        Call<List<ProductModal>> call = APIClient.getInstance().getApi().getproducts(ownerId);

        call.enqueue(new Callback<List<ProductModal>>() {
            @Override
            public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>> response) {

                List<ProductModal> productList = response.body();


                productModalArrayList = new ArrayList<>();
                for(int i=0; i <productList.size(); i++) {
                    productModalArrayList.add(new ProductModal(productList.get(i).get_id(),productList.get(i).getName(),productList.get(i).getImage(),productList.get(i).getPrice(),productList.get(i).getDescription()

                    ,productList.get(i).getCalories(),productList.get(i).getQuantity(),productList.get(i).getKidSection(),productList.get(i).getPopular(),productList.get(i).getVisibleStatus(),productList.get(i).getDeliveryTime()
                    ));

                }

                ViewItemAdapter customAdapter= new ViewItemAdapter(productModalArrayList,ViewItemsActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<ProductModal>> call, Throwable t) {

            }




        });


    }
}