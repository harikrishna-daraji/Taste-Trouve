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
import com.example.tastetrouve.Models.PopularSectionModel;
import com.example.tastetrouve.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TopSellingRecycleAdapter  extends RecyclerView.Adapter<TopSellingRecycleAdapter.ViewHolder>  {

    Activity activity;
    List<PopularSectionModel> popularSectionModels;

    public TopSellingRecycleAdapter(Activity activity, List<PopularSectionModel> popularSectionModels) {
        this.activity = activity;
        this.popularSectionModels = popularSectionModels;
    }

    @NonNull
    @Override
    public TopSellingRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topselling_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopSellingRecycleAdapter.ViewHolder holder, int position) {
        PopularSectionModel model = popularSectionModels.get(position);
        holder.name.setText(model.getName());
        Glide.with(activity).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.circleImg);
        holder.price.setText("$"+String.valueOf(model.getPrice()));
        holder.viewRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ItemDetailsActivity.class);
                intent.putExtra("product",model);
                intent.putExtra("type", GlobalObjects.ModelList.Popular.toString());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularSectionModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout viewRelative;
        TextView name, price;
        CircleImageView circleImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewRelative = itemView.findViewById(R.id.viewRelative);
            name = itemView.findViewById(R.id.topItemNameTV);
            price = itemView.findViewById(R.id.topItemPriceTV);
            circleImg = itemView.findViewById(R.id.circleImage);
        }
    }

}
