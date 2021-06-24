package com.example.tastetrouve.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.SubCategoryRecycleAdapter;
import com.example.tastetrouve.HelperClass.ApiClient;
import com.example.tastetrouve.HelperClass.ApiInterface;
import com.example.tastetrouve.Models.CategoryModel;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.SubCategoryModel;
import com.example.tastetrouve.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubcategoryListActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    TextView topHeading;
    RecyclerView subCategoryRecycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_subcategory_list);
        initUI();
        manageIntent();
    }

    private void initUI() {
        topHeading = findViewById(R.id.topHeading);
        subCategoryRecycle = findViewById(R.id.subCategoryRecycle);
        subCategoryRecycle.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void manageIntent() {
        if(getIntent().hasExtra(GlobalObjects.ModelList.Restaurant.toString()) && getIntent().hasExtra(GlobalObjects.ModelList.Category.toString())) {
            CategoryModel categoryModel = (CategoryModel)getIntent().getSerializableExtra(GlobalObjects.ModelList.Category.toString());
            String restaurantID = getIntent().getStringExtra(GlobalObjects.ModelList.Restaurant.toString());
            topHeading.setText(categoryModel.getName());
            getSubCategory(categoryModel.get_id(),restaurantID);
        }
    }

    private void getSubCategory(String categoryId,String restaurantID) {
        try {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            apiInterface.getSubCategoryOfCategory(categoryId).enqueue(new Callback<List<SubCategoryModel>>() {
                @Override
                public void onResponse(Call<List<SubCategoryModel>> call, Response<List<SubCategoryModel>> response) {
                    try {
                        Log.i("TAG","TAG: Code"+response.code()+" Message "+response.message());
                        if(response.code() == 200) {
                            List<SubCategoryModel> list = response.body();
                            subCategoryRecycle.setAdapter(new SubCategoryRecycleAdapter(list,SubcategoryListActivity.this,restaurantID,categoryId));
                        }
                    } catch (Exception ex) {
                        Log.i("TAG","TAG "+ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<SubCategoryModel>> call, Throwable t) {
                    Log.i("TAG","TAG "+t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.i("TAG","TAG "+ex.getMessage());
        }
    }

}