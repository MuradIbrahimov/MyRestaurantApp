package com.example.myrestaurantapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button confirmBtn = findViewById(R.id.signInBtn);
        TextView signUpText = findViewById(R.id.signUpText);
        // Set an OnClickListener for the button
        confirmBtn.setOnClickListener(view -> {
            // Start the LoginActivity when the button is clicked
            Intent intent = new Intent(LoginActivity.this, MainPage.class);
            startActivity(intent);
        });
        signUpText.setOnClickListener(view -> {
            // Start the LoginActivity when the button is clicked
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}