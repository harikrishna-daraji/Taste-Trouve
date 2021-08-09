package com.example.tastetrouvedriver;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouvedriver.Helper.Model.PastOrderModel;

import java.util.List;

public class PastOrdersAdapter extends RecyclerView.Adapter<PastOrdersAdapter.MyViewHolder> {


    List<PastOrderModel> pastOrderModelList;
    Context context;


    public PastOrdersAdapter(List<PastOrderModel> pastOrderModelList, Context context) {
        this.pastOrderModelList = pastOrderModelList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView orderId;
        TextView price;
        TextView date;
        ImageView moreDetail;
        ImageView image;


        Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.price=(TextView)itemview.findViewById(R.id.price);
            this.date = (TextView)itemview.findViewById(R.id.date);
            this.image = (ImageView) itemview.findViewById(R.id.image);

        }
    }



    @NonNull
    @Override
    public PastOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_myorder,parent,false);
        PastOrdersAdapter.MyViewHolder myViewHolder= new PastOrdersAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PastOrdersAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView price=holder.price;
        TextView date=holder.date;
        ImageView moreDetail=holder.moreDetail;
        ImageView image=holder.image;






        orderId.setText(pastOrderModelList.get(position).getOrderId().get_id());
        price.setText("$ "+pastOrderModelList.get(position).getOrderId().getTotal());
        date.setText(pastOrderModelList.get(position).getOrderId().getTotal());

        Glide.with(context)
                .asBitmap()
                .load(pastOrderModelList.get(position).getOrderId().getOrderImage())
                .into(image);




    }


    @Override
    public int getItemCount() {
        return pastOrderModelList.size();
    }
}


