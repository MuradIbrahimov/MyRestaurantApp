package com.example.myrestaurantapp.api;

import com.example.myrestaurantapp.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("api/register")
    Call<User> saveUser(@Body User user);

    @POST("api/login")
    Call<User> loginUser(@Body User user);

    @POST("api/users/guest")
    Call<User> createGuestUser();

}
