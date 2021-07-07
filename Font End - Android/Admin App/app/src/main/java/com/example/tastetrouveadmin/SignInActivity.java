package com.example.tastetrouveadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {

    EditText email,password;
    TextView signupadmin;
    Button signIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signupadmin=findViewById(R.id.signupadmin);
        signupadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(myintent);
            }
        });
    }
}