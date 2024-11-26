package com.example.smartcard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smartcard.R;
import com.example.smartcard.SessionManager.SessionManager;

import java.util.HashMap;

public class SuccessPage extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_page);
        sessionManager = new SessionManager(SuccessPage.this);
        final HashMap<String, String> user = sessionManager.getUserDetails();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                System.out.println(user.get(SessionManager.L_STATUS));
                if (sessionManager.isLoggedIn()) {
                    if (user.get(SessionManager.L_STATUS).equals("parent")) {
                        Intent i = new Intent(SuccessPage.this, ParentHomeActivity.class);
                        startActivity(i);
                        finish();
                    } else if (user.get(SessionManager.L_STATUS).equals("student")) {
                        Intent i = new Intent(SuccessPage.this, HomeActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    Intent i = new Intent(SuccessPage.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }


                // close this activity
                finish();
            }
        }, 3000);
    }
    }


