package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

// This class is used to create the splash screen animations
public class splash_screen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    Animation topAnimation, botAnimation;
    ImageView logosplash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Setting animations for logo and text
        logosplash = findViewById(R.id.logodum);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        botAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logosplash.setAnimation(botAnimation);


        // Created in order to mesh two different classes together for animation adapting. Splash screen
        // animation will adapt to Login class layout.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, Landing.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(logosplash, "logo_image");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (splash_screen.this, pairs);
                finish();
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_SCREEN);

    }
}