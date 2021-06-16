package com.example.tastetrouverestaurantowner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    String[] category = { "Main Course", "Appetizers", "Drinks"};
    private TextView mTextView;
    private Spinner subCategory;
    public String CategoryId,SubCategoryId;
    List<String> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Spinner spin = (Spinner) findViewById(R.id.categorySpinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);


        subCategory= (Spinner) findViewById(R.id.subCategorySpinner);
        spin.setOnItemSelectedListener(this);


        mTextView = (TextView) findViewById(R.id.text);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
       // Toast.makeText(getApplicationContext(),country[position] , Toast.LENGTH_LONG).show();

        Spinner spin = (Spinner)adapterView;
        Spinner spin2 = (Spinner)adapterView;
        if(spin.getId() == R.id.subCategorySpinner) {
            SubCategoryId = list.get(i).toString();

        }
        if(spin2.getId() == R.id.categorySpinner){
            CategoryId=category[i].toString();
            if(CategoryId.equals("Main Course")){

                list.clear();
                list.add("Beef");
                list.add("Chicken");
                list.add("Fish");
                list.add("Vegetarian");
                list.add("Vegan");


                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategory.setAdapter(aa);
            }
            else if(CategoryId.equals("Appetizers")){

                list.clear();
                list.add("Chips & Dips");
                list.add("Salads");
                list.add("Soup & Consommé");
                list.add("Relishes/Crudite");
                list.add("Canape");


                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategory.setAdapter(aa);

            }    else if(CategoryId.equals("Drinks")){

                list.clear();
                list.add("Alcoholic");
                list.add("Non Alcoholic");



                ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,list);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCategory.setAdapter(aa);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}