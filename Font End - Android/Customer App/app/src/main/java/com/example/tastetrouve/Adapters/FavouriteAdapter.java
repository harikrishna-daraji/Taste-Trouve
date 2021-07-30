package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.ItemDetailsActivity;
import com.example.tastetrouve.Activities.OrderDetail;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.R;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {


    List<FavouriteModel> favouriteModelList;
    Context context;


    public FavouriteAdapter(List<FavouriteModel> favouriteModelList, Context context) {
        this.favouriteModelList = favouriteModelList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView circleImg;
        TextView nameTV;
        TextView priceTV;
        TextView description;
        LinearLayout favContainer;



        Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.circleImg= itemview.findViewById(R.id.circleImg);
            this.nameTV= itemview.findViewById(R.id.nameTV);
            this.priceTV= itemview.findViewById(R.id.priceTV);
            this.description= itemview.findViewById(R.id.description);
            this.favContainer= itemview.findViewById(R.id.favContainer);




        }
    }



    @NonNull
    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_favourite,parent,false);
        FavouriteAdapter.MyViewHolder myViewHolder= new FavouriteAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.MyViewHolder holder, final int position) {

        TextView nameTV=holder.nameTV;
        TextView priceTV=holder.priceTV;
        TextView description=holder.description;

        ImageView circleImg=holder.circleImg;
        LinearLayout favContainer=holder.favContainer;







        nameTV.setText(favouriteModelList.get(position).getProductId().getName());
        priceTV.setText("$ "+favouriteModelList.get(position).getProductId().getPrice());
        description.setText(favouriteModelList.get(position).getProductId().getDescription());

        Glide.with(context)
                .asBitmap()
                .load(favouriteModelList.get(position).getProductId().getImage())
                .into(circleImg);


        favContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favouriteModelList.get(position).getProductId().setFavourite("true");
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("type", GlobalObjects.ModelList.Item.toString());
                intent.putExtra("product",favouriteModelList.get(position).getProductId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return favouriteModelList.size();
    }
}


