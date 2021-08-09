package com.example.tastetrouve.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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

import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    ImageView itemImg;
    int quantity = 1, totalQuantity;
    TextView itemNameTV, kidPriceTV, deliveryTV, calorieTV, descriptionTV, quantityTV, adjustableQuantTV,favouriteText;
    String productId, restaurantId,favoriteValue;
    ItemProductModel itemProductModel;
    PopularSectionModel popularSectionModel;
    KidSectionModel kidSectionModel;
    RelativeLayout favoutite;
    RelativeLayout shareRelative;
    String shareUrl,shareProductName;
    int index=0;

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
        favoutite = findViewById(R.id.favourite);
        favouriteText = findViewById(R.id.favText);
        shareRelative = findViewById(R.id.shareRelative);
        favoutite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toogleFavourite();
            }
        });
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

        shareRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Glide.with(getApplicationContext())
                        .asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE) .load(shareUrl)

                        .into(new SimpleTarget< Bitmap >(250, 250) {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, shareProductName);
                                String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
                                Log.i("quoteswahttodo", "is onresoursereddy" + path);

                                Uri screenshotUri = Uri.parse(path);

                                Log.i("quoteswahttodo", "is onresoursereddy" + screenshotUri);

                                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                intent.setType("image/*");

                                startActivity(Intent.createChooser(intent, "Share image via..."));
                            }


                        });
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
        if(getIntent().hasExtra("index")) {
            index = getIntent().getIntExtra("index",0);
        }
        if(getIntent().hasExtra("product") && getIntent().hasExtra("type")) {
            if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Item.toString())) {
                itemProductModel = (ItemProductModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(itemProductModel.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(itemProductModel.getName());
                kidPriceTV.setText("$"+itemProductModel.getPrice());
                deliveryTV.setText(itemProductModel.getDeliveryTime());
                calorieTV.setText(itemProductModel.getCalories());
                descriptionTV.setText(itemProductModel.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+itemProductModel.getQuantity());
                totalQuantity = itemProductModel.getQuantity();
                productId = itemProductModel.get_id();
                restaurantId = itemProductModel.getRestaurantId();
                favoriteValue=itemProductModel.getFavourite();
                Log.d("TAG", "favoriteValue "+favoriteValue);
                favoutite.setBackgroundColor(
                        favoriteValue.equals("true")?
                        Color.parseColor("#F3E4E2"):
                                Color.parseColor("#DDF8DE") );
                favouriteText.setTextColor(favoriteValue.equals("true")?
                        Color.parseColor("#BC180C"):
                        Color.parseColor("#036707") );

                favouriteText.setText(favoriteValue.equals("true")? getString(R.string.remove_from_favourite):getString(R.string.add_to_favorite));

                shareUrl=itemProductModel.getImage();
                shareProductName=itemProductModel.getName();

            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Popular.toString())) {
                popularSectionModel = (PopularSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(popularSectionModel.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(popularSectionModel.getName());
                Log.i("TAG","TAG: Product idP "+popularSectionModel.get_id());
                kidPriceTV.setText("$"+popularSectionModel.getPrice());
                deliveryTV.setText(popularSectionModel.getDeliveryTime());
                calorieTV.setText(popularSectionModel.getCalories());
                descriptionTV.setText(popularSectionModel.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+popularSectionModel.getQuantity());
                totalQuantity = popularSectionModel.getQuantity();
                productId = popularSectionModel.get_id();
                restaurantId = popularSectionModel.getRestaurantId();
                favoriteValue=popularSectionModel.getFavourite();
                Log.d("TAG", "favoriteValue "+favoriteValue);
                favoutite.setBackgroundColor(
                        favoriteValue.equals("true")?
                                Color.parseColor("#F3E4E2"):
                                Color.parseColor("#DDF8DE") );
                favouriteText.setTextColor(favoriteValue.equals("true")?
                        Color.parseColor("#BC180C"):
                        Color.parseColor("#036707") );

                favouriteText.setText(favoriteValue.equals("true")? getString(R.string.remove_from_favourite):getString(R.string.add_to_favorite));
                shareUrl=popularSectionModel.getImage();
                shareProductName=popularSectionModel.getName();

            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Kid.toString())) {
                kidSectionModel = (KidSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(kidSectionModel.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(kidSectionModel.getName());
                kidPriceTV.setText("$"+kidSectionModel.getPrice());
                deliveryTV.setText(kidSectionModel.getDeliveryTime());
                calorieTV.setText(kidSectionModel.getCalories());
                descriptionTV.setText(kidSectionModel.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+kidSectionModel.getQuantity());
                totalQuantity = kidSectionModel.getQuantity();
                productId = kidSectionModel.get_id();
                restaurantId = kidSectionModel.getRestaurantId();
                favoriteValue=kidSectionModel.getFavourite();
                Log.d("TAG", "favoriteValue "+favoriteValue);
                favoutite.setBackgroundColor(
                        favoriteValue.equals("true")?
                                Color.parseColor("#F3E4E2"):
                                Color.parseColor("#DDF8DE") );
                favouriteText.setTextColor(favoriteValue.equals("true")?
                        Color.parseColor("#BC180C"):
                        Color.parseColor("#036707") );

                favouriteText.setText(favoriteValue.equals("true")? getString(R.string.remove_from_favourite):getString(R.string.add_to_favorite));

                shareUrl=kidSectionModel.getImage();
                shareProductName=kidSectionModel.getName();

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


    private void toogleFavourite() {
        String token = getUserToken();
        if(!token.isEmpty()) {
            try {
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                apiInterface.toggleFav(token,productId,favoriteValue).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                            if(response.code() == 200) {

                                if(favoriteValue.equals("true"))
                                    favoriteValue="false";
                                else
                                    favoriteValue="true";
                                Log.d("fav", favoriteValue);
                                favoutite.setBackgroundColor(
                                        favoriteValue.equals("true")?
                                                Color.parseColor("#F3E4E2"):
                                                Color.parseColor("#DDF8DE") );
                                favouriteText.setTextColor(favoriteValue.equals("true")?
                                        Color.parseColor("#BC180C"):
                                        Color.parseColor("#036707") );

                                favouriteText.setText(favoriteValue.equals("true")? getString(R.string.remove_from_favourite):getString(R.string.add_to_favorite));
                                if(popularSectionModel != null) {
                                    popularSectionModel.setFavourite(favoriteValue);
                                } else if(kidSectionModel != null) {
                                    kidSectionModel.setFavourite(favoriteValue);
                                } else if(itemProductModel != null) {
                                    itemProductModel.setFavourite(favoriteValue);
                                }
                            } else {

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

    @Override
    public void onBackPressed() {
        if(popularSectionModel != null) {
            Intent intent = new Intent();
            intent.putExtra("model",popularSectionModel);
            intent.putExtra("index",index);
            setResult(GlobalObjects.POPULAR_LIST_REFRESH,intent);
        } else if(kidSectionModel != null) {
            Intent intent = new Intent();
            intent.putExtra("model",kidSectionModel);
            intent.putExtra("index",index);
            setResult(GlobalObjects.KIDS_LIST_REFRESH,intent);
        } else if(itemProductModel != null) {
            Intent intent = new Intent();
            intent.putExtra("model",itemProductModel);
            intent.putExtra("index",index);
            setResult(GlobalObjects.ITEM_LIST_REFRESH_CODE,intent);
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG","TAG: Item activity result called");
    }
}
