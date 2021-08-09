package com.example.tastetrouvedriver.Helper;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "http://192.168.2.13:5000/api/";
    private static APIClient mInstance;
    private static Retrofit retrofit ;

    public  APIClient(boolean isCurrentOrder) {

        if(isCurrentOrder) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    }

    public static synchronized  APIClient getInstance(){
        if(mInstance==null){
            mInstance=new APIClient(false);
        }
        return  mInstance;
    }

    public static synchronized APIClient getCurrentOrderInstance() {
            if(mInstance==null){
                mInstance=new APIClient(true);
            }
            return  mInstance;
    }

    public com.example.tastetrouvedriver.Helper.ApiInterface getApi(){
        return  retrofit.create(com.example.tastetrouvedriver.Helper.ApiInterface.class);
    }
}
