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
//                ChangeStatus("accepted" ,uDataList.get_id());
                Call<ResponseBody> call = APIClient.getInstance().getApi().updateRestaurantStatus(uDataList.get_id(),"accepted ");

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

//                    @Override
//                    public void onResponse(Call<List<ResponseBody>> call, Response<List<ResponseBody>> response) {
//                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<ResponseBody>> call, Throwable t) {
//
//                        Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
//                    }


                });

                //Toast.makeText(context, "Accept Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Reject Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChangeStatus(String approved, String id) {
//        Call<List<ResponseBody>> call = APIClient.getInstance().getApi().updateRestaurantStatus(approved,id);
//
//        call.enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Toast.makeText(context,"Status Update", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
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
