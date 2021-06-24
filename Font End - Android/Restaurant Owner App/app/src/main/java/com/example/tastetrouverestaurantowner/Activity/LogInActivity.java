package com.example.tastetrouverestaurantowner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    Button adminLogin;
    TextView signUp;
    EditText emailLogin,passwordLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_log_in);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);


        signUp=(TextView)findViewById(R.id.signUp);
        adminLogin=(Button)findViewById(R.id.adminLogin);
        emailLogin=(EditText) findViewById(R.id.emailLogin);
        passwordLogin=(EditText) findViewById(R.id.passwordLogin);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(myintent);
            }
        });


        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean formValid = validateForm();
                if (formValid){
                    Call<ResponseBody> call = APIClient.getInstance().getApi().loginUser(emailLogin.getText().toString(),
                            passwordLogin.getText().toString()
                    );

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

                            SharedPreferences.Editor myEdit = sharedPreferences.edit();
                            try {
                                myEdit.putString("ownerId", jsonObject.getString("_id"));
                                myEdit.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(LogInActivity.this, "Success Login", Toast.LENGTH_LONG).show();
                            Intent myintent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(myintent);
                        } else {
                            Toast.makeText(LogInActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LogInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            }
        });



    }

    private boolean validateForm() {


        String email =emailLogin.getText().toString();
        if (TextUtils.isEmpty(email)) {

            emailLogin.requestFocus();
            emailLogin.setError("EMAIL CANNOT BE EMPTY");
            return false;
        }


String password=passwordLogin.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordLogin.requestFocus();
            passwordLogin.setError("PASSWORD CANNOT BE EMPTY");
            return false;
        }




        return true;
    }
}