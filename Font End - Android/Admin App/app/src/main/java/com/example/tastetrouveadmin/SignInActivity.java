package com.example.tastetrouveadmin;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    EditText email,password;
    TextView signUp;
    Button signIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_in);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        signUp=(TextView)findViewById(R.id.signupadmin);
        signIn=(Button)findViewById(R.id.signIn);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.adminpassword);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(myintent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean formValid = validateForm();
                if (formValid){
                    Call<ResponseBody> call = APIClient.getInstance().getApi().signIn(email.getText().toString(),
                            password.getText().toString()
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
//
//                                SharedPreferences.Editor myEdit = sharedPreferences.edit();
//                                try {
//                                    myEdit.putString("ownerId", jsonObject.getString("_id"));
//                                    myEdit.commit();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }

                                Toast.makeText(SignInActivity.this, "Success Login", Toast.LENGTH_LONG).show();
                                Intent myintent = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(myintent);
                            } else {
                                Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    private boolean validateForm() {


        String email =signIn.getText().toString();
        if (TextUtils.isEmpty(email)) {

            signIn.requestFocus();
            signIn.setError("EMAIL CANNOT BE EMPTY");
            return false;
        }


        String adminpassword= password.getText().toString();
        if (TextUtils.isEmpty(adminpassword)) {
            password.requestFocus();
            password.setError("PASSWORD CANNOT BE EMPTY");
            return false;
        }




        return true;
    }
}
