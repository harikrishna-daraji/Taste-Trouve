package com.example.tastetrouverestaurantowner;

import com.example.tastetrouverestaurantowner.Modal.ProductModal;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("product/add")
    Call<ResponseBody> createProduct(
            @Field("restaurantId") String restaurantId,
            @Field("categoryId") String categoryId,
            @Field("subCategoryId") String subCategoryId,
            @Field("name") String name,
            @Field("image") String image,
            @Field("price") Integer price,
            @Field("description") String description,
            @Field("calories") String calories,
            @Field("quantity") Integer quantity,
            @Field("kidSection") Boolean kidSection,
            @Field("popular") Boolean popular,
            @Field("DeliveryTime") String DeliveryTime
    );

    @FormUrlEncoded
    @POST("restaurantUsers/register")
    Call<ResponseBody> SignUp(
            @Field("restaurantName") String restaurantName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcmToken") String fcmToken,
            @Field("phoneNumber") String phoneNumber,
            @Field("status") Boolean status,
            @Field("userType") String userType
    );


    @FormUrlEncoded
    @POST("restaurantUsers/login")
    Call<ResponseBody> loginUser(
            @Field("email") String email,
            @Field("password") String passwor
    );

    @FormUrlEncoded
    @POST("product/getProductsByRestaurant")
    Call<List<ProductModal>> getproducts(
            @Field("restaurantId") String restaurantId
    );

    @FormUrlEncoded
    @PUT("product/deleteProduct")
    Call<List<ProductModal>> deleteProduct(
            @Field("productId") String productId
    );


    @FormUrlEncoded
    @PUT("product/update")
    Call<ResponseBody> updateProduct(
            @Field("productId") String productId,
            @Field("name") String name,
            @Field("image") String image,
            @Field("price") Integer price,
            @Field("description") String description,
            @Field("calories") String calories,
            @Field("quantity") Integer quantity,
            @Field("kidSection") Boolean kidSection,
            @Field("popular") Boolean popular,
            @Field("DeliveryTime") String DeliveryTime
    );
}
