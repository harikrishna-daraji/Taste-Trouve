package com.example.tastetrouveadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UAdapter extends RecyclerView.Adapter<UAdapter.ViewHolder> {

    ArrayList<UData> uData;
    Context context;

    public UAdapter(ArrayList<UData> uData, MainActivity activity){
        this.uData = uData;
        this.context = activity;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final UData uDataList = uData.get(position);
        holder.Name.setText(uDataList.getRestaurantName());
        holder.Address.setText(uDataList.getAddress());
        holder.Email.setText(uDataList.getEmail());
        holder.Phone.setText(uDataList.getPhoneNumber());



        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accept Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Reject Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return uData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView Name,Address,Email,Phone;
        Button Accept, Reject;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            Name = itemView.findViewById(R.id.itemPersonName);
            Address = itemView.findViewById(R.id.itemAddress);
            Email = itemView.findViewById(R.id.itemEmail);
            Phone = itemView.findViewById(R.id.itemPhone);
            Accept = itemView.findViewById(R.id.itemAccept);
            Reject = itemView.findViewById(R.id.itemReject);

        }
    }
}
