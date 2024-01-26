package com.example.myrestaurantapp.api;

import com.example.myrestaurantapp.authentication.LoginRequest;
import com.example.myrestaurantapp.authentication.LoginResponse;
import com.example.myrestaurantapp.domain.Category;
import com.example.myrestaurantapp.domain.Foods;
import com.example.myrestaurantapp.domain.Location;
import com.example.myrestaurantapp.domain.Price;
import com.example.myrestaurantapp.domain.Time;
import com.example.myrestaurantapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
    @POST("api/users")
    Call<User> signUpUser(@Header("Authorization") String token, @Body User user);

    @POST("/api/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("api/users/guest")
    Call<User> createGuestUser();

    @GET("api/foods/best-foods")
    Call<List<Foods>> getBestFoods();
    @GET("api/foods")
    Call<List<Foods>> getAllFoods();

    @GET("api/locations")
    Call<List<Location>> getLocations();

    @GET("api/times")
    Call<List<Time>> getTime();

    @GET("api/prices")
    Call<List<Price>> getPrice();

    @GET("api/categories")
    Call<List<Category>> getCategories();

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") String id);

}
