package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.ItemDetailsActivity;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {


    List<ItemProductModel> ItemProductModel;
    Context context;


    public OfferAdapter(List<ItemProductModel> ItemProductModel, Context context) {
        this.ItemProductModel = ItemProductModel;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView circleImg;
        TextView nameTV;
        TextView priceTV;
        TextView description;
        TextView cutOffPrice;
        TextView offertext;
        LinearLayout favContainer;




        MyViewHolder (View itemview)
        {
            super(itemview);

            this.circleImg= itemview.findViewById(R.id.circleImg);
            this.nameTV= itemview.findViewById(R.id.nameTV);
            this.priceTV= itemview.findViewById(R.id.priceTV);
            this.description= itemview.findViewById(R.id.description);
            this.cutOffPrice= itemview.findViewById(R.id.offprice);
            this.favContainer= itemview.findViewById(R.id.favContainer);
            this.offertext= itemview.findViewById(R.id.offertext);




        }
    }



    @NonNull
    @Override
    public OfferAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_specialoffer,parent,false);
        OfferAdapter.MyViewHolder myViewHolder= new OfferAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.MyViewHolder holder, final int position) {

        TextView nameTV=holder.nameTV;
        TextView priceTV=holder.priceTV;
        TextView description=holder.description;
        TextView cutOffPrice=holder.cutOffPrice;
        TextView offertext=holder.offertext;

        ImageView circleImg=holder.circleImg;
        LinearLayout favContainer=holder.favContainer;





        nameTV.setText(ItemProductModel.get(position).getName());
        priceTV.setText("$ "+ItemProductModel.get(position).getPrice());
        description.setText(ItemProductModel.get(position).getDescription());
        offertext.setText(ItemProductModel.get(position).getSpecialType());
        cutOffPrice.setText("$ "+ItemProductModel.get(position).getCutOffPrice());
          cutOffPrice.setPaintFlags(cutOffPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(context)
                .asBitmap()
                .load(ItemProductModel.get(position).getImage())
                .into(circleImg);


        favContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.putExtra("type", GlobalObjects.ModelList.Item.toString());
                intent.putExtra("product",ItemProductModel.get(position));
                v.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ItemProductModel.size();
    }
}


