package com.example.tastetrouve;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KidMenuRecycleAdapter extends RecyclerView.Adapter<KidMenuRecycleAdapter.ViewHolder> {

    Context context;
    DisplayMetrics displayMetrics = new DisplayMetrics();
    int screenWidth = 0;

    KidMenuRecycleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kidmenu_recycle_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.viewRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ItemDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout viewRelative;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewRelative = itemView.findViewById(R.id.viewRelative);
        }
    }

}
