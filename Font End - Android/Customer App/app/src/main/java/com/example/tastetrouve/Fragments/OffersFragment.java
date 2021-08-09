package com.example.tastetrouve.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.tastetrouve.Activities.OfferContainerActivity;
import com.example.tastetrouve.Activities.OrderDetail;
import com.example.tastetrouve.Adapters.FavouriteAdapter;
import com.example.tastetrouve.Adapters.ItemRecycleAdapter;
import com.example.tastetrouve.Adapters.OfferAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersFragment extends Fragment {

 LinearLayout christmas,blackFriday,boxingDay,newYear;

    public OffersFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offers, container, false);
        christmas=view.findViewById(R.id.christmas);
        blackFriday=view.findViewById(R.id.blackFriday);
        boxingDay=view.findViewById(R.id.boxingDay);
        newYear=view.findViewById(R.id.newYear);

        christmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData("Christmas");
            }
        });

        blackFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData("Black Friday");
            }
        });

        boxingDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData("Boxing Day");
            }
        });

        newYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showData("New Year");
            }
        });
        return  view;
    }

    private void showData(String christmas) {
        Intent intent =new Intent(getContext(), OfferContainerActivity.class);
        intent.putExtra("offer",christmas );
        getContext().startActivity(intent);
    }


}