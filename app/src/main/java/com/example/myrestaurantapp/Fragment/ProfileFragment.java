package com.example.myrestaurantapp.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myrestaurantapp.R;

public class ProfileFragment extends Fragment {

    private Activity currentActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Store the current activity when the fragment is attached
        currentActivity = getActivity();

        // Find the logout ImageView and TextView by their IDs
        ImageView logoutImageView = root.findViewById(R.id.list_make_image);
        TextView logoutTextView = root.findViewById(R.id.logoutText);

        // Set onClickListeners for both the ImageView and TextView
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the signOut function to log out and terminate the app
                signOut();
            }
        });

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the signOut function to log out and terminate the app
                signOut();
            }
        });

        return root;
    }

    // Function to log out and terminate the app
    private void signOut() {
        if (currentActivity != null) {
            // Finish the current activity to exit the app
            currentActivity.finish();
        }
    }
}
