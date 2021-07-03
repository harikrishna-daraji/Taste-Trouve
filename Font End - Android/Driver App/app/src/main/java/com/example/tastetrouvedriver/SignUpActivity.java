package com.example.tastetrouvedriver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    EditText name,email,phone,password,dateofbirth;
    TextView signin;
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
        
    }
}