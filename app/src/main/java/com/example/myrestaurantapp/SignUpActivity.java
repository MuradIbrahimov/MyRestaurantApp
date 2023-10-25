package com.example.myrestaurantapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button confirmBtn = findViewById(R.id.signInBtn);

        // Set an OnClickListener for the button
        confirmBtn.setOnClickListener(view -> {
            // Start the LoginActivity when the button is clicked
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}