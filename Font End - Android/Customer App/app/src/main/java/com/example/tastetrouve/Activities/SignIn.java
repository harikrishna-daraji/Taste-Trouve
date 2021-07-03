package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.UserModel;
import com.example.tastetrouve.Models.UserTestModel;
import com.example.tastetrouve.Models.Users;
import com.example.tastetrouve.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SignIn extends BaseActivity {
    private static final int RC_SIGN_IN = 123;
    EditText email,password;
    TextView signup, forgotpassword;
    ImageButton signin;
    ImageView facebook,google;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;
    boolean flag;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);

        signup = findViewById(R.id.textViewSignUp);

        signin = findViewById(R.id.imageButtonSignIn);
        facebook = findViewById(R.id.imageButtonSignInFacebook);
        google = findViewById(R.id.imageButtonSignInGoogle);


        forgotpassword = findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,ForgotPassword.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidEmail(email.getText().toString()) && isValidPassword(password.getText().toString())) {
                    LoginUser();
                } else{
                    if(!isValidEmail(email.getText().toString())) {
                        GlobalObjects.Toast(getBaseContext(),getString(R.string.please_enter_email));
                    } else if(!isValidPassword(password.getText().toString())) {
                        GlobalObjects.Toast(getBaseContext(),getString(R.string.please_enter_password));
                    }
                }
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
    }

    boolean isValidEmail(String email) {
        if(!email.isEmpty()) {
            return true;
        }
        return false;
    }

    boolean isValidPassword(String password) {
        if(!password.isEmpty()) {
            return true;
        }
        return false;
    }

    private void LoginUser() {
        String SEmail = email.getText().toString();
        String SPassword = password.getText().toString().trim();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    flag=false;
                        UserTestModel users = snapshot.getValue(UserTestModel.class);
                    if (users.getEmail().equals(SEmail)) {
                 flag=true;
                        //Log.d("aa",users.getNewPassword());
                        String SNewPassword = users.getNewPassword();
                        String SOldPassword = users.getPassword();

                        if(SPassword.equals(SNewPassword)){
                            mAuth.signInWithEmailAndPassword(SEmail,SOldPassword)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            Toast.makeText(SignIn.this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                                            callLoginApi();
                                        }
                                    });
                                break;
                        }else{
                            password.requestFocus();
                            password.setError(getString(R.string.enter_correct_password));
                            Toast.makeText(SignIn.this,getString(R.string.enter_correct_password), Toast.LENGTH_SHORT).show();
                        }

                    }


                }
                  if(flag == false){
                    email.requestFocus();
                    email.setError(getString(R.string.email_does_not_exist));
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignIn.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void saveLogInStatus(String token) {
        Log.i("TAG","TAG: Token: "+token);
        sharedPreferences = getSharedPreferences("AuthenticationTypes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token",token);
        editor.putBoolean("signUpDone",true);
        editor.commit();
    }

    private void callLoginApi() {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.login(email.getText().toString(),password.getText().toString()).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    try {
                        Log.i("TAG","TAG: Code "+response.code()+" Message: "+response.message());
                        if(response.code() == 200) {
                            UserModel userModel = response.body();
                            saveLogInStatus(userModel.get_id());
                            startActivity(new Intent(SignIn.this, HomeActivity.class));
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.i("TAG","TAG: Server Failure"+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

    //Google signin - start
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //home activity intent
                            //startActivity(new Intent(SignIn.this,SignUp.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }
                    }
                });
    }
    //Google signin - end
}
