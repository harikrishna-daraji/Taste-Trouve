package com.example.tastetrouverestaurantowner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


        Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.orderTotal=(TextView)itemview.findViewById(R.id.orderTotal);
            this.orderDate = (TextView)itemview.findViewById(R.id.orderDate);
            this.orderCount = (TextView)itemview.findViewById(R.id.orderCount);
            this.address = (TextView)itemview.findViewById(R.id.address);
            this.status = (TextView)itemview.findViewById(R.id.status);
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





        orderId.setText(pendingOrderModalArrayList.get(position).getOrderId());
        orderTotal.setText("$ "+pendingOrderModalArrayList.get(position).getTotalPrice());
        orderDate.setText(pendingOrderModalArrayList.get(position).getOrderDate());

        orderCount.setText(pendingOrderModalArrayList.get(position).getItemCount()+" items");

        address.setText(pendingOrderModalArrayList.get(position).getAddressId().address);
        status.setText(pendingOrderModalArrayList.get(position).getStatus());


    }


    @Override
    public int getItemCount() {
        return pendingOrderModalArrayList.size();
    }
}

