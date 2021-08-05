package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.UserModel;
import com.example.tastetrouve.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUserInfoActivity extends AppCompatActivity {

    EditText name,DateOfBirth,PhoneNumber;
    Button UpdateProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);


        loadUserData();

        final EditText Name = findViewById(R.id.name);
        final EditText dateOfBirth = findViewById(R.id.DateOfBirth);
         UpdateProfile = findViewById(R.id.UpdateProfile);
        name = findViewById(R.id.name);
        DateOfBirth = findViewById(R.id.DateOfBirth);
        PhoneNumber = findViewById(R.id.PhoneNumber);

        UpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              update();
            }
        });
    }

    private void update() {
        String token = getUserToken();
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.updateUseredetail(name.getText().toString(),token,PhoneNumber.getText().toString(),
                    DateOfBirth.getText().toString()  ).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            startActivity(new Intent(UpdateUserInfoActivity.this, HomeActivity.class));

                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure"+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

    private void loadUserData() {
        String token = getUserToken();
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getUserDetails(token).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    try {
                        Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            UserModel userModel = response.body();
                            name.setText(userModel.getDisplayname());
                            DateOfBirth.setText(userModel.getDateOfBirth());
                            PhoneNumber.setText(userModel.getPhoneNumber());

                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure"+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
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

}