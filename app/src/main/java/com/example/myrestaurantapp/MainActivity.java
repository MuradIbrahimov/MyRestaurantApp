package com.example.myrestaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the "Sahibkar" button (button2) in the MainActivity layout
        Button sahibkarButton = findViewById(R.id.signInBtn);

        // Set an OnClickListener for the button
        sahibkarButton.setOnClickListener(view -> {
            // Start the LoginActivity when the button is clicked
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}