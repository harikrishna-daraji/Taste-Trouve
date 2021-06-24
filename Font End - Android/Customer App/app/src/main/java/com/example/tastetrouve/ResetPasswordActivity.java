package com.example.tastetrouve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText oldpassword,newpassword,connfirmpassword;
    ImageButton resetPassword;
    ImageView show1,show2,show3;
    TextView email;
    String Semail;

    boolean value1 = true;
    boolean value2 = true;
    boolean value3 = true;


    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        oldpassword = findViewById(R.id.editTextOldPassword);
        newpassword = findViewById(R.id.editTextNewPassword);
        connfirmpassword = findViewById(R.id.editTextConfirmNewPassword);
        email = findViewById(R.id.textView7);

        Intent intent = getIntent();
        Semail = intent.getStringExtra("email");

        email.setText(Semail);


        resetPassword = findViewById(R.id.imageButtonResetPassword);

        show1 = findViewById(R.id.imageViewShow1);
        show2 = findViewById(R.id.imageViewShow2);
        show3 = findViewById(R.id.imageViewShow3);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value1 == true){
                    oldpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    value1 = false;
                }else{
                    oldpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    value1 = true;
                }
            }
        });

        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value2 == true){
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    value2 = false;
                }else{
                    newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    value2 = true;
                }
            }
        });

        show3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value3 == true){
                    connfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    value3 = false;
                }else{
                    connfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    value3 = true;
                }
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            PasswordChange();

            }
        });

    }

    private void PasswordChange() {
        String SoldPassword = oldpassword.getText().toString();
        String SnewPassword = newpassword.getText().toString();
        String SconfirmPassword = connfirmpassword.getText().toString();

        if(SnewPassword.equals(SconfirmPassword)) {

            AuthCredential credential = EmailAuthProvider.getCredential(Semail, SoldPassword);
            user.reauthenticate(credential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            user.updatePassword(SnewPassword)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(ResetPasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(ResetPasswordActivity.this, "Password not Updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ResetPasswordActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            newpassword.requestFocus();
            newpassword.setError("New Passwords do not mattch");
        }
    }
}