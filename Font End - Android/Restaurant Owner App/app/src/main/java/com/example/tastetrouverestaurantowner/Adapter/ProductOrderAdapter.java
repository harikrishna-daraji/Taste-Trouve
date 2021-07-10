package com.example.tastetrouverestaurantowner.Adapter;

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
import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Activity.UpdateItemActivity;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.MyViewHolder> {

    ArrayList<ProductOrderModal> productOrderModalArrayList;
    Context context;
    ApiInterface api;

    public ProductOrderAdapter(ArrayList<ProductOrderModal> productOrderModalArrayList, Context context) {
        this.productOrderModalArrayList = productOrderModalArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        ImageView image;
        TextView name;
        TextView price;
        TextView quantity;


        MyViewHolder (View itemview)
        {
            super(itemview);

            this.image=(ImageView) itemview.findViewById(R.id.image);
            this.name=(TextView)itemview.findViewById(R.id.name);
            this.price = (TextView)itemview.findViewById(R.id.price);
            this.quantity = (TextView)itemview.findViewById(R.id.quantity);
        }
    }



    @NonNull
    @Override
    public ProductOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_product_order,parent,false);
        ProductOrderAdapter.MyViewHolder myViewHolder= new ProductOrderAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.MyViewHolder holder, final int position) {

        TextView name=holder.name;
        TextView price=holder.price;
        TextView quantity=holder.quantity;
        ImageView image=holder.image;


        name.setText(productOrderModalArrayList.get(position).getName());
        price.setText("$ "+productOrderModalArrayList.get(position).getPrice().toString());
        quantity.setText(productOrderModalArrayList.get(position).getQuantity());

        Glide.with(context)
                .asBitmap()
                .load(productOrderModalArrayList.get(position).getImage())
                .into(image);



    }


    @Override
    public int getItemCount() {
        return productOrderModalArrayList.size();
    }
}

