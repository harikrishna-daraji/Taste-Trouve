package com.example.tastetrouveadmin;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @GET("restaurantUsers/getRestaurants")
    Call<List<UData>> getUser();




    @FormUrlEncoded
    @PUT("restaurantUsers/UpdateRestuarantStatus")
    Call<List<UData>> updateRestaurantStatus(
            @Field("restaurantId") String restaurantId,
            @Field("updateStatus") String updateStatus
    );

    @FormUrlEncoded
    @POST("restaurantUsers/register")
    Call<ResponseBody> SignUp(
            @Field("restaurantName") String restaurantName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("userType") String userType
    );




}

