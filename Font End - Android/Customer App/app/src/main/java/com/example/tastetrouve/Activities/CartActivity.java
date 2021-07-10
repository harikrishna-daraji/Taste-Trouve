package com.example.tastetrouve.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouve.Adapters.AddressRecycleAdapter;
import com.example.tastetrouve.Adapters.CartRecyclerAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Interfaces.CartInterface;
import com.example.tastetrouve.Models.AddressModel;
import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.Models.CartProductModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity  extends BaseActivity implements CartInterface, AdapterView.OnItemSelectedListener {

    private static final String KEY_CARD = "card";
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    List<CartModel> cartModelArrayList = new ArrayList<>();
    CartRecyclerAdapter cartRecyclerAdapter;
    TextView subTotalTV, texesTV, deliveryTV, totalTV;
    Button placeOrder;
    double subTotal = 0, total=0, taxes=0;
    List<AddressModel> addressModelList = new ArrayList<>();
    List<String> stringAddressList = new ArrayList<>();
    AddressModel selectedAddressModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_cart);
        initUI();
        getCartDetails();
        getAddressList();
    }


    private void initUI() {
        subTotalTV = findViewById(R.id.subTotalTV);
        texesTV = findViewById(R.id.texesTV);
        deliveryTV = findViewById(R.id.deliveryTV);
        totalTV = findViewById(R.id.totalTV);
        recyclerView = findViewById(R.id.cartlist);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        placeOrder = findViewById(R.id.PlaceOrder);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        placeOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sharedPreferences = getApplicationContext().getSharedPreferences("KEY_CARD",Context.MODE_PRIVATE);
                String card = sharedPreferences.getString("CardNumber",null);
                if(card == null){

                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                            CartActivity.this,R.style.BottomSheetDialogTheme
                    );
                    View bottomSheetView = LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.layout_bottom_sheet,findViewById(R.id.bottomSheetContainer)
                            );
                    bottomSheetView.findViewById(R.id.AddCard).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText name,card,expiry,cvv;

                            name = bottomSheetView.findViewById(R.id.CardName);
                            card = bottomSheetView.findViewById(R.id.cardNumberTV);
                            expiry = bottomSheetView.findViewById(R.id.CardExpiryDate);
                            cvv = bottomSheetView.findViewById(R.id.CardCVV);

                            String Sname = name.getText().toString();
                            String Scard = card.getText().toString();
                            String Sexpiry = expiry.getText().toString();
                            String Scvv = cvv.getText().toString();

                            Toast.makeText(CartActivity.this, "Add Card", Toast.LENGTH_SHORT).show();
                            sharedPreferences = getApplicationContext().getSharedPreferences("KEY_CARD",Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("CardName",Sname);
                            editor.putString("CardNumber",Scard);
                            editor.putString("CardExpiry",Sexpiry);
                            editor.putString("CardCvv",Scvv);
                            editor.apply();
                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                }else if(card != null){

                    BottomSheetDialog bottomSheetDialog1 = new BottomSheetDialog(
                            CartActivity.this,R.style.BottomSheetDialogTheme
                    );
                    View bottomSheetView1 = LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.layout_bottom_sheet1,findViewById(R.id.bottomSheetContainer)
                            );


                    TextView cardNumberTV =  bottomSheetView1.findViewById(R.id.cardNumberTV);
                    cardNumberTV.setText(card);

                    Spinner addressSpinner = bottomSheetView1.findViewById(R.id.addressSpinner);

                    TextView totalPriceTV = bottomSheetView1.findViewById(R.id.totalPriceTV);
                    totalPriceTV.setText(roundNumber(total));

                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CartActivity.this, android.R.layout.simple_spinner_item, stringAddressList);
                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    addressSpinner.setAdapter(spinnerAdapter);
                    addressSpinner.setOnItemSelectedListener(CartActivity.this);

                    bottomSheetView1.findViewById(R.id.confirmImg).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            prepareOrderParameters();
                        }
                    });


                    bottomSheetDialog1.setContentView(bottomSheetView1);
                    bottomSheetDialog1.show();

                }
            }
        });
    }

    private String getUserToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("AuthenticationTypes",MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("signUpDone",false);
        if(isLoggedIn) {
            String token = sharedPreferences.getString("token","");
            return token;
        } else {
            return "";
        }
    }

    private void getCartDetails() {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getUserCart(token).enqueue(new Callback<List<CartModel>>() {
                    @Override
                    public void onResponse(Call<List<CartModel>> call, Response<List<CartModel>> response) {
                        try {
                            cartModelArrayList = response.body();

                            for(CartModel cartModel: cartModelArrayList) {
                                CartProductModel cartProductModel = cartModel.getProductId();
                                subTotal = subTotal + cartProductModel.getPrice() * cartModel.getQuantity();
                            }
                            subTotalTV.setText(roundNumber(subTotal));
                            taxes = subTotal * 0.15;
                            texesTV.setText(roundNumber(taxes));
                            total = subTotal + taxes + 5;
                            totalTV.setText(roundNumber(total));
                            cartRecyclerAdapter= new CartRecyclerAdapter(cartModelArrayList,CartActivity.this);
                            recyclerView.setAdapter(cartRecyclerAdapter);
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CartModel>> call, Throwable t) {
                        Log.i("TAG","TAG: Server exception: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG: "+ex.getMessage());
            }
        }
    }

    private void getAddressList() {
        addressModelList.clear();
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.getAddressList(token).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                            if(response.code() == 200) {
                                JSONArray jsonArray = new JSONArray(response.body().string());
                                for(int index=0;index<jsonArray.length();index++) {
                                    AddressModel addressModel = new AddressModel(jsonArray.getJSONObject(index));
                                    stringAddressList.add(addressModel.getAddress());
                                    addressModelList.add(addressModel);
                                }
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG: Exception: "+ex.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG:  Server Failure: "+t.getMessage());
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG Exception: "+ex.getMessage());
            }
        }
    }

    private String roundNumber(double number) {
        DecimalFormat df = new DecimalFormat("####0.00");
        return "$"+df.format(number);
    }

    @Override
    public void refreshRecycle() {
        cartModelArrayList.clear();
        total = 0;
        subTotal = 0;
        taxes = 0;
        getCartDetails();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedAddressModel = addressModelList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private JSONArray prepareProductArray() {
        JSONArray jsonArray = new JSONArray();
        try {
            for(CartModel cartModel: cartModelArrayList) {
                jsonArray.put(cartModel.getProductId().prepareOrderModel());
            }
        } catch (Exception ex) {
            Log.i("TAG","TAG: Order Json array exception: "+ex.getMessage());
        }
        return jsonArray;
    }

    private void prepareOrderParameters() {
        try {
            String userId = getUserToken();
            String restaurantId = cartModelArrayList.get(0).getProductId().getRestaurantId();
            String addressId = selectedAddressModel.get_id();
            int delivery = 5;
            int tax = (int) taxes;
            int totalInt = (int) total;

            if(!userId.isEmpty() && !restaurantId.isEmpty() && !addressId.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId",userId);
                jsonObject.put("addressId",addressId);
                jsonObject.put("restaurantId",restaurantId);
                jsonObject.put("delivery",delivery);
                jsonObject.put("tax",tax);
                jsonObject.put("total",totalInt);
                jsonObject.put("Products",prepareProductArray());
                makeOrderApi(jsonObject);
            } else {
                Log.i("TAG","TAG: Parameters are empty");
            }
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

    private void makeOrderApi(JSONObject jsonObject) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.addOrder(jsonObject).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if(response.code() == 200) {
                            cartModelArrayList.clear();
                            cartRecyclerAdapter= new CartRecyclerAdapter(cartModelArrayList,CartActivity.this);
                            recyclerView.setAdapter(cartRecyclerAdapter);
                            GlobalObjects.Toast(getBaseContext(),getString(R.string.order_placed));
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("TAG","TAG "+t.getMessage());
                }
            });

        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }
}