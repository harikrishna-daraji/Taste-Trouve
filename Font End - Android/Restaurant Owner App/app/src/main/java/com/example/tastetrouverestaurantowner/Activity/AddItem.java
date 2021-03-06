package com.example.tastetrouverestaurantowner.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tastetrouverestaurantowner.APIClient;
import com.example.tastetrouverestaurantowner.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItem extends AppCompatActivity {
    String[] category = {"Main Course", "Appetizers", "Drinks"};
    String[] specialOfferValue = {"Christmas", "Black Friday", "Boxing Day"};
    CheckBox kidsSection, PopularItem,specialOfferCheckBox;
    ImageView imageView;
    public String imageData;
    private TextView mTextView;
    public String specialVal;
    private Spinner subCategory;
    private Spinner spcialOfferSpinner;
    public Button addProduct;
    public EditText name, price, description, calories, quantity, deliveryTime;
    public String CategoryId, SubCategoryId;
    List<String> list = new ArrayList<>();
    private int Rqststorage = 1;
    private int Rqstfile = 2;
    private Uri uri;
    private String uriPath;
    private Intent intentData;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String ownerId = sh.getString("ownerId","");
        Log.d("ownerId", "ownerId");

        name = (EditText) findViewById(R.id.name);
        imageView = (ImageView) findViewById(R.id.imgView);
        price = (EditText) findViewById(R.id.price);
        description = (EditText) findViewById(R.id.description);
        calories = (EditText) findViewById(R.id.calories);
        quantity = (EditText) findViewById(R.id.quantity);
        deliveryTime = (EditText) findViewById(R.id.deliveryTime);
        addProduct = (Button) findViewById(R.id.addProduct);


        kidsSection = (CheckBox) findViewById(R.id.kidSection);
        PopularItem = (CheckBox) findViewById(R.id.popularItem);
        specialOfferCheckBox = (CheckBox) findViewById(R.id.specialOffer);

        Spinner spin = (Spinner) findViewById(R.id.categorySpinner);
        spcialOfferSpinner = (Spinner) findViewById(R.id.spcialOfferSpinner);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AddItem.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Rqststorage);
                } else {
                    PickPicture();
                }

            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {


                CategoryId = category[i].toString();
                if (CategoryId.equals("Main Course")) {

                    list.clear();
                    list.add("Beef");
                    list.add("Chicken");
                    list.add("Fish");
                    list.add("Vegetarian");
                    list.add("Vegan");


                    ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategory.setAdapter(aa);
                } else if (CategoryId.equals("Appetizers")) {

                    list.clear();
                    list.add("Chips & Dips");
                    list.add("Salads");
                    list.add("Soup & Consomm??");
                    list.add("Relishes/Crudite");
                    list.add("Canape");


                    ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategory.setAdapter(aa);

                } else if (CategoryId.equals("Drinks")) {

                    list.clear();
                    list.add("Alcoholic");
                    list.add("Non Alcoholic");


                    ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, list);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subCategory.setAdapter(aa);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter specialOffer = new ArrayAdapter(this, android.R.layout.simple_spinner_item, specialOfferValue);
        specialOffer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spcialOfferSpinner.setAdapter(specialOffer);

        spcialOfferSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                specialVal = specialOfferValue[i].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        subCategory = (Spinner) findViewById(R.id.subCategorySpinner);
        subCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                SubCategoryId = list.get(i).toString();
                Log.d("aa", SubCategoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mTextView = (TextView) findViewById(R.id.text);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean formValid = validateForm();
                if (formValid) {

                    Boolean kidIsActive = kidsSection.isChecked();
                    Boolean PopularIsActive = PopularItem.isChecked();
                    Boolean SpecialOffercheck = specialOfferCheckBox.isChecked();
                    int productPrice = Integer.valueOf(price.getText().toString());
                    int Productquantity = Integer.parseInt(quantity.getText().toString());

                    String productName = name.getText().toString();
                    String productDescription = description.getText().toString();
                    String productcalories = calories.getText().toString();
                    String productDeliveryTime = deliveryTime.getText().toString();


                    Call<ResponseBody> call = APIClient.getInstance().getApi().createProduct(ownerId, CategoryId,
                            SubCategoryId, productName
                            , imageData, productPrice,
                            productDescription, productcalories,
                            Productquantity, kidIsActive,
                            PopularIsActive, productDeliveryTime,SpecialOffercheck,specialVal);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.code() == 200) {
                                    finish();
                                }
                            } catch (Exception ex) {
                                Log.i("TAG","TAG "+ex.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(AddItem.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });


    }


    private boolean validateForm() {




        String productName =name.getText().toString();
        if (TextUtils.isEmpty(imageData)) {

            Toast.makeText(AddItem.this, "Image can not be blank", Toast.LENGTH_SHORT).show();
            return false;
        } if (TextUtils.isEmpty(productName)) {

            name.requestFocus();
            name.setError("NAME CANNOT BE EMPTY");
            return false;
        }


        try {
            int servingSize = Integer.parseInt(((EditText) findViewById(R.id.price)).getText().toString());

        } catch (Exception e) {
            price.requestFocus();
            price.setError("PRICE CANNOT BE EMPTY");
            return false;
        }


        String PproductDesciption=description.getText().toString();
        if (TextUtils.isEmpty(PproductDesciption)) {
            description.requestFocus();
            description.setError("DESCRIPTION CANNOT BE EMPTY");
            return false;
        }

        String address=calories.getText().toString();
        if (TextUtils.isEmpty(address)) {
            calories.requestFocus();
            calories.setError("CALORIES CANNOT BE EMPTY");
            return false;
        }

        try {
            int servingSize = Integer.parseInt(((EditText) findViewById(R.id.quantity)).getText().toString());

        } catch (Exception e) {
            quantity.requestFocus();
            quantity.setError("QUANTITY CANNOT BE EMPTY");
            return false;
        }

        String password=deliveryTime.getText().toString();
        if (TextUtils.isEmpty(password)) {
            deliveryTime.requestFocus();
            deliveryTime.setError("TIME CANNOT BE EMPTY");
            return false;
        }



        return true;
    }



    private void PickPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, Rqstfile);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Rqstfile && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.getData();
                intentData = data;

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final StorageReference imageRference = storageRef.child("images/"+ UUID.randomUUID().toString());

                imageRference.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                imageRference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;
                                        imageData = uri.toString();


                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                            }
                        });


            }


        }
    }
}