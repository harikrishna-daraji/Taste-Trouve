package com.example.tastetrouvedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouvedriver.Helper.APIClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SIgnInActivity extends AppCompatActivity {

    EditText name,password;
    TextView forgotPassword;
    ImageButton signin, signup;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name = findViewById(R.id.editTextEmailPhone);
        password = findViewById(R.id.editTextPassword);

        forgotPassword = findViewById(R.id.textViewForgotPassword);

        signin = findViewById(R.id.SignIn);
        signup = findViewById(R.id.SignUp);

         sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SIgnInActivity.this,SignUpActivity.class));
            }
        });

    }

    private void SignIn() {


            Call<ResponseBody> call = APIClient.getInstance().getApi().loginUser(name.getText().toString(),
                    password.getText().toString());


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

                        Toast.makeText(SIgnInActivity.this, "Success Login", Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(SIgnInActivity.this, MainActivity.class);
                        startActivity(myintent);
                        finish();
                    } else {
                        Toast.makeText(SIgnInActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SIgnInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
