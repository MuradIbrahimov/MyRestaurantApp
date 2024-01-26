package com.example.myrestaurantapp.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.myrestaurantapp.R;
import com.example.myrestaurantapp.api.ApiInterface;
import com.example.myrestaurantapp.sqLite.SqLite;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Activity currentActivity;
    private ApiInterface apiInterface;
    private String userId;
    private String userToken;
    private SqLite sqliteHelper;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Store the current activity when the fragment is attached
        currentActivity = getActivity();

        // Initialize Retrofit API interface
        apiInterface = com.example.myrestaurantapp.api.RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        // Initialize SQLite helper
        sqliteHelper = new SqLite(currentActivity);

        // Retrieve user data from SharedPreferences
        getUserData();

        TextView userDetailsTextView = root.findViewById(R.id.userDetails);

        // Set OnClickListener for "User Details" TextView
        userDetailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for "User Details" TextView
                writeUserDetailsToSQLite();
            }
        });

        TextView deleteUserTextView = root.findViewById(R.id.deleteUser);

        // Set OnClickListener for delete user layout
        deleteUserTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        // Find the logout TextView by its ID
        AppCompatTextView logout = root.findViewById(R.id.logoutText);

        // Set OnClickListener for logout TextView
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();
            }
        });

        return root;
    }

    private void writeUserDetailsToSQLite() {
        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = currentActivity.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("email", "");
        String sharedToken = sharedPreferences.getString("token", "");
        String userId = sharedPreferences.getString("id", "");
        // Insert user details into SQLite
        sqliteHelper.insertUserDetails(userEmail,sharedToken, userId);

        // Display a toast message indicating success
        Toast.makeText(getContext(), "User details written to SQLite", Toast.LENGTH_SHORT).show();
    }

    private void deleteUser() {
        Log.d("ProfileFragment", "userId: " + userId);
        Log.d("ProfileFragment", "userToken: " + userToken);

        // Clear SQLite database
        sqliteHelper.clearUserDetails();

        // Make API call to delete the user
        Call<Void> call = apiInterface.deleteUser(userId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    Toast.makeText(getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();

                    // Log out and terminate the app after deleting the user
                    signOut();
                } else {
                    // Handle error response here
                    String errorMessage = "Failed to delete user.";

                    if (response.errorBody() != null) {
                        errorMessage = response.errorBody().toString();
                    }

                    // Display error message using Toast
                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle failure here
                String errorMessage = "Failed to delete user. Please check your internet connection.";

                if (t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }

                // Display error message using Toast
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to log out and terminate the app
    private void signOut() {
        if (currentActivity != null) {
            // Finish the current activity to exit the app
            // Clear SQLite database
            clearSharedPreferences();
            sqliteHelper.clearUserDetails();
            currentActivity.finish();
        }
    }
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = currentActivity.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    // Function to retrieve user data from SharedPreferences
    private void getUserData() {
        SharedPreferences sharedPreferences = currentActivity.getSharedPreferences("user_data", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("id", "");
        userToken = sharedPreferences.getString("token", "");
    }
}
