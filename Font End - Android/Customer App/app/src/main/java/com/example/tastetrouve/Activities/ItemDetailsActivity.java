package com.example.tastetrouve.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.BaseActivity;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.KidSectionModel;
import com.example.tastetrouve.Models.PopularSectionModel;
import com.example.tastetrouve.R;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    ImageView itemImg;
    int quantity = 1, totalQuantity;
    TextView itemNameTV, kidPriceTV, deliveryTV, calorieTV, descriptionTV, quantityTV, adjustableQuantTV;
    String productId, restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_item_details);
        initUI();
        manageIntent();
    }

    private void initUI() {
        itemImg = findViewById(R.id.itemImg);
        itemNameTV = findViewById(R.id.itemNameTV);
        kidPriceTV = findViewById(R.id.kidPriceTV);
        deliveryTV = findViewById(R.id.deliveryTV);
        calorieTV = findViewById(R.id.calorieTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        quantityTV = findViewById(R.id.quantityTV);
        adjustableQuantTV = findViewById(R.id.adjustableQuantTV);
        findViewById(R.id.addCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < totalQuantity) {
                    quantity = quantity + 1;
                    adjustableQuantTV.setText(String.valueOf(quantity));
                }
            }
        });
        findViewById(R.id.minusCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1) {
                    quantity = quantity - 1;
                    adjustableQuantTV.setText(String.valueOf(quantity));
                }
            }
        });

        findViewById(R.id.addToCartLinear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void manageIntent() {
        if(getIntent().hasExtra("product") && getIntent().hasExtra("type")) {
            if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Item.toString())) {
                ItemProductModel model = (ItemProductModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
                productId = model.get_id();
                restaurantId = model.getRestaurantId();
            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Popular.toString())) {
                PopularSectionModel model = (PopularSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                Log.i("TAG","TAG: Product idP "+model.get_id());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
                productId = model.get_id();
                restaurantId = model.getRestaurantId();
            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Kid.toString())) {
                KidSectionModel model = (KidSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
                productId = model.get_id();
                restaurantId = model.getRestaurantId();
            }
        }
        Log.i("TAG","TAG "+descriptionTV.getText().toString());
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

    private void addToCart() {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.addToCart(token,productId,String.valueOf(quantity),restaurantId).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                            if(response.code() == 200) {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String message = jsonObject.getString("message");
                                GlobalObjects.Toast(getBaseContext(),message);
                                if(!message.equalsIgnoreCase("item quantity is updated")) {
                                    sharedPreferences = getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("cart_count",1);
                                    editor.apply();
                                }
                            } else {
                                GlobalObjects.Toast(getBaseContext(),getString(R.string.cart_failure));
                            }
                        } catch (Exception ex) {
                            Log.i("TAG","TAG "+ex.getMessage());
                            GlobalObjects.Toast(getBaseContext(),getString(R.string.cart_failure));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("TAG","TAG: Server failure: "+t.getMessage());
                        GlobalObjects.Toast(getBaseContext(),getString(R.string.cart_failure));
                    }
                });
            } catch (Exception ex) {
                Log.i("TAG","TAG "+ ex.getMessage());
            }
        }
    }

}