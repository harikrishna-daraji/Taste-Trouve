package com.example.tastetrouve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    EditText email,password;
    TextView signup, forgotpassword;
    ImageButton signin,facebook,google;
    ImageView hide;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);

        signup = findViewById(R.id.textViewSignUp);

        signin = findViewById(R.id.imageButtonSignIn);
        facebook = findViewById(R.id.imageButtonSignInFacebook);
        google = findViewById(R.id.imageButtonSignInGoogle);

        hide = findViewById(R.id.imageView3);

        forgotpassword = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });
    }

    private void LoginUser() {
        String SEmail = email.getText().toString().trim();
        String SPassword = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(SEmail,SPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignIn.this, "Signed in successfully", Toast.LENGTH_SHORT).show();

                        }   else{
                            Toast.makeText(SignIn.this, "Failed to Login. Enter correct credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}