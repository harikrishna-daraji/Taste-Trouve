package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.PendingOrdersAdapter;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrdersActivity extends AppCompatActivity {

    ArrayList<PendingOrderModal> pendingOrderModalArrayList;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        recyclerView = findViewById(R.id.pendingOrderRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        Call<List<PendingOrderModal>> call = APIClient.getInstance().getApi().getPendingOrders(ownerId);

        call.enqueue(new Callback<List<PendingOrderModal>>() {
            @Override
            public void onResponse(Call<List<PendingOrderModal>> call, Response<List<PendingOrderModal>> response) {

                List<PendingOrderModal> productList = response.body();


                pendingOrderModalArrayList = new ArrayList<>();
                for(int i=0; i <productList.size(); i++) {

                    pendingOrderModalArrayList.add(new PendingOrderModal(productList.get(i).getOrderId(),productList.get(i).getOrderDate(),
                            productList.get(i).getTotalPrice(),productList.get(i).getProductOrderModalList().size(),productList.get(i).getAddress(),
                            productList.get(i).getStatus(),productList.get(i).getUserName(),productList.get(i).getUserPhone(),buildSubItemList(productList.get(i).getProductOrderModalList())));


                }


                PendingOrdersAdapter customAdapter= new PendingOrdersAdapter(pendingOrderModalArrayList,PendingOrdersActivity.this);
                recyclerView.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<PendingOrderModal>> call, Throwable t) {

            }
        });

    }

    private ArrayList<ProductOrderModal> buildSubItemList(List<ProductOrderModal> productOrderModalList) {
        ArrayList<ProductOrderModal> subItemList = new ArrayList<>();
        for (int i=0; i<productOrderModalList.size(); i++) {
            ProductOrderModal subItem = new ProductOrderModal(productOrderModalList.get(i).getId(),productOrderModalList.get(i).getImage(),
                    productOrderModalList.get(i).getName(),productOrderModalList.get(i).getPrice(),
                    productOrderModalList.get(i).getQuantity());
            subItemList.add(subItem);
        }
        return subItemList;
    }
}