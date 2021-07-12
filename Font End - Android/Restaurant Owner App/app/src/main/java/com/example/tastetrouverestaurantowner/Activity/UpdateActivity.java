package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.Adapter.ViewItemAdapter;
import com.example.tastetrouverestaurantowner.Modal.ProductModal;
import com.example.tastetrouverestaurantowner.Modal.UserModal;
import com.example.tastetrouverestaurantowner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    EditText nameupdate,emailupdate,phoneupdate,addressupdate,passwordupdate;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nameupdate=findViewById(R.id.nameupdate);
        emailupdate=findViewById(R.id.emailupdate);
        phoneupdate=findViewById(R.id.phoneupdate);
        addressupdate=findViewById(R.id.addressupdate);
        passwordupdate=findViewById(R.id.passwordupdate);
        update=findViewById(R.id.update);


        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");

        Call<ResponseBody> call = APIClient.getInstance().getApi().getuserdetails(ownerId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response != null) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {

                        nameupdate.setText(jsonObject.getString("restaurantName"));
                        emailupdate.setText(jsonObject.getString("email"));
                        phoneupdate.setText(jsonObject.getString("phoneNumber"));
                        addressupdate.setText(jsonObject.getString("address"));
                        passwordupdate.setText(jsonObject.getString("password"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }




        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Call<ResponseBody> call = APIClient.getInstance().getApi().updateadmin(ownerId,
                        nameupdate.getText().toString(), emailupdate.getText().toString(),passwordupdate.getText().toString(),
                        phoneupdate.getText().toString(),addressupdate.getText().toString() );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent myintent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(myintent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });



            }
        });
    }
}