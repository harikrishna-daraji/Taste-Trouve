package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.AcceptedOrderAdapter;
import com.example.tastetrouverestaurantowner.Adapter.PendingOrdersAdapter;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptedOrderActivity extends AppCompatActivity {

    List<PendingOrderModal> pendingOrderModalArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_order);

        recyclerView = findViewById(R.id.acceptedOrderRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        Call<List<PendingOrderModal>> call = APIClient.getInstance().getApi().getPendingOrders(ownerId,"accepted");

        call.enqueue(new Callback<List<PendingOrderModal>>() {
            @Override
            public void onResponse(Call<List<PendingOrderModal>> call, Response<List<PendingOrderModal>> response) {

                pendingOrderModalArrayList = response.body();

                AcceptedOrderAdapter customAdapter= new AcceptedOrderAdapter(pendingOrderModalArrayList,AcceptedOrderActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<PendingOrderModal>> call, Throwable t) {

            }
        });
    }
}