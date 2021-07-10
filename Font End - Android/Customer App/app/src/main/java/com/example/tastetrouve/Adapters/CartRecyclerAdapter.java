package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.Models.CartProductModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerAdapter  extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    List<CartModel> cartModelArrayList;
    Context context;

    public CartRecyclerAdapter(List<CartModel> cartModelArrayList, Context context) {
        this.cartModelArrayList = cartModelArrayList;
        this.context = context;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView name;
        TextView price;
        TextView quantity;
        ImageView image,add,minus;



        MyViewHolder (View itemview)
        {
            super(itemview);

            this.name=(TextView)itemview.findViewById(R.id.name);
            this.price=(TextView)itemview.findViewById(R.id.price);
            this.quantity=(TextView)itemview.findViewById(R.id.quantity);

            this.image=(ImageView)itemview.findViewById(R.id.image);
            this.add=(ImageView)itemview.findViewById(R.id.add);
            this.minus=(ImageView)itemview.findViewById(R.id.minus);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.cart_item,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView name=holder.name;
        TextView price=holder.price;
        TextView quantity=holder.quantity;
        ImageView image=holder.image;
        ImageView add=holder.add;
        ImageView minus=holder.minus;


        CartProductModel model = cartModelArrayList.get(position).getProductId();

        name.setText(model.getName());
        price.setText("$"+model.getPrice());
        quantity.setText(cartModelArrayList.get(position).getQuantity());

        Glide.with(context).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }
}
