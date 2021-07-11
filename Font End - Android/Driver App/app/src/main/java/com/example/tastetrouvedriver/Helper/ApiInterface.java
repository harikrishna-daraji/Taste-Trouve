package com.example.tastetrouvedriver.Helper;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("clientUser/register")
    Call<ResponseBody> SignUp(
            @Field("displayname") String displayname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("fcmToken") String fcmToken,
            @Field("phoneNumber") String phoneNumber,
            @Field("isDriver") Boolean isDriver,
            @Field("dateOfBirth") String dateOfBirth

    );



    @FormUrlEncoded
    @POST("clientUser/driverLogin")
    Call<ResponseBody> loginUser(
            @Field("email") String email,
            @Field("password") String password


    );


}
