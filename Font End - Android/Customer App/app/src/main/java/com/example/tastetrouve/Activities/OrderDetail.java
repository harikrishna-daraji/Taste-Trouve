package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.MyOrderAdapter;
import com.example.tastetrouve.Adapters.OrderDetailAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.Models.OrderDetailModel;
import com.example.tastetrouve.R;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetail extends  BaseActivity  {


    List<OrderDetailModel> orderDetails;
    RecyclerView recyclerView;
    TextView orderIDdetail,tax,Delivery,total,orderStatus,ratingReview;
    SharedPreferences sharedPreferences;
    LinearLayout reviewOrder,reviewContainer, trackOrderLinear;
    RatingBar rating;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_order_detail);

        trackOrderLinear = findViewById(R.id.trackOrderLinear);
        trackOrderLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDriverId();
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        orderId = getIntent().getStringExtra("orderId");

        recyclerView = findViewById(R.id.orderDetails);
        orderIDdetail = findViewById(R.id.orderIDdetail);
        reviewContainer = findViewById(R.id.reviewContainer);
        tax = findViewById(R.id.tax);
        Delivery = findViewById(R.id.Delivery);
        total = findViewById(R.id.total);
        orderStatus = findViewById(R.id.orderStatus);
        reviewOrder = findViewById(R.id.reviewOrder);
        rating = findViewById(R.id.rating);
        ratingReview = findViewById(R.id.ratingReview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });


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
                            orderStatus.setText(orderDetails.get(0).getOrderStatus().toString());
                            rating.setRating(orderDetails.get(0).getRatingStar());
                            ratingReview.setText(orderDetails.get(0).getRatingReview());
                            Log.d("TAG", orderDetails.get(0).getRatingReview());

                            if(orderDetails.get(0).getOrderStatus().equals(GlobalObjects.delivered)) {
                                if(orderDetails.get(0).getRatingReview().equals("") ){
                                    reviewContainer.setVisibility(View.GONE);
                                }else {
                                    reviewOrder.setVisibility(View.GONE);
                                }
                            } else if(orderDetails.get(0).getOrderStatus().equals(GlobalObjects.accepted_by_driver)) {
                                trackOrderLinear.setVisibility(View.VISIBLE);
                                reviewContainer.setVisibility(View.GONE);
                                reviewOrder.setVisibility(View.GONE);
                            } else {
                                trackOrderLinear.setVisibility(View.GONE);
                                reviewOrder.setVisibility(View.GONE);
                                reviewContainer.setVisibility(View.GONE);
                            }
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

    private void getDriverId() {
        if(!orderId.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getDriverIdOfOrder(orderId).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject finalResponse = new JSONObject(response.body().string());
                            String driverId = finalResponse.getString("60fb0074fd8abd572e9ebe86");

                            Intent intent = new Intent(OrderDetail.this,DriverLocationActivity.class);
                            intent.putExtra("driverId",driverId);
                            startActivity(intent);
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }
}