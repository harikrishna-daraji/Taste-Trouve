package com.example.tastetrouve.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.tastetrouve.Activities.BaseActivity;
import com.example.tastetrouve.Models.GlobalObjects;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.KidSectionModel;
import com.example.tastetrouve.Models.PopularSectionModel;
import com.example.tastetrouve.R;

public class ItemDetailsActivity extends BaseActivity {

    SharedPreferences sharedPreferences;
    ImageView itemImg;
    int quantity = 1, totalQuantity;
    TextView itemNameTV, kidPriceTV, deliveryTV, calorieTV, descriptionTV, quantityTV, adjustableQuantTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_item_details);
        initUI();
        manageIntent();
    }

    private void initUI() {
        itemImg = findViewById(R.id.itemImg);
        itemNameTV = findViewById(R.id.itemNameTV);
        kidPriceTV = findViewById(R.id.kidPriceTV);
        deliveryTV = findViewById(R.id.deliveryTV);
        calorieTV = findViewById(R.id.calorieTV);
        descriptionTV = findViewById(R.id.descriptionTV);
        quantityTV = findViewById(R.id.quantityTV);
        adjustableQuantTV = findViewById(R.id.adjustableQuantTV);
        findViewById(R.id.addCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < totalQuantity) {
                    quantity = quantity + 1;
                    adjustableQuantTV.setText(String.valueOf(quantity));
                }
            }
        });
        findViewById(R.id.minusCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 1) {
                    quantity = quantity - 1;
                    adjustableQuantTV.setText(String.valueOf(quantity));
                }
            }
        });
    }

    private void manageIntent() {
        if(getIntent().hasExtra("product") && getIntent().hasExtra("type")) {
            if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Item.toString())) {
                ItemProductModel model = (ItemProductModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Popular.toString())) {
                PopularSectionModel model = (PopularSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
            } else if(getIntent().getStringExtra("type").equals(GlobalObjects.ModelList.Kid.toString())) {
                KidSectionModel model = (KidSectionModel) getIntent().getSerializableExtra("product");
                Glide.with(this).load(model.getImage()).placeholder(R.drawable.image_placeholder).into(itemImg);
                itemNameTV.setText(model.getName());
                kidPriceTV.setText("$"+model.getPrice());
                deliveryTV.setText(model.getDeliveryTime());
                calorieTV.setText(model.getCalories());
                descriptionTV.setText(model.getDescription());
                quantityTV.setText(getString(R.string.quantity)+": "+model.getQuantity());
                totalQuantity = model.getQuantity();
            }
        }
        Log.i("TAG","TAG "+descriptionTV.getText().toString());
    }

}