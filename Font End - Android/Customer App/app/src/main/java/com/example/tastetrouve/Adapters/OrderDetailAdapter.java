package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.OrderDetail;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.Models.OrderDetailModel;
import com.example.tastetrouve.Models.ProductOrderModel;
import com.example.tastetrouve.R;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {


    List<ProductOrderModel> myOrderModels;
    Context context;


    public OrderDetailAdapter(List<ProductOrderModel> myOrderModels, Context context) {
        this.myOrderModels = myOrderModels;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView orderId;
        TextView price;
        TextView date;
        ImageView image;



        Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.price=(TextView)itemview.findViewById(R.id.price);
            this.date = (TextView)itemview.findViewById(R.id.date);
            this.image = (ImageView)itemview.findViewById(R.id.image);

        }
    }



    @NonNull
    @Override
    public OrderDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_orderdetail,parent,false);
        OrderDetailAdapter.MyViewHolder myViewHolder= new OrderDetailAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView price=holder.price;
        TextView date=holder.date;
        ImageView image=holder.image;






        orderId.setText(myOrderModels.get(position).getName());
        price.setText("$ "+myOrderModels.get(position).getPrice());
        date.setText("Quantity "+myOrderModels.get(position).getQuantity());

        Glide.with(context)
                .asBitmap()
                .load(myOrderModels.get(position).getImage())
                .into(image);



    }


    @Override
    public int getItemCount() {
        return myOrderModels.size();
    }
}


