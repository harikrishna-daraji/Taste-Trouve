package com.example.tastetrouve.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
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
import com.example.tastetrouve.Models.FilterCatergoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View root;
    RecyclerView topSellingRecycle, kidMenuRecycle, restaurantRecycle;
    HomeProductModel homeProductModel;
    TextView cartCountTV;
    SharedPreferences sharedPreferences;

    TextView seekText;
    List<ItemProductModel> itemProductModels;
    List<ItemProductModel> filteredProductList = new ArrayList<>();


    String MainId = "60c83e1257e0183c050a7222";
    String AppetizerId = "60c84564b91443700feb8e6e";
    String DesertId = "60d50517b953042a6db7b0a4";


    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("TAG","TAG: Resume called");
        getCartCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.home_fragment_xml, container, false);
        initUI();
        getAllProducts();
        getHomeProducts();
        getCartCount();
        return root;
    }

    private void getCartCount() {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getCartCount(token).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            JSONObject finalResponse = new JSONObject(response.body().string());
                            int count = finalResponse.getInt("cart");
                            cartCountTV.setText(String.valueOf(count));
                            Log.i("TAG","TAG: Cart count arrived");
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ex.getMessage());
            }
        }
    }

    private void initUI() {

        root.findViewById(R.id.cartImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.filterIcon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //stored

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getContext(),R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(R.layout.bottom_filter_layout,getActivity().findViewById(R.id.bottomSheetContainer)
                        );
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                String[] items = new String[] {"Select...","Low To High", "High To Low"};
                Spinner spin = bottomSheetView.findViewById(R.id.spinnerPrice);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);

                String[] item = new String[] {"Select...","Main-Course", "Appetizer","Desserts"};

                Spinner spin1 = bottomSheetView.findViewById(R.id.spinner);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                 android.R.layout.simple_spinner_item, item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin1.setAdapter(adapter1);

                SeekBar seekBar = bottomSheetView.findViewById(R.id.seekBar);
                seekText = bottomSheetView.findViewById(R.id.textseekbar);
                seekText.setText("0");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        seekText.setText(progress+"");
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                bottomSheetView.findViewById(R.id.updateButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String pricespinner = spin.getSelectedItem().toString();
                        Log.i("toast", "onClick: "+pricespinner);

                        String catogeryspinner = spin1.getSelectedItem().toString();
                        Log.i("toast", "onClick: "+catogeryspinner);

                        String price = seekText.getText().toString();

                        RadioGroup radioGroup = bottomSheetView.findViewById(R.id.RadioGroup);
                        int radioId = radioGroup.getCheckedRadioButtonId();

                        RadioButton radioButton = bottomSheetView.findViewById(radioId);
                        String radio = radioButton.getText().toString();
                        Log.i("toast", "onClick: "+radio);

                        if(catogeryspinner == "Appetizer"){
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("section", GlobalObjects.Category.appetizer.toString());
                            for(CategoryModel model: homeProductModel.getCategoryObject()) {
                                if(model.getName().equals("Appetizers")) {
                                    intent.putExtra("categoryId",model.get_id());
                                    intent.putExtra("sort",pricespinner);
                                    intent.putExtra("radio",radio);
                                    intent.putExtra("price",price);
                                    break;
                                }
                            }
                            startActivity(intent);
                        }

                        if(catogeryspinner == "Main-Course"){
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("section", GlobalObjects.Category.main_course.toString());

                            for(CategoryModel model: homeProductModel.getCategoryObject()) {
                                if(model.getName().equals("Main Course")) {
                                    intent.putExtra("categoryId",model.get_id());
                                    intent.putExtra("sort",pricespinner);
                                    intent.putExtra("radio",radio);
                                    intent.putExtra("price",price);
                                    break;
                                }
                            }
                            startActivity(intent);
                        }

                        if(catogeryspinner == "Desserts"){
                            Intent intent = new Intent(getActivity(), ItemActivity.class);
                            intent.putExtra("section", GlobalObjects.Category.dessert.toString());

                            for(CategoryModel model: homeProductModel.getCategoryObject()) {
                                if(model.getName().equals("Dessert")) {
                                    intent.putExtra("categoryId",model.get_id());
                                    intent.putExtra("sort",pricespinner);
                                    intent.putExtra("radio",radio);
                                    intent.putExtra("price",price);
                                    break;
                                }
                            }
                            startActivity(intent);
                        }
                    }
                });
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

    private void getAllProducts() {
        String token = getUserToken();
        if (!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getAllProducts(token).enqueue(new Callback<List<ItemProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ItemProductModel>> call, Response<List<ItemProductModel>> response) {
                        try {
                            Log.i("TAG", "TAG Code: " + response.code() + " Message: " + response.message());
                            if (response.code() == 200) {
                                itemProductModels = response.body();
                                Log.i("TAG", "TAG: All Product Size: " + itemProductModels.size());
                            }
                        } catch (Exception ex) {
                            Log.i("TAG", "TAG " + ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ItemProductModel>> call, Throwable t) {
                        Log.i("TAG", "TAG: " + t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG", "TAG " + ex.getMessage());
            }
        }
    }
}
