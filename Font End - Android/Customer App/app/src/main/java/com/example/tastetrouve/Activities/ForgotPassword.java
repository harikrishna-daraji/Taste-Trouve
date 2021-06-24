package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.tastetrouve.R;

public class ForgotPassword extends AppCompatActivity {

    EditText emailPhone;
    ImageButton send;

    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailPhone = findViewById(R.id.editTextEmailPhone);
        send = findViewById(R.id.imageButtonSend);
        mAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                Forgotpassword();

            }
        });
    }

    private void Forgotpassword() {
        String Semailphone = emailPhone.getText().toString();

        if(Patterns.EMAIL_ADDRESS.matcher(Semailphone).matches()){
            Toast.makeText(this, "This is an email", Toast.LENGTH_SHORT).show();
           mAuth.fetchSignInMethodsForEmail(Semailphone)
                   .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                       @Override
                       public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                           boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                           if(isNewUser){
                               Toast.makeText(ForgotPassword.this, "No User Exists with that Email", Toast.LENGTH_SHORT).show();
                           }
                           else {
//                               Toast.makeText(ForgotPassword.this, "User Exists in database", Toast.LENGTH_SHORT).show();
////                               startActivity(new Intent(ForgotPassword.this,ResetPasswordActivity.class));
//                               Intent intent = new Intent(ForgotPassword.this,ResetPasswordActivity.class);
//                               intent.putExtra("email",Semailphone);
//                               startActivity(intent);
                               mAuth.sendPasswordResetEmail(emailPhone.getText().toString())
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               AlertDialog dialog = new AlertDialog.Builder(ForgotPassword.this)
                                                       .setTitle("Email sent to " +emailPhone.getText().toString())
                                                       .setMessage("Follow the instructions sent to your email for resetting the password")
                                                       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                           @Override
                                                           public void onClick(DialogInterface dialog, int which) {

                                                               startActivity(new Intent(ForgotPassword.this,SignIn.class));
                                                           }
                                                       })
                                                       .create();
                                               dialog.show();
                                           }
                                       });
                           }
                       }
                   });
        }else if(Patterns.PHONE.matcher(Semailphone).matches()){
            Toast.makeText(this, "This is a phone number", Toast.LENGTH_SHORT).show();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
            userRef.orderByChild("phone").equalTo(Semailphone).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.getValue() != null){
                        Toast.makeText(ForgotPassword.this, "Phone number exists", Toast.LENGTH_SHORT).show();
                        
                    }else
                        {
                            Toast.makeText(ForgotPassword.this, "Phone number don't exists", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            emailPhone.requestFocus();
            emailPhone.setError("Ener valid Email or Phone!");
            Toast.makeText(this, "Enter valid Email or Phone !", Toast.LENGTH_SHORT).show();
        }

    }
}