package com.example.tastetrouvedriver.Helper.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouvedriver.Helper.APIClient;
import com.example.tastetrouvedriver.Helper.ApiInterface;
import com.example.tastetrouvedriver.Helper.Model.MyOrderModel;
import com.example.tastetrouvedriver.Helper.Model.PastOrderModel;
import com.example.tastetrouvedriver.MainActivity;
import com.example.tastetrouvedriver.PastOrdersAdapter;
import com.example.tastetrouvedriver.R;
import com.example.tastetrouvedriver.SIgnInActivity;
import com.example.tastetrouvedriver.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastOrderFragment extends Fragment {

    List<PastOrderModel> pastOrderModelList;
    RecyclerView pastRecyler;

    public PastOrderFragment() {
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
        View view= inflater.inflate(R.layout.past_order_xml, container, false);
        pastRecyler = view.findViewById(R.id.myOrderREcycler);

        pastRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        getPastOrders();
        return  view;
    }

    private void getPastOrders() {
        String token = getUserToken();
        Log.i("TAG", "TAG: Token id: " + token);
        if (!token.isEmpty()) {
            APIClient.getInstance().getApi().getPastOrder(token).enqueue(new Callback<List<PastOrderModel>>() {
                @Override
                public void onResponse(Call<List<PastOrderModel>> call, Response<List<PastOrderModel>> response) {
                    pastOrderModelList = (List<PastOrderModel>) response.body();
                    PastOrdersAdapter customAdapter= new PastOrdersAdapter(pastOrderModelList,getContext());
                    pastRecyler.setAdapter(customAdapter);
                }

                @Override
                public void onFailure(Call<List<PastOrderModel>> call, Throwable t) {

                }
            });



        }
    }


           private String getUserToken() {
            SharedPreferences   sharedPreferences = getActivity().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
            String token = sharedPreferences.getString("ownerId", "");
            return token;
        }





}
