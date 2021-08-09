package com.example.tastetrouvedriver.Helper;

import android.content.Context;
import android.widget.Toast;

public class GlobalObjects {
    public static final String accepted_by_driver = "accepted by driver";
    public static final String rejected_by_driver = "rejected by driver";
    public static final String delivered = "delivered";

    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
