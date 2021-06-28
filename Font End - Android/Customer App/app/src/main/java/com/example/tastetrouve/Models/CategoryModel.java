package com.example.tastetrouve.Models;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    String _id;
    String name;
    String image;

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
