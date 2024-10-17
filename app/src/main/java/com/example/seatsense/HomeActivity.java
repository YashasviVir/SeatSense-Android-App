package com.example.seatsense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.example.seatsense.AttendanceActivity;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button viewOccupancyButton;
    private Button viewAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewOccupancyButton = findViewById(R.id.viewOccupancyButton);
        viewAttendance = findViewById(R.id.viewAttendance);

        viewOccupancyButton.setOnClickListener(v -> {
            Intent occupancyIntent = new Intent(HomeActivity.this, OccupancyActivity.class);
            startActivity(occupancyIntent);
        });
        viewAttendance.setOnClickListener(v -> {
            Intent attendanceIntent = new Intent(HomeActivity.this, AttendanceActivity.class);
            startActivity(attendanceIntent);
        });
    }
}
