package com.example.tastetrouvedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouvedriver.Helper.APIClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText name,email,phone,password,dateofbirth;
    TextView signin;
    ImageButton signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhone);
        password = findViewById(R.id.editTextSUPassword);
        dateofbirth = findViewById(R.id.editTextDate);
        signin = findViewById(R.id.SignIn);
        signup = findViewById(R.id.ButtonSignUp);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SIgnInActivity.class));
            }
        });

        String Sname = name.getText().toString();
        String Semail = email.getText().toString();
        String Sphone = phone.getText().toString();
        String Spassword = password.getText().toString();
        String Sdateofbirth = dateofbirth.getText().toString();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = APIClient.getInstance().getApi().SignUp(name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        "",
                        phone.getText().toString(),
                        true, dateofbirth.getText().toString());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(SignUpActivity.this, "Admin User created", Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(SignUpActivity.this, SIgnInActivity.class);
                        startActivity(myintent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


        
    }
}