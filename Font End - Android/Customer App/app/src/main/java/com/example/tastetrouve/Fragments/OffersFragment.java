package com.example.tastetrouve.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    List<ItemProductModel> itemProductModels;
    RecyclerView offerRecycler;


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
        offerRecycler = view.findViewById(R.id.specialOffersRecycler);

        offerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getSpecialOfferProducts();
        return  view;
    }

    private void getSpecialOfferProducts() {
        String token = getUserToken();
        Log.i("TAG","TAG: Token id: "+token);
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getSpecialProducts(token).enqueue(new Callback<List<ItemProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                        try {
                            if(response.code() == 200) {
                                itemProductModels = response.body();

                                offerRecycler.setAdapter(new OfferAdapter(itemProductModels,getContext()));

                            } else {
                                Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

    private String getUserToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AuthenticationTypes",getContext().MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }
}