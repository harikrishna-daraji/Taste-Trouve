package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.tastetrouve.Adapters.CartRecyclerAdapter;
import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;

public class CartActivity  extends BaseActivity {

    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<CartModel> cartModelArrayList;
    CartRecyclerAdapter cartRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_cart);
        recyclerView=(RecyclerView)findViewById(R.id.cartlist);
        layoutManager=new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        fetchdata();
    }

    private void fetchdata() {
        cartModelArrayList=new ArrayList<>();
        ArrayList<String> newList = new ArrayList<>();


        cartModelArrayList.add(new CartModel("1","https://tse4.mm.bing.net/th?id=OIP.eIL72CWEe-BgPKk9brhv0wHaHA&pid=Api&P=0&w=173&h=165","sugar cotton",24,1));
        cartModelArrayList.add(new CartModel("2","https://tse4.mm.bing.net/th?id=OIP.eIL72CWEe-BgPKk9brhv0wHaHA&pid=Api&P=0&w=173&h=165","sugar cotton",34,2));
        cartModelArrayList.add(new CartModel("3","https://tse4.mm.bing.net/th?id=OIP.eIL72CWEe-BgPKk9brhv0wHaHA&pid=Api&P=0&w=173&h=165","sugar cotton",04,3));
        cartModelArrayList.add(new CartModel("4","https://tse4.mm.bing.net/th?id=OIP.eIL72CWEe-BgPKk9brhv0wHaHA&pid=Api&P=0&w=173&h=165","sugar cotton",14,4));
        cartModelArrayList.add(new CartModel("5","https://tse4.mm.bing.net/th?id=OIP.eIL72CWEe-BgPKk9brhv0wHaHA&pid=Api&P=0&w=173&h=165","sugar cotton",64,5));

        cartRecyclerAdapter= new CartRecyclerAdapter(cartModelArrayList,CartActivity.this);
        recyclerView.setAdapter(cartRecyclerAdapter);
    }
}