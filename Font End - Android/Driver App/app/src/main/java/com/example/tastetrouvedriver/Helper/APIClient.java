package com.example.tastetrouvedriver.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "http://192.168.137.1:5000/api/";
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

       public com.example.tastetrouvedriver.Helper.ApiInterface getApi(){
        return  retrofit.create(com.example.tastetrouvedriver.Helper.ApiInterface.class);
       }
    }
