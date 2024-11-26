package com.example.smartcard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;

import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        sessionManager = new SessionManager(SplashScreenActivity.this);
        final HashMap<String, String> user = sessionManager.getUserDetails();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                System.out.println(user.get(SessionManager.L_STATUS));
                if (sessionManager.isLoggedIn()) {
                    if (user.get(SessionManager.L_STATUS).equals("parent")) {
                        Intent i = new Intent(SplashScreenActivity.this, ParentHomeActivity.class);
                        startActivity(i);
                        finish();
                    } else if (user.get(SessionManager.L_STATUS).equals("student")) {
                        Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }


                // close this activity
                finish();
            }
        }, 2000);
    }
}

