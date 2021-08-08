package com.example.tastetrouve.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class GlobalObjects {

    public enum ModelList {
        Kid, Popular, Item, Restaurant, Category, Subcategory;
    }

    public enum CartOperation {
        add, minus, remove
    }

    public enum Category {
        main_course, appetizer, dessert;
    }

    public static final int POPULAR_LIST_REFRESH = 40;
    public static final int KIDS_LIST_REFRESH = 30;
    public static final int RESTAURANT_REFRESH_CODE = 20;
    public static final int ITEM_LIST_REFRESH_CODE = 10;
    public static final int FAVOURITES_REFRESH_CODE = 1000;
    public static final int MAP_REQUEST_CODE = 100;

    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
