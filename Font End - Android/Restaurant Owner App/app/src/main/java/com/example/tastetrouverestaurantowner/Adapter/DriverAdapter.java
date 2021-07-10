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
import com.example.tastetrouverestaurantowner.Modal.DriverModal;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {

    ArrayList<DriverModal> driverModalArrayList;
    Context context;


    public DriverAdapter(ArrayList<DriverModal> driverModalArrayList, Context context) {
        this.driverModalArrayList = driverModalArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {



        TextView name;
        TextView email;
        TextView phone;


        MyViewHolder (View itemview)
        {
            super(itemview);


            this.name=(TextView)itemview.findViewById(R.id.name);
            this.email = (TextView)itemview.findViewById(R.id.email);
            this.phone = (TextView)itemview.findViewById(R.id.phone);
        }
    }



    @NonNull
    @Override
    public DriverAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_driver,parent,false);
        DriverAdapter.MyViewHolder myViewHolder= new DriverAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverAdapter.MyViewHolder holder, final int position) {

        TextView name=holder.name;
        TextView email=holder.email;
        TextView phone=holder.phone;


        name.setText(driverModalArrayList.get(position).getDisplayname());
        email.setText(driverModalArrayList.get(position).getEmail());
        phone.setText(driverModalArrayList.get(position).getPhoneNumber());




    }


    @Override
    public int getItemCount() {
        return driverModalArrayList.size();
    }
}

