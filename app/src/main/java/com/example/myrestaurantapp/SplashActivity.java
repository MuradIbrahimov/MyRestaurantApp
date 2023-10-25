package com.example.myrestaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView imageView;
    ObjectAnimator scaleXAnimator;
    ObjectAnimator scaleYAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.imageView);

        scaleXAnimator = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.6f, 1.5f);
        scaleYAnimator = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.6f, 1.5f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(3000);
        animatorSet.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}
