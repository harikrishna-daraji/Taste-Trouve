package com.example.tastetrouve.Adapters;

import android.app.Activity;
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
import com.example.tastetrouve.Activities.ItemActivity;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.SubCategoryModel;
import com.example.tastetrouve.R;

import java.util.List;


public class SubCategoryRecycleAdapter extends RecyclerView.Adapter<SubCategoryRecycleAdapter.ViewHolder> {

    Activity activity;
    List<SubCategoryModel>  subCategoryModels;
    String restaurantID;
    String categoryID;

    public SubCategoryRecycleAdapter(List<SubCategoryModel> subCategoryModelList, Activity activity, String restaurantID, String categoryID) {
        this.activity = activity;
        this.subCategoryModels = subCategoryModelList;
        this.restaurantID = restaurantID;
        this.categoryID = categoryID;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategoryModel model = subCategoryModels.get(position);
        Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.image);
        holder.textView.setText(model.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ItemActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Category.toString(),categoryID);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantID);
                intent.putExtra(GlobalObjects.ModelList.Subcategory.toString(),model);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView textView;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.subCategoryImg);
            textView = itemView.findViewById(R.id.subCategoryTV);
            relativeLayout  = itemView.findViewById(R.id.subCategoryRelative);
        }
    }

}
