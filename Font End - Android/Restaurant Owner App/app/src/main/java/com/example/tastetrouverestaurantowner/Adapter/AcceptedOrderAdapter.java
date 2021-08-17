package com.example.tastetrouverestaurantowner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

public class AcceptedOrderAdapter extends RecyclerView.Adapter<AcceptedOrderAdapter.MyViewHolder> {


    List<PendingOrderModal> pendingOrderModalArrayList;
    Context context;


    public AcceptedOrderAdapter(List<PendingOrderModal> pendingOrderModalArrayList, Context context) {
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
        TextView status;
        TextView ratingReview;


        RatingBar rating;
        LinearLayout reviewContainer;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.orderTotal=(TextView)itemview.findViewById(R.id.orderTotal);
            this.orderDate = (TextView)itemview.findViewById(R.id.orderDate);
            this.orderCount = (TextView)itemview.findViewById(R.id.orderCount);
            this.address = (TextView)itemview.findViewById(R.id.address);
            this.status = (TextView)itemview.findViewById(R.id.status);
            this.ratingReview = (TextView)itemview.findViewById(R.id.ratingReview);
            this.rating = itemview.findViewById(R.id.rating);
            this.reviewContainer = itemview.findViewById(R.id.reviewContainer);
        }

    }



    @NonNull
    @Override
    public AcceptedOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_acepted_order,parent,false);
        AcceptedOrderAdapter.MyViewHolder myViewHolder= new AcceptedOrderAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedOrderAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView orderTotal=holder.orderTotal;
        TextView orderDate=holder.orderDate;
        TextView orderCount=holder.orderCount;
        TextView address=holder.address;
        TextView status=holder.status;

        TextView ratingReview=holder.ratingReview;
        RatingBar rating=holder.rating;
        LinearLayout reviewContainer=holder.reviewContainer;




        status.setText(pendingOrderModalArrayList.get(position).orderStatus);
        orderId.setText(pendingOrderModalArrayList.get(position).getOrderId());
        orderTotal.setText("$ "+pendingOrderModalArrayList.get(position).getTotalPrice());
        orderDate.setText(pendingOrderModalArrayList.get(position).getOrderDate());

        if(pendingOrderModalArrayList.get(position).getItemCount() > 1) {
            orderCount.setText(pendingOrderModalArrayList.get(position).getItemCount()+" items");
        } else {
            orderCount.setText(pendingOrderModalArrayList.get(position).getItemCount()+" item");
        }


        address.setText(pendingOrderModalArrayList.get(position).getAddressId().address);
        ratingReview.setText(pendingOrderModalArrayList.get(position).getRatingReview());
        rating.setRating(pendingOrderModalArrayList.get(position).getRatingStar());

        if(pendingOrderModalArrayList.get(position).getRatingReview().equals("") ){
            reviewContainer.setVisibility(View.GONE);
        }else {
            reviewContainer.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public int getItemCount() {
        return pendingOrderModalArrayList.size();
    }
}

