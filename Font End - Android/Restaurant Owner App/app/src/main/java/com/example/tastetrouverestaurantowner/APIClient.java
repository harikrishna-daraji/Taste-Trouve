package com.example.tastetrouverestaurantowner;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "http://192.168.2.13:5000/api/product/";
    private static APIClient mInstance;
    private static Retrofit retrofit ;

    public  APIClient() {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        public static synchronized  APIClient getInstance(){
        if(mInstance==null){
            mInstance=new APIClient();
        }
        return  mInstance;
        }

       public  ApiInterface getApi(){
        return  retrofit.create(ApiInterface.class);
       }
    }
