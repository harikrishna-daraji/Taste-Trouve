package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tastetrouve.Models.UserTestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.tastetrouve.R;

import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {

    EditText emailPhone;
    ImageButton send;

    FirebaseAuth mAuth;

    String codeeBySystem;


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

        if (Patterns.EMAIL_ADDRESS.matcher(Semailphone).matches()) {
            Toast.makeText(this, "This is an email", Toast.LENGTH_SHORT).show();
            mAuth.fetchSignInMethodsForEmail(Semailphone)
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            boolean isNewUser = task.getResult().getSignInMethods().isEmpty();
                            if (isNewUser) {
                                Toast.makeText(ForgotPassword.this, "No User Exists with that Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPassword.this, "User Exists in database", Toast.LENGTH_SHORT).show();
//                               startActivity(new Intent(ForgotPassword.this,ResetPasswordActivity.class));
                                Intent intent = new Intent(ForgotPassword.this, ResetPasswordActivity.class);
                                intent.putExtra("email", Semailphone);
                                startActivity(intent);
                                mAuth.sendPasswordResetEmail(emailPhone.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                AlertDialog dialog = new AlertDialog.Builder(ForgotPassword.this)
                                                        .setTitle("Email sent to " + emailPhone.getText().toString())
                                                        .setMessage("Follow the instructions sent to your email for resetting the password")
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                startActivity(new Intent(ForgotPassword.this, SignIn.class));
                                                            }
                                                        })
                                                        .create();
                                                dialog.show();
                                            }
                                        });
                            }
                        }
                    });
        } else if (Patterns.PHONE.matcher(Semailphone).matches()) {
            Toast.makeText(this, "This is a phone number", Toast.LENGTH_SHORT).show();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        UserTestModel users = snapshot1.getValue(UserTestModel.class);
                        if (users.getPhone().equals(Semailphone)) {
                            Log.d("aa", users.getPhone());
                            String SOldPassword = users.getPassword();
                            String SPhone = "+1"+Semailphone;
                            Log.d("aa", SPhone);
                            SendVerificationCodeToUsers(SPhone);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ForgotPassword.this, "Phone number don't exists", Toast.LENGTH_SHORT).show();
                }

            });

        } else {
            emailPhone.requestFocus();
            emailPhone.setError("Enter valid Email or Phone!");
            Toast.makeText(ForgotPassword.this, "Enter valid Email or Phone !", Toast.LENGTH_SHORT).show();
        }

    }

    private void SendVerificationCodeToUsers(String SPhone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                SPhone,
                60,
                TimeUnit.SECONDS,
                ForgotPassword.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationCode, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        Intent intent = new Intent(ForgotPassword.this,VerifyOtpActivity.class);
                        intent.putExtra("phone",SPhone);
                        intent.putExtra("verificationCode",verificationCode);
                        startActivity(intent);
                    }
                }
        );
    }
}