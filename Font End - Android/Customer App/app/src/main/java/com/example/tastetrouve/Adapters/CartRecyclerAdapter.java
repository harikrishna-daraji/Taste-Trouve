package com.example.tastetrouve.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Interfaces.CartInterface;
import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.Models.CartProductModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRecyclerAdapter  extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder> {

    List<CartModel> cartModelArrayList;
    Context context;
    CartInterface cartInterface;

    public CartRecyclerAdapter(List<CartModel> cartModelArrayList, Context context) {
        this.cartModelArrayList = cartModelArrayList;
        this.context = context;
        cartInterface = (CartInterface) context;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView name;
        TextView price;
        TextView quantity;
        ImageView image,add,minus;



        MyViewHolder (View itemview)
        {
            super(itemview);

            this.name=(TextView)itemview.findViewById(R.id.name);
            this.price=(TextView)itemview.findViewById(R.id.price);
            this.quantity=(TextView)itemview.findViewById(R.id.quantity);

            this.image=(ImageView)itemview.findViewById(R.id.image);
            this.add=(ImageView)itemview.findViewById(R.id.add);
            this.minus=(ImageView)itemview.findViewById(R.id.minus);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.cart_item,parent,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        TextView name=holder.name;
        TextView price=holder.price;
        TextView quantity=holder.quantity;
        ImageView image=holder.image;
        ImageView add=holder.add;
        ImageView minus=holder.minus;


        CartProductModel model = cartModelArrayList.get(position).getProductId();

        name.setText(model.getName());
        price.setText("$"+model.getPrice());
        quantity.setText(""+cartModelArrayList.get(position).getQuantity());

        Glide.with(context).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(holder.image);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartModelArrayList.get(position).getQuantity() < model.getQuantity()) {
                    cartModelArrayList.get(position).setQuantity(cartModelArrayList.get(position).getQuantity() + 1);
                    updateCart(cartModelArrayList.get(position).get_id(),String.valueOf(cartModelArrayList.get(position).getQuantity()),position, GlobalObjects.CartOperation.add);
                } else {
                    GlobalObjects.Toast(context,(cartModelArrayList.get(position).getQuantity()+1)+" "+context.getString(R.string.quantity_not_in_stock));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartModelArrayList.get(position).getQuantity() > 0) {
                    cartModelArrayList.get(position).setQuantity(cartModelArrayList.get(position).getQuantity() - 1);
                    if(cartModelArrayList.get(position).getQuantity() == 0) {
                        updateCart(cartModelArrayList.get(position).get_id(),String.valueOf(cartModelArrayList.get(position).getQuantity()),position,GlobalObjects.CartOperation.remove);
                    } else {
                        Log.i("TAG","TAG: else called");
                        updateCart(cartModelArrayList.get(position).get_id(),String.valueOf(cartModelArrayList.get(position).getQuantity()),position,GlobalObjects.CartOperation.minus);
                    }
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    private void updateCart(String cartId, String quantity, int index, GlobalObjects.CartOperation cartOperation) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.updateCart(cartId,quantity).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        Log.i("TAG","TAG: enum value "+cartOperation.toString());
                        if(response.code() == 200) {
                            cartInterface.refreshRecycle();
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("TAG","TAG: Server failure "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }
}
