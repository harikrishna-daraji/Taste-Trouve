package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.MyOrderAdapter;
import com.example.tastetrouve.Adapters.OrderDetailAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.Models.OrderDetailModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetail extends  BaseActivity  {


    List<OrderDetailModel> orderDetails;
    RecyclerView recyclerView;
    TextView orderIDdetail,tax,Delivery,total;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_order_detail);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        String orderId = getIntent().getStringExtra("orderId");

        recyclerView = findViewById(R.id.orderDetails);
        orderIDdetail = findViewById(R.id.orderIDdetail);
        tax = findViewById(R.id.tax);
        Delivery = findViewById(R.id.Delivery);
        total = findViewById(R.id.total);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getMyOrdersByUser(orderId).enqueue(new Callback<List<OrderDetailModel>>() {
                @Override
                public void onResponse(Call<List<OrderDetailModel>> call, Response<List<OrderDetailModel>> response) {
                    try {

                        if(response.code() == 200) {
                            orderDetails = response.body();
                            OrderDetailAdapter customAdapter= new OrderDetailAdapter(orderDetails.get(0).getProducts(),OrderDetail.this);
                            recyclerView.setAdapter(customAdapter);

                            orderIDdetail.setText(orderDetails.get(0).get_id().toString());
                            tax.setText(orderDetails.get(0).getTax().toString());
                            Delivery.setText(orderDetails.get(0).getDelivery().toString());
                            total.setText(orderDetails.get(0).getTotal().toString());
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<OrderDetailModel>> call, Throwable t) {

                }


            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }


    }
}