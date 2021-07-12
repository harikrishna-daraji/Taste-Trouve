package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
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
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    DatePickerDialog.OnDateSetListener listener;

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

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        Calendar calendar = Calendar.getInstance();
     DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
             calendar.set(Calendar.YEAR,year);
             calendar.set(Calendar.MONTH,month);
             calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

             updateCalendar();

         }
         private void updateCalendar(){
             String Format = "MM/dd/yy";
             SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

             dateofbirth.setText(sdf.format(calendar.getTime()));
         }
     };
     dateofbirth.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             new DatePickerDialog(SignUp.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
         }
     });



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
            String NewPassword = password.getText().toString();

        if(SName.isEmpty()){
            name.requestFocus();
            name.setError(getString(R.string.name_required));
        }else
        if(SEmail.isEmpty()){
            email.requestFocus();
            email.setError(getString(R.string.email_required));
        }else
        if(!Patterns.EMAIL_ADDRESS.matcher(SEmail).matches()){
            email.requestFocus();
            email.setError(getString(R.string.enter_valid_email));
        } else if(!validatePassword()) {
            password.requestFocus();
            password.setError(getString(R.string.password_condition));
        } else if(!isValidPhoneNumber()){
            phone.requestFocus();
            phone.setError(getString(R.string.phone_required));
        }else
        if(SDateofbirth.isEmpty()){
            dateofbirth.requestFocus();
            dateofbirth.setError(getString(R.string.birthDate_required));
        }else {
            mAuth.createUserWithEmailAndPassword(SEmail, SPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Users user = new Users(SName, SEmail, SPhone, SDateofbirth, SPassword, NewPassword);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.i("TAG","TAG: Firebase user is created");
                                        registerUser(user,FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    } else {
                                        Toast.makeText(SignUp.this, getString(R.string.user_creation_failed), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.i("TAG","TAG: Firebase real time database failure");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("TAG","TAG: mAuth failure: "+e.getMessage());
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

    private void saveLogInStatus(String token, String phone) {
        sharedPreferences = getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.putString("phone",phone);
        editor.putBoolean("signUpDone",true);
        editor.apply();
        editor.commit();
    }

    private void registerUser(Users user, String fcmToken) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.registerUser(user.getEmail(),user.getPassword(),user.getName(),fcmToken,user.getPhone(),user.getDateofbirth()).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    try {
                        Log.i("TAG","TAG: Code: "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            saveLogInStatus(response.body().get_id(),response.body().getPhoneNumber());
                            Toast.makeText(SignUp.this, getString(R.string.user_created), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this, HomeActivity.class));
                        } else {

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

