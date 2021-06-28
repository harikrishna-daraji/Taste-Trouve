package com.example.tastetrouve.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tastetrouve.Adapters.SliderAdapter;
import com.example.tastetrouve.HelperClass.SliderItem;
import com.example.tastetrouve.R;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class OnBoardingScreen extends BaseActivity {

    private ViewPager2 viewPager2;
    SharedPreferences sharedPreferences;

    TextView boardingTV;
    RelativeLayout signInRelative;
    List<String> textList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(loadStyle(false));
        sharedPreferences = getApplicationContext().getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("code","en");
        setLanguage(language);
        setContentView(R.layout.activity_on_boarding_screen);
        initUI();
        textList.add(getString(R.string.boarding_one_text));
        textList.add(getString(R.string.boarding_two_text));
        textList.add("");
        boardingTV.setText(textList.get(0));
    }

    private void initUI() {
        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        boardingTV = findViewById(R.id.boardingTV);
        signInRelative = findViewById(R.id.signInRelative);

        signInRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OnBoardingScreen.this,SignIn.class);
                startActivity(intent);
            }
        });

        List<SliderItem> sliderItemList = new ArrayList<>();
        sliderItemList.add(new SliderItem(R.drawable.boarding_0));
        sliderItemList.add(new SliderItem(R.drawable.boarding_1));
        sliderItemList.add(new SliderItem(R.drawable.boarding_image3));

        viewPager2.setAdapter(new SliderAdapter(sliderItemList,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
                boardingTV.setText(textList.get(viewPager2.getCurrentItem()));
                if(viewPager2.getCurrentItem() == 2) {
                    boardingTV.setVisibility(View.GONE);
                    signInRelative.setVisibility(View.VISIBLE);
                } else {
                    boardingTV.setVisibility(View.VISIBLE);
                    signInRelative.setVisibility(View.GONE);
                }
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
    }

}