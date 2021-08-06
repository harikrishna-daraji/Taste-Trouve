package com.example.tastetrouve.HelperClass;

import com.example.tastetrouve.Models.CartModel;
import com.example.tastetrouve.Models.FavouriteModel;
import com.example.tastetrouve.Models.HomeProductModel;
import com.example.tastetrouve.Models.ItemProductModel;
import com.example.tastetrouve.Models.MyOrderModel;
import com.example.tastetrouve.Models.OrderDetailModel;
import com.example.tastetrouve.Models.SubCategoryModel;
import com.example.tastetrouve.Models.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("HomeScreen/getHomeProduct")
    Call<HomeProductModel> getHomeProduct(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("product/getProductsByMainCategory")
    Call<List<ItemProductModel>> getProductsOfMainCategory(
            @Field("categoryId") String categoryId ,
            @Field("userId") String userId );


    @FormUrlEncoded
    @POST("product/getDrinksProducts")
    Call<List<ItemProductModel>> getDrinksProducts(@Field("restaurantId") String restaurantId);


    @FormUrlEncoded
    @POST("clientUser/register")
    Call<UserModel> registerUser(@Field("email") String email, @Field("password") String password, @Field("displayname")
            String displayname, @Field("fcmToken") String   fcmToken, @Field("phoneNumber") String  phoneNumber, @Field("dateOfBirth") String dateOfBirth);


    @FormUrlEncoded
    @POST("clientUser/login")
    Call<UserModel> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("clientUser/getUserById")
    Call<UserModel> getUserDetails(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("subCategory/getSubById")
    Call<List<SubCategoryModel>> getSubCategoryOfCategory(@Field("categoryId") String categoryId);

    @FormUrlEncoded
    @POST("product/getProducts")
    Call<List<ItemProductModel>> getProductOfRestaurant(@Field("restaurantId") String restaurantId,@Field("categoryId") String categoryId,
                                                        @Field("subCategoryId") String subCategoryId,
                                                        @Field("userId") String userId
                                                        );
    @FormUrlEncoded
    @POST("product/get")
    Call<List<ItemProductModel>> getAllProducts(@Field("userId") String userId);

 @FormUrlEncoded
    @POST("product/getSpecialOfferProducts")
    Call<List<ItemProductModel>> getSpecialProducts(
            @Field("userId") String userId,
            @Field("specialType") String specialType

 );

    @FormUrlEncoded
    @PUT("clientUser/update")
    Call<ResponseBody> updateUser(@Field("phoneNumber") String phone, @Field("password") String password);

    @FormUrlEncoded
@PUT("clientUser/updateById")
    Call<ResponseBody> updateUseredetail(@Field("displayname") String displayname,
                                         @Field("userId") String userId,
                                         @Field("phoneNumber") String phoneNumber,
                                         @Field("dateOfBirth") String dateOfBirth
);

    @FormUrlEncoded
    @PUT("clientUser/update")
    Call<ResponseBody> updateUserFcmToken(@Field("phoneNumber") String phone ,@Field("fcmToken") String fcmToken);

    @FormUrlEncoded
    @POST("address/add")
    Call<ResponseBody> addAddress(@Field("userId") String userId, @Field("address") String address, @Field("lat") String lat, @Field("long") String _long);

    @FormUrlEncoded
    @POST("address/getAddresByUser")
    Call<ResponseBody> getAddressList(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("Order/getOrderByUser")
    Call<List<MyOrderModel>> getMyOrders(
            @Field("userId") String userId

    );

    @FormUrlEncoded
    @POST("favourite/getFavByUser")
    Call<List<FavouriteModel>> getFavouriteItems(
            @Field("userId") String userId

    );

    @FormUrlEncoded
    @POST("Order/getOrderById")
    Call<List<OrderDetailModel>> getMyOrdersByUser(
            @Field("orederId") String orederId

    );

    @FormUrlEncoded
    @POST("Cart/add")
    Call<ResponseBody> addToCart(@Field("userId") String userId, @Field("productId") String productId, @Field("quantity") String quantity, @Field("restaurantId") String restaurantId);

    @FormUrlEncoded
    @POST("Cart/getCartByUser")
    Call<List<CartModel>> getUserCart(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("Cart/updateQuantity")
    Call<ResponseBody> updateCart(@Field("cartId") String cartId, @Field("quantity") String quantity);

@FormUrlEncoded
    @POST("favourite/toogleFav")
    Call<ResponseBody> toggleFav(@Field("userId") String userId,
                                 @Field("productId") String productId,
                                 @Field("flag") String flag
                                );


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "address/delete", hasBody = true)
    Call<ResponseBody> deleteAddress(@Field("addressId") String addressId);


    @POST("Order/add")
    Call<ResponseBody> addOrder(@Body HashMap<String,Object> jsonObject);

    @FormUrlEncoded
    @POST("Cart/getCartCountByUser")
    Call<ResponseBody> getCartCount(@Field("userId") String userId);


    @FormUrlEncoded
    @PUT("Order/addReview")
    Call<ResponseBody> addReview(
            @Field("orderId") String orderId,
            @Field("ratingStar") Double ratingStar,
            @Field("ratingReview") String ratingReview
    );
}
