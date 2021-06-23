package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.UserModel;
import com.example.tastetrouve.R;
import com.example.tastetrouve.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends BaseActivity {

    EditText name,email,password,phone,dateofbirth;
    ImageButton signup;
    TextView signin;
    SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.userEmailId);
        password = findViewById(R.id.editTextTextPassword);
        phone = findViewById(R.id.PhoneNumber);
        dateofbirth = findViewById(R.id.DateOfBirth);

        signup = findViewById(R.id.imageButtonSignUp);

        signin = findViewById(R.id.textViewSignIN);


        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUserToFirebase();
            }
        });
    }

    private void RegisterUserToFirebase() {
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
        } else if(!validatePassword()) {
            password.requestFocus();
            password.setError("Password must be between 8 to 20 and contain at least one special symbol, uppercase, lowercase and number");
        } else if(!isValidPhoneNumber()){
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

                            Users user = new Users(SName, SEmail, SPhone, SDateofbirth,SPassword);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("TAG","TAG: Firebase user is created");
                                        registerUser(user,FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    } else {
                                        Toast.makeText(SignUp.this, "User creation failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
        }
    }

    private boolean validateEmail() {
        //Regular Expression
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email.getText().toString().trim());
        if (matcher.matches()) {
            return true;
        } else {
            return false;
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
        Matcher m = p.matcher(password.getText().toString().trim());

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    boolean isValidPhoneNumber() {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[+]?[0-9]{10,13}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone.getText().toString());
        return matcher.matches();
    }

    private void saveLogInStatus(String token) {
        sharedPreferences = getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.putBoolean("signUpDone",true);
        editor.commit();
    }

    private void registerUser(Users user, String fcmToken) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.registerUser(user.email,user.password,user.name,fcmToken,user.phone,user.dateofbirth).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            saveLogInStatus(response.body().get_id());
                            Toast.makeText(SignUp.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, HomeActivity.class));
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure");
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

}

