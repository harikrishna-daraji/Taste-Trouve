package com.example.tastetrouve.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.ItemDetailsActivity;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.KidSectionModel;
import com.example.tastetrouve.Models.PopularSectionModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemRecycleAdapter extends RecyclerView.Adapter<ItemRecycleAdapter.ViewHolder> {

    Activity activity;
    List<ItemProductModel> itemProductModels;
    ArrayList<PopularSectionModel> popularSectionModels;
    List<KidSectionModel> kidSectionModels;

    public ItemRecycleAdapter(Activity activity, List<ItemProductModel> itemProductModels) {
        this.activity = activity;
        this.itemProductModels = itemProductModels;
    }

    public ItemRecycleAdapter(Activity activity, ArrayList<PopularSectionModel> popularSectionModels) {
        this.activity = activity;
        this.popularSectionModels = popularSectionModels;
    }

    public ItemRecycleAdapter(List<KidSectionModel> kidSectionModels, Activity activity) {
        this.kidSectionModels = kidSectionModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kidmenu_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(itemProductModels != null) {
            ItemProductModel model = itemProductModels.get(position);
            Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.circleImg);
            holder.nameTV.setText(model.getName());
            holder.typeTV.setText(model.getName());
            holder.priceTV.setText("$"+String.valueOf(model.getPrice()));
            holder.viewRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ItemDetailsActivity.class);
                    intent.putExtra("type", GlobalObjects.ModelList.Item.toString());
                    intent.putExtra("product",model);
                    activity.startActivity(intent);
                }
            });
        } else if(kidSectionModels != null) {
            KidSectionModel model = kidSectionModels.get(position);
            Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.circleImg);
            holder.nameTV.setText(model.getName());
            holder.typeTV.setText(model.getName());
            holder.priceTV.setText("$"+String.valueOf(model.getPrice()));
            holder.viewRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ItemDetailsActivity.class);
                    intent.putExtra("type", GlobalObjects.ModelList.Kid.toString());
                    intent.putExtra("product",model);
                    activity.startActivity(intent);
                }
            });
        } else {
            PopularSectionModel model = popularSectionModels.get(position);
            Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.circleImg);
            holder.nameTV.setText(model.getName());
            holder.typeTV.setText(model.getName());
            holder.priceTV.setText("$"+String.valueOf(model.getPrice()));
            holder.viewRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ItemDetailsActivity.class);
                    intent.putExtra("type", GlobalObjects.ModelList.Popular.toString());
                    intent.putExtra("product",model);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(itemProductModels != null) {
            return itemProductModels.size();
        } else if(kidSectionModels != null) {
            return kidSectionModels.size();
        } else {
            return popularSectionModels.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout viewRelative;
        CircleImageView circleImg;
        TextView nameTV, typeTV, priceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewRelative = itemView.findViewById(R.id.viewRelative);
            circleImg = itemView.findViewById(R.id.circleImg);
            nameTV = itemView.findViewById(R.id.nameTV);
            typeTV = itemView.findViewById(R.id.typeTV);
            priceTV = itemView.findViewById(R.id.priceTV);
        }
    }

}
