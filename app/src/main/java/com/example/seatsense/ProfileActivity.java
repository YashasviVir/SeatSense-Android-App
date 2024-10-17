package com.example.seatsense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText firstName, lastName, email, employeeId;
    private RadioGroup genderGroup;
    private RadioButton radioMale, radioFemale;
    private Button updateButton;
    private Button logoutBtn;

    private static final String TOKEN_KEY = "auth_token";
    private static final String PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        employeeId = findViewById(R.id.employeeId);
        genderGroup = findViewById(R.id.genderGroup);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        updateButton = findViewById(R.id.updateButton);
        logoutBtn = findViewById(R.id.logout);

        // Set dummy data
        firstName.setText("Nayan");
        lastName.setText("Radadiya");
        email.setText("21bt04099@gsfcuniversity.ac.in");
        employeeId.setText("21BT04099");

        // Set default selection for gender (Male)
        radioMale.setChecked(true);

        // Set click listener for the Update button
        updateButton.setOnClickListener(v -> {
            String updatedFirstName = firstName.getText().toString();
            String updatedLastName = lastName.getText().toString();
            String updatedEmail = email.getText().toString();
            String updatedEmployeeId = employeeId.getText().toString();

            // Determine selected gender
            int selectedGenderId = genderGroup.getCheckedRadioButtonId();
            String gender = selectedGenderId == R.id.radioMale ? "Male" : "Female";

            // Display a toast message showing updated information (for now)
            Toast.makeText(ProfileActivity.this, "Updated Profile:\n" +
                    "First Name: " + updatedFirstName + "\n" +
                    "Last Name: " + updatedLastName + "\n" +
                    "Email: " + updatedEmail + "\n" +
                    "Employee ID: " + updatedEmployeeId + "\n" +
                    "Gender: " + gender, Toast.LENGTH_LONG).show();
        });

        logoutBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            preferences.edit().remove(TOKEN_KEY).apply();

            Intent homeIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(homeIntent);
        });
    }
}