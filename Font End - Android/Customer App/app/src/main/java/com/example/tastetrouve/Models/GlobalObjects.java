package com.example.tastetrouve.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class GlobalObjects {

    public enum ModelList {
        Kid, Popular, Item, Restaurant, Category, Subcategory;
    }

    public enum Category {
        main_course, appetizer, dessert;
    }

    public static final int MAP_REQUEST_CODE = 100;

    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
