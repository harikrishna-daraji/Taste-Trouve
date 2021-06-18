package com.example.tastetrouve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

            String SName = name.getText().toString().trim();
            String SEmail = email.getText().toString().trim();
            String SPassword = password.getText().toString().trim();
            String SPhone = phone.getText().toString().trim();
            String SDateofbirth = dateofbirth.getText().toString().trim();

            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword("ysujaykumar@gmail.com","123456789").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(SignUp.this, "User created", Toast.LENGTH_SHORT).show();
                     }else{
//                         Toast.makeText(SignUp.this, "User Failed", Toast.LENGTH_SHORT).show();
//                         Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
//                                 Toast.LENGTH_SHORT).show();
                         Log.d("error", task.getException().toString());
                     }
                    }
                });
            }
        });

    }
}