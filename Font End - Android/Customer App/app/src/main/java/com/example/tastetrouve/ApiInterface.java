package com.example.tastetrouve;

import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("HomeScreen/getHomeProduct")
    Call<HomeProductModel> getHomeProduct();

    @FormUrlEncoded
    @POST("product/getProductsByMainCategory")
    Call<List<ItemProductModel>> getProductsOfMainCategory(@Field("categoryId") String categoryId);


    @FormUrlEncoded
    @POST("clientUser/register")
    Call<ResponseBody> registerUser(@Field("email") String email,@Field("password") String password,@Field("displayname")
            String displayname,@Field("fcmToken") String   fcmToken,@Field(" phoneNumber") String  phoneNumber,@Field("dateOfBirth") String dateOfBirth);


}
