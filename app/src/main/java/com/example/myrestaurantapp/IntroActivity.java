package com.example.myrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Find the "Sahibkar" button (button2) in the MainActivity layout
        Button sahibkarButton = findViewById(R.id.signInBtn);

        // Set an OnClickListener for the button
        sahibkarButton.setOnClickListener(view -> {
            // Start the LoginActivity when the button is clicked
            Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}