package com.example.tastetrouverestaurantowner.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.GlobalObjects;
import com.example.tastetrouverestaurantowner.R;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    EditText adminname,adminEmail,adminPhone,adminPassword,adminAddress;
    Button signUp;
    TextView login;
    String latitude, longitude, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_sign_up2);

        adminname=(EditText)findViewById(R.id.adminname);
        adminPhone=(EditText)findViewById(R.id.adminPhone);
        adminPassword=(EditText)findViewById(R.id.adminPassword);

        adminAddress=(EditText)findViewById(R.id.adminAddress);
        adminAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,MapActivity.class);
                startActivityForResult(intent,GlobalObjects.MAP_REQUEST_CODE);
            }
        });

        login=(TextView) findViewById(R.id.login);
        adminEmail=(EditText)findViewById(R.id.adminEmail);
        signUp=(Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  checkValidation();
                boolean formValid = validateForm();
                if (formValid){

                    Call<ResponseBody> call = APIClient.getInstance().getApi().SignUp(adminname.getText().toString(),
                            adminEmail.getText().toString(),
                            adminPassword.getText().toString(),
                            "",
                            adminPhone.getText().toString(),
                            false, "restaurantOwner",latitude,longitude
                    );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(SignUpActivity.this, "Admin User created", Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(SignUpActivity.this, LogInActivity.class);
                        startActivity(myintent);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(myintent);
            }
        });
    }

    private boolean validateForm() {


        String name =adminname.getText().toString();
        if (TextUtils.isEmpty(name)) {

            adminname.requestFocus();
            adminname.setError("NAME CANNOT BE EMPTY");
            return false;
        }
        String email=adminEmail.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            adminEmail.requestFocus();
//            adminEmail.setError("EMAIL CANNOT BE EMPTY");
//            return false;
//        }

        if(!isValidateEmail()) {
            adminEmail.requestFocus();
            adminEmail.setError("Enter valid E-Mail address");
            return false;
        }

        String phone=adminPhone.getText().toString();
//        if (TextUtils.isEmpty(phone)) {
//            adminPhone.requestFocus();
//            adminPhone.setError("PHONE CANNOT BE EMPTY");
//            return false;
//        }
        if(!isValidPhoneNumber()) {
            adminPhone.requestFocus();
            adminPhone.setError("Enter Valid Phone number");
            return false;
        }

        String address=adminAddress.getText().toString();
        if (TextUtils.isEmpty(address)) {
            adminAddress.requestFocus();
            adminAddress.setError("ADDRESS CANNOT BE EMPTY");
            return false;
        }

        String password=adminPassword.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            adminPassword.requestFocus();
//            adminPassword.setError("PASSWORD CANNOT BE EMPTY");
//            return false;
//        }
         if(!isValidatePassword()) {
             adminPassword.requestFocus();
             adminPassword.setError("Password must be between 8 to 20 and contain at least one special symbol, uppercase, lowercase and number");
             return false;
        }



        return true;
    }



    private boolean isValidateEmail() {
        //Regular Expression
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(adminEmail.getText().toString().trim());
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isValidatePassword() {
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
        Matcher m = p.matcher(adminPassword.getText().toString().trim());

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    boolean isValidPhoneNumber() {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[+]?[0-9]{10,13}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(adminPhone.getText().toString());
        return matcher.matches();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == GlobalObjects.MAP_REQUEST_CODE) {
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
            address = data.getStringExtra("address");

            adminAddress.setText(address);
        }
    }
}