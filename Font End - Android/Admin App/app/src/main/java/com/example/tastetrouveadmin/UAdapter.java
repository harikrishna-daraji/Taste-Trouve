package com.example.tastetrouveadmin;

import android.content.Context;
import android.util.Log;
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
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                changeStatus(uDataList.get_id(),"accepted",position,holder);

            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatus(uDataList.get_id(),"declined",position,holder);
            }
        });
    }

    private void changeStatus(String id, String accepted, int position, ViewHolder holder) {
        Call<List<UData>> call = APIClient.getInstance().getApi().updateRestaurantStatus(id,accepted);

        call.enqueue(new Callback<List<UData>>() {
            @Override
            public void onResponse(Call<List<UData>> call, Response<List<UData>> response) {

                Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<UData>> call, Throwable t) {
                //   Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("aa", t.getMessage().toString());
            }


        });


        uData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, uData.size());
        holder.itemView.setVisibility(View.GONE);
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
