package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.R;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder> {


    List<MyOrderModel> myOrderModels;
    Context context;


    public MyOrderAdapter(List<MyOrderModel> myOrderModels, Context context) {
        this.myOrderModels = myOrderModels;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView orderId;
        TextView price;
        TextView date;



        Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.price=(TextView)itemview.findViewById(R.id.price);
            this.date = (TextView)itemview.findViewById(R.id.date);

        }
    }



    @NonNull
    @Override
    public MyOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_myorder,parent,false);
        MyOrderAdapter.MyViewHolder myViewHolder= new MyOrderAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView price=holder.price;
        TextView date=holder.date;






        orderId.setText(myOrderModels.get(position).get_id());
        price.setText("$ "+myOrderModels.get(position).getTotal());
        date.setText("Total "+myOrderModels.get(position).getOrderDate());



    }


    @Override
    public int getItemCount() {
        return myOrderModels.size();
    }
}


