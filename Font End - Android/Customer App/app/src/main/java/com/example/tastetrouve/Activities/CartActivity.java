package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.tastetrouve.Adapters.CartRecyclerAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity  extends BaseActivity {

    private static final String KEY_CARD = "card";
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    List<CartModel> cartModelArrayList = new ArrayList<>();
    CartRecyclerAdapter cartRecyclerAdapter;
    TextView subTotalTV, textTV, deliveryTV, totalTV;
    Button placeOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_cart);
        recyclerView = findViewById(R.id.cartlist);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        placeOrder = findViewById(R.id.PlaceOrder);





        placeOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String card = sharedPreferences.getString(KEY_CARD,null);
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
                            card = bottomSheetView.findViewById(R.id.CardNumber);
                            expiry = bottomSheetView.findViewById(R.id.CardExpiryDate);
                            cvv = bottomSheetView.findViewById(R.id.CardCVV);

                            String Sname = name.getText().toString();
                            String Scard = card.getText().toString();
                            String Sexpiry = expiry.getText().toString();
                            String Scvv = cvv.getText().toString();

                            Toast.makeText(CartActivity.this, "Add Card", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("KEY_CARD",MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("CardName",Sname);
                            editor.putString("CardNumber",Scard);
                            editor.putString("CardExpiry",Sexpiry);
                            editor.putString("CardCvv",Scvv);
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

                }
            }
        });
        getCartDetails();
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
}