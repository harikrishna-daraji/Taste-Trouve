package com.example.tastetrouve.Models;

public class FilterCatergoryModel {
    String name,id;

    public FilterCatergoryModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FilterCatergoryModel{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
