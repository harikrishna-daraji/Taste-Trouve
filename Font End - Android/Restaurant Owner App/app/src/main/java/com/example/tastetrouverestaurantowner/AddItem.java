package com.example.tastetrouverestaurantowner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
import retrofit2.http.Field;

public class AddItem extends AppCompatActivity {
    String[] category = {"Main Course", "Appetizers", "Drinks"};
    CheckBox kidsSection, PopularItem;
    ImageView imageView;
    String imageData;
    private TextView mTextView;
    private Spinner subCategory;
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

        Spinner spin = (Spinner) findViewById(R.id.categorySpinner);
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
                    list.add("Soup & Consommé");
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
                Boolean kidIsActive = kidsSection.isChecked();
                Boolean PopularIsActive = PopularItem.isChecked();
                int productPrice = Integer.valueOf(price.getText().toString());
                int Productquantity = Integer.parseInt(quantity.getText().toString());

                String productName = name.getText().toString();
                String productDescription = description.getText().toString();
                String productcalories = calories.getText().toString();
                String productDeliveryTime = deliveryTime.getText().toString();


                Call<ResponseBody> call = APIClient.getInstance().getApi().createProduct("restaurantId", CategoryId,
                        SubCategoryId, productName
                        , "http:://test.poimag", productPrice,
                        productDescription, productcalories,
                        Productquantity, kidIsActive,
                        PopularIsActive, productDeliveryTime);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(AddItem.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


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