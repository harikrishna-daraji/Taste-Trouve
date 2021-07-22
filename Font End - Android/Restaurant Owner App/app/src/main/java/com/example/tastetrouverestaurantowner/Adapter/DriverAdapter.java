package com.example.tastetrouverestaurantowner.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import com.bumptech.glide.Glide;
import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Activity.DriversActivity;
import com.example.tastetrouverestaurantowner.Activity.MainActivity;
import com.example.tastetrouverestaurantowner.Activity.UpdateItemActivity;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.DriverModal;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder> {

    ArrayList<DriverModal> driverModalArrayList;
    Context context;
    String orderId;
    String ownerId;



    public DriverAdapter(ArrayList<DriverModal> driverModalArrayList, Context context,String orderId,String ownerId) {
        this.driverModalArrayList = driverModalArrayList;
        this.context = context;
        this.orderId=orderId;
        this.ownerId=ownerId;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {



        TextView name;
        TextView email;
        TextView phone;
        TextView assign;


        MyViewHolder (View itemview)
        {
            super(itemview);


            this.name=(TextView)itemview.findViewById(R.id.name);
            this.email = (TextView)itemview.findViewById(R.id.email);
            this.phone = (TextView)itemview.findViewById(R.id.phone);
            this.assign = (TextView)itemview.findViewById(R.id.assign);
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
        TextView assign=holder.assign;


        name.setText(driverModalArrayList.get(position).getDisplayname());
        email.setText(driverModalArrayList.get(position).getEmail());
        phone.setText(driverModalArrayList.get(position).getPhoneNumber());



        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = APIClient.getInstance().getApi().assignDriver(driverModalArrayList.get(position).get_id(),orderId,ownerId);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Toast.makeText(context, "Driver Assigned", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(context, MainActivity.class);

                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                    }


                });
            }
        });


    }


    @Override
    public int getItemCount() {
        return driverModalArrayList.size();
    }
}

