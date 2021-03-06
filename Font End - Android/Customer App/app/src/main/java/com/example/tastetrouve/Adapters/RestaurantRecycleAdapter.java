package com.example.tastetrouve.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.MainCategoryActivity;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.RestaurantModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRecycleAdapter extends RecyclerView.Adapter<RestaurantRecycleAdapter.ViewHolder>{

    Activity activity;
    List<RestaurantModel> list;
    List<CategoryModel> categoryModels;

    public RestaurantRecycleAdapter(Activity activity, List<RestaurantModel> list, List<CategoryModel> categoryModels) {
        this.activity = activity;
        this.list = list;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantModel model = list.get(position);
        holder.restaurantNameTV.setText(model.getRestaurantName());
        Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.restaurantImage);
        holder.restaurantRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainCategoryActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),model);
                intent.putStringArrayListExtra(GlobalObjects.ModelList.Category.toString(),(ArrayList)categoryModels);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout restaurantRelative;
        TextView restaurantNameTV;
        ImageView restaurantImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantRelative = itemView.findViewById(R.id.restaurantRelative);
            restaurantNameTV = itemView.findViewById(R.id.restaurantNameTV);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
        }
    }

}
