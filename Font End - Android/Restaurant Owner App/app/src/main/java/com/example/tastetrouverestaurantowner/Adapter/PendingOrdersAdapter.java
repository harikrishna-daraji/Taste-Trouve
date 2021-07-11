package com.example.tastetrouverestaurantowner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Activity.DriversActivity;
import com.example.tastetrouverestaurantowner.Activity.MainActivity;
import com.example.tastetrouverestaurantowner.Activity.UpdateItemActivity;
import com.example.tastetrouverestaurantowner.ApiInterface;
import com.example.tastetrouverestaurantowner.Modal.PendingOrderModal;
import com.example.tastetrouverestaurantowner.Modal.ProductOrderModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingOrdersAdapter extends RecyclerView.Adapter<PendingOrdersAdapter.MyViewHolder> {

    private  RecyclerView.RecycledViewPool viewPool=new RecyclerView.RecycledViewPool();
    List<PendingOrderModal> pendingOrderModalArrayList;
    Context context;
    ApiInterface api;

    public PendingOrdersAdapter(List<PendingOrderModal> pendingOrderModalArrayList, Context context) {
        this.pendingOrderModalArrayList = pendingOrderModalArrayList;
        this.context = context;
    }



    public  static class MyViewHolder extends RecyclerView.ViewHolder
    {


        TextView orderId;
        TextView orderTotal;
        TextView orderDate;
        TextView orderCount;
        TextView address;
        TextView userName;
        TextView userPhone;
        TextView status;
        RecyclerView pendingOrderRecycler;

   Button accept_order_button,decline_order_button;

        MyViewHolder (View itemview)
        {
            super(itemview);

            this.orderId=(TextView) itemview.findViewById(R.id.orderId);
            this.orderTotal=(TextView)itemview.findViewById(R.id.orderTotal);
            this.orderDate = (TextView)itemview.findViewById(R.id.orderDate);
            this.orderCount = (TextView)itemview.findViewById(R.id.orderCount);
            this.address = (TextView)itemview.findViewById(R.id.address);
            this.userName = (TextView)itemview.findViewById(R.id.userName);
            this.userPhone = (TextView)itemview.findViewById(R.id.userPhone);
            this.pendingOrderRecycler = (RecyclerView)itemview.findViewById(R.id.produtREcycler);
            this.status = (TextView)itemview.findViewById(R.id.status);
            this.accept_order_button = (Button) itemview.findViewById(R.id.accept_order_button);
            this.decline_order_button = (Button) itemview.findViewById(R.id.decline_order_button);
        }
    }



    @NonNull
    @Override
    public PendingOrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= LayoutInflater.from(parent.getContext());
        View view=li.inflate(R.layout.item_pending_order,parent,false);
        PendingOrdersAdapter.MyViewHolder myViewHolder= new PendingOrdersAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingOrdersAdapter.MyViewHolder holder, final int position) {

        TextView orderId=holder.orderId;
        TextView orderTotal=holder.orderTotal;
        TextView orderDate=holder.orderDate;
        TextView orderCount=holder.orderCount;
        TextView address=holder.address;
        TextView userName=holder.userName;
        TextView userPhone=holder.userPhone;
        TextView status=holder.status;
        TextView accept_order_button=holder.accept_order_button;
        TextView decline_order_button=holder.decline_order_button;


        // Add sub recycler view

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.pendingOrderRecycler.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(pendingOrderModalArrayList.get(position).getProductOrderModalList().size());

        // Create sub item view adapter
        ProductOrderAdapter subItemAdapter = new ProductOrderAdapter((ArrayList<ProductOrderModal>) pendingOrderModalArrayList.get(position).getProductOrderModalList(),context);

        holder.pendingOrderRecycler.setLayoutManager(layoutManager);
        holder.pendingOrderRecycler.setAdapter(subItemAdapter);
        holder.pendingOrderRecycler.setRecycledViewPool(viewPool);


        orderId.setText(pendingOrderModalArrayList.get(position).getOrderId());
        orderTotal.setText("$ "+pendingOrderModalArrayList.get(position).getTotalPrice());
        orderDate.setText(pendingOrderModalArrayList.get(position).getOrderDate());

        orderCount.setText(pendingOrderModalArrayList.get(position).getItemCount()+" items");

        address.setText(pendingOrderModalArrayList.get(position).getAddressId().address);
        userName.setText(pendingOrderModalArrayList.get(position).getUserId().displayname);

        userPhone.setText(pendingOrderModalArrayList.get(position).getUserId().phoneNumber);
        status.setText(pendingOrderModalArrayList.get(position).getStatus());

        accept_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = APIClient.getInstance().getApi().updateOrderStatus(pendingOrderModalArrayList.get(position).getOrderId(),"accepted");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Order Accepted", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(context, DriversActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                pendingOrderModalArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pendingOrderModalArrayList.size());
                holder.itemView.setVisibility(View.GONE);
            }
        });


        decline_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = APIClient.getInstance().getApi().updateOrderStatus(pendingOrderModalArrayList.get(position).getOrderId(),"declined");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, "Order Rejected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                pendingOrderModalArrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, pendingOrderModalArrayList.size());
                holder.itemView.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return pendingOrderModalArrayList.size();
    }
}

