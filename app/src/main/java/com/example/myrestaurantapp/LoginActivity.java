package com.example.myrestaurantapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myrestaurantapp.Fragment.SignUpFragment;
import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.api.RetrofitClient;
import com.example.myrestaurantapp.authentication.LoginRequest;
import com.example.myrestaurantapp.authentication.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signUpText = findViewById(R.id.signUpText);
        Button signInBtn = findViewById(R.id.SignUpBtn);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        // Set an OnClickListener for the button
        signInBtn.setOnClickListener(view -> loginUser());

        signUpText.setOnClickListener(view -> {
            // Start the SignUpFragment when the text is clicked
            Intent intent = new Intent(LoginActivity.this, SignUpFragment.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        // Retrieve email and password from your UI components
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Check if email and password are not empty
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a login request
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Make API call
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginUser(loginRequest);

        Log.d("LoginActivity", "Attempting to login...");

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Login Activity", "Login response code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    // Login successful, handle the response
                    LoginResponse loginResponse = response.body();
                    Log.d("LoginActivity", "Login successful: " + loginResponse.getMessage());

                    // Save user data to SharedPreferences
                    saveUserData(loginResponse.getEmail(),loginResponse.getToken(), loginResponse.getRole() , loginResponse.getUserId());

                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    // Start the MainPage activity
                    Intent intent = new Intent(LoginActivity.this, MainPage.class);
                    startActivity(intent);
                } else {
                    // Login failed, handle the error
                    String errorBody = response.errorBody() != null ? response.errorBody().toString() : "Unknown Error Body";
                    Log.e("LoginActivity", "Login failed. Response code: " + response.code() + ", Error body: " + errorBody);

                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle failure
                Log.e("LoginActivity", "Login failed: " + t.getMessage());

                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData(String email , String token, String role , String id) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("token", token);
        editor.putString("role", role);
        editor.putString("id", id);
        editor.apply();
    }
}
