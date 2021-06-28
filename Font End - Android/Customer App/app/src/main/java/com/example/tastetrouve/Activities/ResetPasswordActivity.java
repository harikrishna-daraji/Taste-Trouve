package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.UserTestModel;
import com.example.tastetrouve.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText newpassword,connfirmpassword;
    ImageButton resetPassword;
    ImageView show1,show2;
    TextView email;
    String Semail,Phone;
    String SnewPassword,SconfirmPassword;

    Boolean flag = false;
    boolean value1 = true;
    boolean value2 = true;



    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        newpassword = findViewById(R.id.editTextNewPassword);
        connfirmpassword = findViewById(R.id.editTextConfirmNewPassword);
        email = findViewById(R.id.textView7);

        Intent intent = getIntent();
        Semail = intent.getStringExtra("email");

        email.setText(Semail);


        resetPassword = findViewById(R.id.imageButtonResetPassword);

        show1 = findViewById(R.id.imageViewShow1);
        show2 = findViewById(R.id.imageViewShow2);



        show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value1 == true){
                    newpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    value1 = false;
                }else{
                    newpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    value1 = true;
                }
            }
        });

        show2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value2 == true){
                    connfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    value2 = false;
                }else{
                    connfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    value2 = true;
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
        SnewPassword = newpassword.getText().toString();
        SconfirmPassword = connfirmpassword.getText().toString();
        flag = false;
        Phone = getIntent().getStringExtra("Phone");
//        mAuth = FirebaseAuth.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
        if (!validatePassword()) {
            newpassword.requestFocus();
            newpassword.setError("Password must be between 8 to 20 and contain at least one special symbol, uppercase, lowercase and number");
        }else if(SnewPassword.equals(SconfirmPassword)) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                            UserTestModel users = snapshot2.getValue(UserTestModel.class);
                            String firebasephone = "+1" + users.getPhone();
                            if (firebasephone.equals(Phone) && flag == false) {
                                String SEMAIL = users.getEmail();
                                users.setNewPassword(SnewPassword);

                                databaseReference.child("Users").child(snapshot2.getKey()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.i("TAG","TAG: Firebase success");
                                        updateUser(Phone,SnewPassword);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("TAG","TAG: Firebase failure: "+e.getMessage());
                                    }
                                });

                                databaseReference.child("Users").child(snapshot2.getKey()).setValue(users);
                                Toast.makeText(ResetPasswordActivity.this, "Password updated Successfully", Toast.LENGTH_SHORT).show();
                                flag = true;
                                startActivity(new Intent(ResetPasswordActivity.this,SignIn.class));

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        else{
            newpassword.requestFocus();
            newpassword.setError("New Passwords do not match");
        }
            }
        private boolean validatePassword() {
            // Regex to check valid password.
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";

            // Compile the ReGex
            Pattern p = Pattern.compile(regex);
            // Pattern class contains matcher() method
            // to find matching between given password
            // and regular expression.
            Matcher m = p.matcher(SnewPassword);

            // Return if the password
            // matched the ReGex
            return m.matches();
        }
    private void updateUser(String phone, String password) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.updateUser(phone,password).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Toast.makeText(ResetPasswordActivity.this, "Password updated Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this,SignIn.class));
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure: "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG: "+ex.getMessage());
        }
    }
}