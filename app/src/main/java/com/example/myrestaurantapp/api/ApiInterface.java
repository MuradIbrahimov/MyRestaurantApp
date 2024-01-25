package com.example.myrestaurantapp.api;

import com.example.myrestaurantapp.domain.Foods;
import com.example.myrestaurantapp.domain.Location;
import com.example.myrestaurantapp.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("api/register")
    Call<User> saveUser(@Body User user);

    @POST("api/login")
    Call<User> loginUser(@Body User user);

    @POST("api/users/guest")
    Call<User> createGuestUser();

    @GET("api/foods/best-foods")
    Call<List<Foods>> getBestFoods();
    @GET("api/locations")
    Call<List<Location>> getLocations();

}
