package com.example.tastetrouveadmin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("restaurantUsers/getRestaurants")
    Call<List<UData>> getUser();

}
