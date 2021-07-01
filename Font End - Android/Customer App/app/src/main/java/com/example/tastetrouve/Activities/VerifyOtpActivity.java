package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpActivity extends BaseActivity {

    TextView NumberOtp;
    SharedPreferences sharedPreferences;
    EditText Otp1,Otp2,Otp3,Otp4,Otp5,Otp6;
    ImageButton Verify;

    String verificationId,Phone;

    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_verify_otp);

        Otp1 = findViewById(R.id.editTextOtp1);
        Otp2 = findViewById(R.id.editTextOtp2);
        Otp3 = findViewById(R.id.editTextOtp3);
        Otp4 = findViewById(R.id.editTextOtp4);
        Otp5 = findViewById(R.id.editTextOtp5);
        Otp6 = findViewById(R.id.editTextOtp6);
        Verify = findViewById(R.id.imageButtonVerify);

        NumberOtp = findViewById(R.id.textView9);


        Phone = getIntent().getStringExtra("phone");
        NumberOtp.setText("We have sent you an OTP to "+Phone+"\nPlease Enter below to verify forgot password");

        verificationId = getIntent().getStringExtra("verificationCode");

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Otp1.getText().toString().trim().isEmpty()||
                        Otp2.getText().toString().trim().isEmpty()||
                        Otp3.getText().toString().trim().isEmpty()||
                        Otp4.getText().toString().trim().isEmpty()||
                        Otp5.getText().toString().trim().isEmpty()||
                        Otp6.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOtpActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                return;
                }
                String code = Otp1.getText().toString()+
                        Otp2.getText().toString()+
                        Otp3.getText().toString()+
                        Otp4.getText().toString()+
                        Otp5.getText().toString()+
                        Otp6.getText().toString();

                if(verificationId != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,code);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(VerifyOtpActivity.this, "Otp Verification completed successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(VerifyOtpActivity.this,ResetPasswordActivity.class);
                                        intent.putExtra("Phone",Phone);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(VerifyOtpActivity.this, "Otp Verification Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}