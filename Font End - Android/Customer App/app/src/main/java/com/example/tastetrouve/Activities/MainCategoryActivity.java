package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.RestaurantModel;
import com.example.tastetrouve.R;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    TextView topHeading;
    ImageView appetizerImg, mainCourseImg, dessertImg;
    RestaurantModel restaurantModel;
    CategoryModel appetizerModel, main_CourseModel, dessertModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_main_category);
        initUI();
        manageIntent();
    }

    private void initUI() {
        topHeading = findViewById(R.id.topHeading);
        appetizerImg = findViewById(R.id.appetizerImg);
        mainCourseImg = findViewById(R.id.mainCourseImg);
        dessertImg = findViewById(R.id.dessertImg);
        findViewById(R.id.appetizerRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                intent.putExtra(GlobalObjects.ModelList.Category.toString(),appetizerModel.get_id());
                startActivity(intent);
            }
        });

        findViewById(R.id.main_courseRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                intent.putExtra(GlobalObjects.ModelList.Category.toString(),main_CourseModel.get_id());
                startActivity(intent);
            }
        });

        findViewById(R.id.dessertRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                intent.putExtra(GlobalObjects.ModelList.Category.toString(),dessertModel.get_id());
                startActivity(intent);
            }
        });

    }

    private void manageIntent() {
        if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Category.toString())) {
            restaurantModel = (RestaurantModel) getIntent().getSerializableExtra(GlobalObjects.ModelList.Restaurant.toString());
            topHeading.setText(restaurantModel.getRestaurantName());
            List<CategoryModel> categoryModels = (ArrayList) getIntent().getStringArrayListExtra(GlobalObjects.ModelList.Category.toString());
            for(CategoryModel model: categoryModels) {
                if(model.getName().equals("Drinks")) {
                    dessertModel = model;
                    Glide.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(dessertImg);
                } else if(model.getName().equals("Main Course")) {
                    main_CourseModel = model;
                    Glide.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(mainCourseImg);
                } else if(model.getName().equals("Appetizers")) {
                    appetizerModel = model;
                    Glide.with(getBaseContext()).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(appetizerImg);
                }
            }
        }
    }

}