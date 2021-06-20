package com.example.tastetrouve;

import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("HomeScreen/getHomeProduct")
    Call<HomeProductModel> getHomeProduct();

    @GET("product/getProductsByMainCategory")
    Call<List<ItemProductModel>> getProductsOfMainCategory();

}
