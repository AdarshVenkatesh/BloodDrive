package com.example.harika.blooddriveah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;


    /* Duration of wait */
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        runSplashScreen();
    }
    /* New Handler to start the Menu-Activity
     * and close this Splash-Screen after some seconds.*/
     /*   new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
            /*    Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }*/
    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
    public void redirectUser() {
        Intent userRedirectionIntent;
        if (firebaseAuth.getCurrentUser() != null) {
           /* Redirect to Events page */
            userRedirectionIntent = new Intent(this,LoginActivity.class);
        }
        else {
            userRedirectionIntent = new Intent(this, HomeActivity.class);
        }
        startActivity(userRedirectionIntent);}

    public void runSplashScreen() {
        Runnable splashScreen = new Runnable() {
            @Override
            public void run() {
                redirectUser();
            }
        };
        android.os.Handler myHandler = new android.os.Handler();
        myHandler.postDelayed(splashScreen, 500);
    }
}