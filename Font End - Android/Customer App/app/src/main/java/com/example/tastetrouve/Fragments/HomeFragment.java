package com.example.tastetrouve.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tastetrouve.Activities.CartActivity;
import com.example.tastetrouve.Activities.HomeActivity;
import com.example.tastetrouve.Activities.ItemActivity;
import com.example.tastetrouve.Adapters.KidMenuRecycleAdapter;
import com.example.tastetrouve.Adapters.RestaurantRecycleAdapter;
import com.example.tastetrouve.Adapters.TopSellingRecycleAdapter;
import com.example.tastetrouve.HelperClass.AbsolutefitLayourManager;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Interfaces.HomeInterfaceMethods;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View root;
    RecyclerView topSellingRecycle, kidMenuRecycle, restaurantRecycle;
    HomeProductModel homeProductModel;
    TextView cartCountTV;
    SharedPreferences sharedPreferences;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getActivity().getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
        int count = sharedPreferences.getInt("cart_count",0);
        if(count ==  1) {
            int temp_Count =  Integer.parseInt(homeProductModel.getCart());
            temp_Count = temp_Count + 1;
            cartCountTV.setText(temp_Count+"");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("cart_count",0);
            editor.apply();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.home_fragment_xml, container, false);
        initUI();
        getHomeProducts();
        return root;
    }

    private void initUI() {

        root.findViewById(R.id.cartImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.appetizerLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.appetizer.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Appetizers")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        root.findViewById(R.id.main_courseLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.main_course.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Main Course")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        root.findViewById(R.id.dessertLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ItemActivity.class);
                intent.putExtra("section", GlobalObjects.Category.dessert.toString());
                for(CategoryModel model: homeProductModel.getCategoryObject()) {
                    if(model.getName().equals("Dessert")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        root.findViewById(R.id.viewAllKidTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ItemActivity.class);
                intent.putStringArrayListExtra(GlobalObjects.ModelList.Kid.toString(),(ArrayList)homeProductModel.getKidsSection());
                startActivity(intent);
            }
        });

        root.findViewById(R.id.viewAllTopSellingTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ItemActivity.class);
                intent.putStringArrayListExtra(GlobalObjects.ModelList.Popular.toString(),(ArrayList)homeProductModel.getPopular());
                startActivity(intent);
            }
        });

        root.findViewById(R.id.searchLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeInterfaceMethods homeInterfaceMethods = (HomeInterfaceMethods) getActivity();
                homeInterfaceMethods.openSearchRelative();
            }
        });

        cartCountTV = root.findViewById(R.id.cartCountTV);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        topSellingRecycle = root.findViewById(R.id.topSellingRecycle);
        topSellingRecycle.setLayoutManager(gridLayoutManager);


        AbsolutefitLayourManager absolutefitLayourManager = new AbsolutefitLayourManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
        kidMenuRecycle = root.findViewById(R.id.kidsMenuRecycle);
        kidMenuRecycle.setLayoutManager(absolutefitLayourManager);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        restaurantRecycle = root.findViewById(R.id.restaurantRecycle);
        restaurantRecycle.setLayoutManager(linearLayoutManager);
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

    private void getHomeProducts() {
        String token = getUserToken();
        Log.i("TAG","TAG: Token id: "+token);
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getHomeProduct(token).enqueue(new Callback<HomeProductModel>() {
                    @Override
                    public void onResponse(Call<HomeProductModel> call, Response<HomeProductModel> response) {
                        try {
                            if(response.code() == 200) {
                                homeProductModel = response.body();
                                topSellingRecycle.setAdapter(new TopSellingRecycleAdapter(getActivity(),homeProductModel.getPopular()));
                                kidMenuRecycle.setAdapter(new KidMenuRecycleAdapter(getActivity(),homeProductModel.getKidsSection()));
                                restaurantRecycle.setAdapter(new RestaurantRecycleAdapter(getActivity(),homeProductModel.getRestaurants(),homeProductModel.getCategoryObject()));
                                cartCountTV.setText(homeProductModel.getCart());
                            } else {
                                Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeProductModel> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

}
