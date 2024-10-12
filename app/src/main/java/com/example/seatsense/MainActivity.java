package com.example.seatsense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seatsense.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TOKEN_KEY = "auth_token";
    private static final String PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Simulate loading screen for 2 seconds
        new Handler().postDelayed(() -> checkToken(), 2000);
    }

    private void checkToken() {
        // Retrieve token from SharedPreferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String token = preferences.getString(TOKEN_KEY, null);

        //temporary token
//    if (token == null) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(TOKEN_KEY, "dummy_token");  // Add a dummy token
//        editor.apply();
//        token = "dummy_token";
//    }

        if (token == null) {

            // No token, redirect to login screen
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();

        } else {
            // Token exists, redirect to home screen
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}