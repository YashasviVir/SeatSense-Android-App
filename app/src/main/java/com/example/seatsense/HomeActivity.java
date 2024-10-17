package com.example.seatsense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import com.example.seatsense.AttendanceActivity;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button viewAttendance;
    private Button logoutBtn;
    FrameLayout OccupancyLayoutButton;

    private static final String TOKEN_KEY = "auth_token";
    private static final String PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewAttendance = findViewById(R.id.viewAttendance);
        logoutBtn = findViewById(R.id.logout);

        OccupancyLayoutButton = findViewById(R.id.occupancyLayoutButton);

        OccupancyLayoutButton.setOnClickListener(view -> {
            Intent occupancyIntent = new Intent(HomeActivity.this, OccupancyActivity.class);
            startActivity(occupancyIntent);
        });
        viewAttendance.setOnClickListener(v -> {
            Intent attendanceIntent = new Intent(HomeActivity.this, AttendanceActivity.class);
            startActivity(attendanceIntent);
        });
        logoutBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            preferences.edit().remove(TOKEN_KEY).apply();

            Intent homeIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(homeIntent);
        });
    }
}
