package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tastetrouverestaurantowner.Adapter.PendingOrdersAdapter;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;

public class PendingOrdersActivity extends AppCompatActivity {

    ArrayList<PendingOrderModal> pendingOrderModalArrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);
        recyclerView = findViewById(R.id.pendingOrderRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pendingOrderModalArrayList = new ArrayList<>();

        pendingOrderModalArrayList.add(new PendingOrderModal("HB4455CFC55","11 july 2021","50","3","3080 Rue Goyer","pending","George Hillion","5145492584",buildSubItemList()));



        PendingOrdersAdapter customAdapter= new PendingOrdersAdapter(pendingOrderModalArrayList,PendingOrdersActivity.this);
        recyclerView.setAdapter(customAdapter);
    }

    private ArrayList<ProductOrderModal> buildSubItemList() {
        ArrayList<ProductOrderModal> subItemList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            ProductOrderModal subItem = new ProductOrderModal("1","https://www.wholesomeyum.com/wp-content/uploads/2020/03/wholesomeyum-chef-salad-recipe-4.jpg","item1","33","2");
            subItemList.add(subItem);
        }
        return subItemList;
    }
}