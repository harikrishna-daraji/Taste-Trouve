package com.example.tastetrouve.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Activities.ItemDetailsActivity;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KeywordRecycleAdapter extends RecyclerView.Adapter<KeywordRecycleAdapter.ViewHolder> {

    List<ItemProductModel> list;
    Activity activity;

    public KeywordRecycleAdapter(Activity activity, List<ItemProductModel> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.keyword_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordRecycleAdapter.ViewHolder holder, int position) {
        holder.itemNameTV.setText(list.get(position).getName());
        holder.linearKeyword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ItemDetailsActivity.class);
                intent.putExtra("type", GlobalObjects.ModelList.Item.toString());
                intent.putExtra("product",list.get(position));
                intent.putExtra("index",position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("TAG","TAG:  it is called "+list.size());
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemNameTV;
        LinearLayout linearKeyword;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTV = itemView.findViewById(R.id.itemNameTV);
            linearKeyword = itemView.findViewById(R.id.linearKeyword);
        }
    }

}
