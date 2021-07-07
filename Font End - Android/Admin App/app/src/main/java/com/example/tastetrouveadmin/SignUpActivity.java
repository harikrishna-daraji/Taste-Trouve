package com.example.tastetrouveadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {


    EditText name,Email,Password;
    Button signUp,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        name=(EditText)findViewById(R.id.name);
        Password=(EditText)findViewById(R.id.password);
        Email=(EditText)findViewById(R.id.email);
        signUp=(Button) findViewById(R.id.signUp);
        cancel=(Button) findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(myintent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean formValid = validateForm();
                if (formValid) {

                Call<ResponseBody> call = APIClient.getInstance().getApi().SignUp(name.getText().toString(),
                        Email.getText().toString(),
                        Password.getText().toString(),
                        "admin"
                );

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(SignUpActivity.this, "Admin User created", Toast.LENGTH_LONG).show();
                        Intent myintent = new Intent(SignUpActivity.this, SignInActivity.class);
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

}

    private boolean validateForm() {


        String adminname =name.getText().toString();
        if (TextUtils.isEmpty(adminname)) {

            name.requestFocus();
            name.setError("NAME CANNOT BE EMPTY");
            return false;
        }

        if(!isValidateEmail()) {
            Email.requestFocus();
            Email.setError("Enter valid E-Mail address");
            return false;
        }


        if(!isValidatePassword()) {
            Password.requestFocus();
            Password.setError("Password must be between 8 to 20 and contain at least one special symbol, uppercase, lowercase and number");
            return false;
        }
        return true;
    }

    private boolean isValidateEmail() {
        //Regular Expression
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(Email.getText().toString().trim());
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

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(Password.getText().toString().trim());

        return m.matches();
    }

}