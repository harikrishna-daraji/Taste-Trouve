package com.example.tastetrouve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class SignUp extends AppCompatActivity {

    EditText name,email,password,phone,dateofbirth;
    ImageView hide;
    ImageButton signup;
    TextView signin;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.userEmailId);
        password = findViewById(R.id.editTextTextPassword);
        phone = findViewById(R.id.PhoneNumber);
        dateofbirth = findViewById(R.id.DateOfBirth);

        hide = findViewById(R.id.imageView3);

        signup = findViewById(R.id.imageButtonSignUp);

        signin = findViewById(R.id.textViewSignIN);

        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                RegisterUser();
            }
        });
    }

    private void RegisterUser() {
            String SName = name.getText().toString().trim();
            String SEmail = email.getText().toString().trim();
            String SPassword = password.getText().toString().trim();
            String SPhone = phone.getText().toString().trim();
            String SDateofbirth = dateofbirth.getText().toString().trim();


        if(SName.isEmpty()){
            name.requestFocus();
            name.setError("Name is required");
        }else
        if(SEmail.isEmpty()){
            email.requestFocus();
            email.setError("Email is required");
        }else
        if(!Patterns.EMAIL_ADDRESS.matcher(SEmail).matches()){
            email.requestFocus();
            email.setError("Enter valid E-Mail address");
        }else
        if(SPhone.isEmpty()){
            phone.requestFocus();
            phone.setError("Phone is required");
        }else
        if(SDateofbirth.isEmpty()){
            dateofbirth.requestFocus();
            dateofbirth.setError("Date of birth is required");
        }else {

            mAuth.createUserWithEmailAndPassword(SEmail, SPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Users user = new Users(SName, SEmail, SPhone, SDateofbirth);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp.this,SignIn.class));
                                    } else {
                                        Toast.makeText(SignUp.this, "User creation failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
        }
    }
}

