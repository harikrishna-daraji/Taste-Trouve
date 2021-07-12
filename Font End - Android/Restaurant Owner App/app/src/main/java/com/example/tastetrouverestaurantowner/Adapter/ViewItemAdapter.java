package com.example.tastetrouverestaurantowner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Activity.UpdateItemActivity;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewItemAdapter extends RecyclerView.Adapter<ViewItemAdapter.MyViewHolder> {

    ArrayList<ProductModal> productModalArrayList;
    Context context;
    ApiInterface api;

    public ViewItemAdapter(ArrayList<ProductModal> productModalArrayList, Context context) {
        this.productModalArrayList = productModalArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        ImageView image;
        ImageView edit;
        ImageView delete;
        TextView name;
        TextView price;
        TextView calories;


        MyViewHolder (View itemview)
        {
            super(itemview);

            this.image=(ImageView) itemview.findViewById(R.id.image);
            this.edit=(ImageView) itemview.findViewById(R.id.edit);
            this.delete=(ImageView) itemview.findViewById(R.id.delete);
            this.name=(TextView)itemview.findViewById(R.id.name);
            this.price = (TextView)itemview.findViewById(R.id.price);
            this.calories = (TextView)itemview.findViewById(R.id.calories);
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_product,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView name=holder.name;
        TextView price=holder.price;
        TextView calories=holder.calories;
        ImageView image=holder.image;
        ImageView edit=holder.edit;
        ImageView delete=holder.delete;

        name.setText(productModalArrayList.get(position).getName());
        price.setText("$ "+productModalArrayList.get(position).getPrice().toString());
        calories.setText(productModalArrayList.get(position).getCalories()+" kcal");

        Glide.with(context).load(productModalArrayList.get(position).getImage()).placeholder(R.drawable.imageplaceholder).into(image);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Call<List<ProductModal>> call = APIClient.getInstance().getApi().deleteProduct(productModalArrayList.get(position).get_id());

                    call.enqueue(new Callback<List<ProductModal>>() {
                        @Override
                        public void onResponse(Call<List<ProductModal>> call, Response<List<ProductModal>> response) {

                   Toast.makeText(context, "Product deleted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<ProductModal>> call, Throwable t) {
                            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                        }


                    });


                    productModalArrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, productModalArrayList.size());
                    holder.itemView.setVisibility(View.GONE);
                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(context, UpdateItemActivity.class);
                    intent.putExtra("productData",  productModalArrayList.get(position));
                    context.startActivity(intent);
                }
            });


    }


    @Override
    public int getItemCount() {
        return productModalArrayList.size();
    }
}

