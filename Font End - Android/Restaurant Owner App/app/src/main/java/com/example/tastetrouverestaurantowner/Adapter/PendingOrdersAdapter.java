package com.example.tastetrouverestaurantowner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.MyViewHolder> {

    private  RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
    ArrayList<PendingOrderModal> pendingOrderModalArrayList;
    Context context;
    ApiInterface api;

    public PendingOrdersAdapter(ArrayList<PendingOrderModal> pendingOrderModalArrayList, Context context) {
        this.pendingOrderModalArrayList = pendingOrderModalArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView orderId;
        TextView orderTotal;
        TextView orderDate;
        TextView orderCount;
        TextView address;
        TextView userName;
        TextView userPhone;
        TextView status;
        RecyclerView pendingOrderRecycler;

   Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.orderTotal=(TextView)itemview.findViewById(R.id.orderTotal);
            this.orderDate = (TextView)itemview.findViewById(R.id.orderDate);
            this.orderCount = (TextView)itemview.findViewById(R.id.orderCount);
            this.address = (TextView)itemview.findViewById(R.id.address);
            this.userName = (TextView)itemview.findViewById(R.id.userName);
            this.userPhone = (TextView)itemview.findViewById(R.id.userPhone);
            this.pendingOrderRecycler = (RecyclerView)itemview.findViewById(R.id.pendingOrderRecycler);
            this.status = (TextView)itemview.findViewById(R.id.status);
        }
    }



    @NonNull
    @Override
    public PendingOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_product_order,parent,false);
        PendingOrdersAdapter.MyViewHolder myViewHolder= new PendingOrdersAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrdersAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView orderTotal=holder.orderTotal;
        TextView orderDate=holder.orderDate;
        TextView orderCount=holder.orderCount;
        TextView address=holder.address;
        TextView userName=holder.userName;
        TextView userPhone=holder.userPhone;
        TextView status=holder.status;
        RecyclerView pendingOrderRecycler=holder.pendingOrderRecycler;

        // Add sub recycler view

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                pendingOrderRecycler.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(pendingOrderModalArrayList.get(position).getProductOrderModalList().size());

        // Create sub item view adapter
        ProductOrderAdapter subItemAdapter = new ProductOrderAdapter((ArrayList<ProductOrderModal>) pendingOrderModalArrayList.get(position).getProductOrderModalList(),context);

        pendingOrderRecycler.setLayoutManager(layoutManager);
        pendingOrderRecycler.setAdapter(subItemAdapter);
        pendingOrderRecycler.setRecycledViewPool(viewPool);


        orderId.setText(pendingOrderModalArrayList.get(position).getOrderId());
        orderTotal.setText("$ "+pendingOrderModalArrayList.get(position).getTotalPrice());
        orderDate.setText(pendingOrderModalArrayList.get(position).getOrderDate());

        orderCount.setText(pendingOrderModalArrayList.get(position).getItemCount());

        address.setText(pendingOrderModalArrayList.get(position).getAddress());
        userName.setText(pendingOrderModalArrayList.get(position).getUserName());

        userPhone.setText(pendingOrderModalArrayList.get(position).getUserPhone());
        status.setText(pendingOrderModalArrayList.get(position).getStatus());


    }


    @Override
    public int getItemCount() {
        return pendingOrderModalArrayList.size();
    }
}

