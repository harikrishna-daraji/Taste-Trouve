package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.Modal.UserModal;
import com.example.tastetrouverestaurantowner.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    EditText nameupdate,emailupdate,phoneupdate,addressupdate,passwordupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nameupdate=findViewById(R.id.nameupdate);
        emailupdate=findViewById(R.id.emailupdate);
        phoneupdate=findViewById(R.id.phoneupdate);
        addressupdate=findViewById(R.id.addressupdate);
        passwordupdate=findViewById(R.id.passwordupdate);


        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        Call<List<UserModal>> call = APIClient.getInstance().getApi().getuserdetails(ownerId);

        call.enqueue(new Callback<List<UserModal>>() {
            @Override
            public void onResponse(Call<List<UserModal>> call, Response<List<UserModal>> response) {

                List<UserModal> adminlist = response.body();

                nameupdate.setText(adminlist.get(0).getRestaurantName());
                emailupdate.setText(adminlist.get(0).getEmail());
                phoneupdate.setText(adminlist.get(0).getPhoneNumber());



            }

            @Override
            public void onFailure(Call<List<UserModal>> call, Throwable t) {

            }




        });
    }
}