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

public class SIgnInActivity extends AppCompatActivity {

    EditText name,password;
    TextView forgotPassword;
    ImageButton signin, signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        name = findViewById(R.id.editTextEmailPhone);
        password = findViewById(R.id.editTextPassword);

        forgotPassword = findViewById(R.id.textViewForgotPassword);

        signin = findViewById(R.id.SignIn);
        signup = findViewById(R.id.SignUp);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }

            Call<ResponseBody> call = APIClient.getInstance().getApi().loginUser(name.getText().toString(),
                    password.getText().toString());


        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SIgnInActivity.this,SignUpActivity.class));
            }
        });

    }

    private void SignIn() {
    }
}