package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.RestaurantModel;
import com.example.tastetrouve.R;

import java.util.List;

public class MainCategoryActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    TextView topHeading;
    RestaurantModel restaurantModel;
    List<CategoryModel> categoryModelList;

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
        findViewById(R.id.appetizerRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                for(CategoryModel model: categoryModelList) {
                    if(model.getName().equals("Appetizers")) {
                        intent.putExtra(GlobalObjects.ModelList.Category.toString(),model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        findViewById(R.id.main_courseRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                for(CategoryModel model: categoryModelList) {
                    if(model.getName().equals("Main Course")) {
                        intent.putExtra(GlobalObjects.ModelList.Category.toString(),model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        findViewById(R.id.dessertRelative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainCategoryActivity.this,SubcategoryListActivity.class);
                intent.putExtra(GlobalObjects.ModelList.Restaurant.toString(),restaurantModel.get_id());
                for(CategoryModel model: categoryModelList) {
                    if(model.getName().equals("Drinks")) {
                        intent.putExtra("categoryId",model.get_id());
                        break;
                    }
                }
                startActivity(intent);
            }
        });

    }

    private void manageIntent() {
        if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString())) {
            restaurantModel = (RestaurantModel) getIntent().getSerializableExtra(GlobalObjects.ModelList.Restaurant.toString());
            topHeading.setText(restaurantModel.getRestaurantName());
        }
    }

}