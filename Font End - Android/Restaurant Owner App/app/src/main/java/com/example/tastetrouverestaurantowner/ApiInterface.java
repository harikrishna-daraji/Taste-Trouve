package com.example.tastetrouverestaurantowner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("add")
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

}
