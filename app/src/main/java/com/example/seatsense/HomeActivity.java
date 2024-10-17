package com.example.seatsense;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.seatsense.AttendanceActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HomeActivity extends AppCompatActivity {
    FrameLayout OccupancyLayoutButton;
    FrameLayout attendaneLayoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        OccupancyLayoutButton = findViewById(R.id.occupancyLayoutButton);
        attendaneLayoutButton = findViewById(R.id.attendanceLayoutButton);

        OccupancyLayoutButton.setOnClickListener(view -> {
            Intent occupancyIntent = new Intent(HomeActivity.this, OccupancyActivity.class);
            startActivity(occupancyIntent);
        });
        attendaneLayoutButton.setOnClickListener(v -> {
            Intent attendanceIntent = new Intent(HomeActivity.this, AttendanceActivity.class);
            startActivity(attendanceIntent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}
