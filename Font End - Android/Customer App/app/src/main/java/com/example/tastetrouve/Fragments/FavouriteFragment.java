package com.example.tastetrouve.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Adapters.FavouriteAdapter;
import com.example.tastetrouve.Adapters.KidMenuRecycleAdapter;
import com.example.tastetrouve.Adapters.RestaurantRecycleAdapter;
import com.example.tastetrouve.Adapters.TopSellingRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavouriteFragment extends Fragment {

    private View root;
    TextView noResultTV;
    List<FavouriteModel> favouriteModelList;
    RecyclerView favRecycler;
    FavouriteModel favouriteModel;
    public FavouriteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.favourite_fragment_xml, container, false);
        favRecycler = root.findViewById(R.id.favouriteRecycler);
        noResultTV = root.findViewById(R.id.noResultTV);
        favRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        getFavouriteProducts();
        return root;
    }

    private void getFavouriteProducts() {
        String token = getUserToken();
        Log.i("TAG","TAG: Token id: "+token);
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getFavouriteItems(token).enqueue(new Callback<List<FavouriteModel>>() {
                    @Override
                    public void onResponse(Call<List<FavouriteModel>> call, Response<List<FavouriteModel>> response) {
                        try {
                            if(response.code() == 200) {
                                favouriteModelList = response.body();
                                if(favouriteModelList.size() > 0) {
                                    noResultTV.setVisibility(View.GONE);
                                    favRecycler.setVisibility(View.VISIBLE);
                                    favRecycler.setAdapter(new FavouriteAdapter(favouriteModelList,getActivity()));
                                } else {
                                    noResultTV.setVisibility(View.VISIBLE);
                                    favRecycler.setVisibility(View.GONE);
                                }


                            } else {
                                Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FavouriteModel>> call, Throwable t) {
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

    public void refreshFavouriteList() {
        getFavouriteProducts();
    }

}
