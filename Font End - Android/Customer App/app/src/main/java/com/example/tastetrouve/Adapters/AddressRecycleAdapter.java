package com.example.tastetrouve.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Interfaces.AddressInterface;
import com.example.tastetrouve.Models.AddressModel;
import com.example.tastetrouve.R;

import java.util.List;

public class AddressRecycleAdapter extends RecyclerView.Adapter<AddressRecycleAdapter.ViewHolder>  {

    Activity activity;
    List<AddressModel> addressModelList;
    AddressInterface addressInterface;

    public AddressRecycleAdapter(Activity activity, List<AddressModel> addressModelList) {
        this.activity =  activity;
        this.addressModelList = addressModelList;
        this.addressInterface = (AddressInterface) activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_recycle_xml,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(addressModelList.get(position).getAddress());
        holder.closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressInterface.deleteAddress(addressModelList.get(position).get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        ImageView closeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.addressRecycleTV);
            closeImg = itemView.findViewById(R.id.closeImg);
        }
    }

}
