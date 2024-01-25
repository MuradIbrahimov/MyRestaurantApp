package com.example.myrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.entity.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity {

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        apiInterface = com.example.myrestaurantapp.api.RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        Button sahibkarButton = findViewById(R.id.signInBtn);
        Button guestButton = findViewById(R.id.buttonGuest);

        sahibkarButton.setOnClickListener(view -> {
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        guestButton.setOnClickListener(view -> {
            createGuestUser();
        });
    }

    private void createGuestUser() {
        Call<User> call = apiInterface.createGuestUser();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    User guestUser = response.body();
                    // Do something with guestUser
                    Intent intent = new Intent(IntroActivity.this, MainPage.class);
                    startActivity(intent);
                } else {
                    // Handle error response here
                    String errorMessage = "Failed to create guest user.";

                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Display error message using Toast
                    Toast.makeText(IntroActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure here
                String errorMessage = "Failed to create guest user. Please check your internet connection.";

                if (t instanceof IOException) {
                    errorMessage = t.getMessage();
                } else {
                    errorMessage = "Failed to create guest user. Error: " + t.getMessage();
                }

                // Display error message using Toast
                Toast.makeText(IntroActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }



        });
    }
}
