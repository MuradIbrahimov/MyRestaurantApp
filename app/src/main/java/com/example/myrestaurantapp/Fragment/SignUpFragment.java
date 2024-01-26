package com.example.myrestaurantapp.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myrestaurantapp.R;
import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private ApiInterface apiInterface;

    private EditText adminNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        // Initialize your UI components
        // Change these IDs to match the ones in your XML layout
        adminNameEditText = root.findViewById(R.id.signUpUsername);
        emailEditText = root.findViewById(R.id.signUpEmail);
        passwordEditText = root.findViewById(R.id.signUpPassword);
        confirmPasswordEditText = root.findViewById(R.id.signUpRepeatPass);
        signUpButton = root.findViewById(R.id.SignUpBtn);

        // Initialize your Retrofit API interface
        apiInterface = com.example.myrestaurantapp.api.RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // Set an OnClickListener for the signUpButton
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        return root;
    }

    private void signUpUser() {
        // Retrieve user input
        String adminName = adminNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate user input
        if (adminName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Retrieve the user token from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userToken = sharedPreferences.getString("token", "");  // Replace with your actual token

        // Create a User object with the input data
        User newUser = new User(adminName, email, password, "ROLE_ADMIN");

        // Make API call to sign up the user with the 'Authorization' header
        Call<User> call = apiInterface.signUpUser(userToken, newUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    User signedUpUser = response.body();
                    // Do something with signedUpUser
                    Toast.makeText(getContext(), "User signed up successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error response here
                    String errorMessage = "Failed to sign up user.";

                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }

                    // Display error message using Toast
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle failure here
                String errorMessage = "Failed to sign up user. Please check your internet connection.";

                if (t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }

                // Display error message using Toast
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
