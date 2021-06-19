package com.example.tastetrouverestaurantowner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    EditText adminname,adminEmail,adminPhone,adminPassword,adminAddress;
    Button signUp;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_up2);

        adminname=(EditText)findViewById(R.id.adminname);
        adminPhone=(EditText)findViewById(R.id.adminPhone);
        adminPassword=(EditText)findViewById(R.id.adminPassword);
        adminAddress=(EditText)findViewById(R.id.adminAddress);
        login=(TextView) findViewById(R.id.login);
        adminEmail=(EditText)findViewById(R.id.adminEmail);
        signUp=(Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  checkValidation();


                Call<ResponseBody> call = APIClient.getInstance().getApi().SignUp(adminname.getText().toString(),
                        adminEmail.getText().toString(),
                        adminPassword.getText().toString(),
                        "",
                        adminPhone.getText().toString(),
                       false,"restaurantOwner"
                       );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(SignUpActivity.this, "Admin User created", Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(myintent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(myintent);
            }
        });
    }
}