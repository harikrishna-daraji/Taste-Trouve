package com.example.tastetrouve.HelperClass;

import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.SubCategoryModel;
import com.example.tastetrouve.Models.UserModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @GET("HomeScreen/getHomeProduct")
    Call<HomeProductModel> getHomeProduct();

    @FormUrlEncoded
    @POST("product/getProductsByMainCategory")
    Call<List<ItemProductModel>> getProductsOfMainCategory(@Field("categoryId") String categoryId);


    @FormUrlEncoded
    @POST("clientUser/register")
    Call<UserModel> registerUser(@Field("email") String email, @Field("password") String password, @Field("displayname")
            String displayname, @Field("fcmToken") String   fcmToken, @Field(" phoneNumber") String  phoneNumber, @Field("dateOfBirth") String dateOfBirth);


    @FormUrlEncoded
    @POST("clientUser/login")
    Call<UserModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("subCategory/getSubById")
    Call<List<SubCategoryModel>> getSubCategoryOfCategory(@Field("categoryId") String categoryId);

    @FormUrlEncoded
    @POST("product/getProducts")
    Call<List<ItemProductModel>> getProductOfRestaurant(@Field("restaurantId") String restaurantId,@Field("categoryId") String categoryId, @Field("subCategoryId") String subCategoryId);

    @GET("product/get")
    Call<List<ItemProductModel>> getAllProducts();

    @PUT("clientUser/update")
    Call<ResponseBody> updateUser(@Field("phone") String phone, @Field("password") String password);

}
