package com.example.tastetrouve.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.ItemDetailsActivity;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.KidSectionModel;
import com.example.tastetrouve.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KidMenuRecycleAdapter extends RecyclerView.Adapter<KidMenuRecycleAdapter.ViewHolder> {

    Activity activity;
    List<KidSectionModel> kidSectionModelList;

    public KidMenuRecycleAdapter(Activity activity, List<KidSectionModel> kidSectionModelList) {
        this.activity = activity;
        this.kidSectionModelList = kidSectionModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kidmenu_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KidSectionModel model = kidSectionModelList.get(position);
        Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.circleImageView);
        holder.name.setText(model.getName());
        holder.price.setText("$"+String.valueOf(model.getPrice()));
        holder.type.setText(model.getName());
        holder.viewRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ItemDetailsActivity.class);
                intent.putExtra("product",model);
                intent.putExtra("type", GlobalObjects.ModelList.kid.toString());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kidSectionModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout viewRelative;
        CircleImageView circleImageView;
        TextView name, type, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewRelative = itemView.findViewById(R.id.viewRelative);
            circleImageView = itemView.findViewById(R.id.circleImg);
            name = itemView.findViewById(R.id.nameTV);
            type = itemView.findViewById(R.id.typeTV);
            price = itemView.findViewById(R.id.priceTV);
        }
    }

}
