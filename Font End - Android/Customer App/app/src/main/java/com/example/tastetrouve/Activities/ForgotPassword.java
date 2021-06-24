package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tastetrouve.R;

public class ForgotPassword extends AppCompatActivity {

    EditText emailPhone;
    ImageButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailPhone = findViewById(R.id.editTextEmailPhone);
        send = findViewById(R.id.imageButtonSend);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}