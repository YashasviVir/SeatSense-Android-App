package com.example.seatsense;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private final OkHttpClient client = new OkHttpClient();  // OkHttp client for making network requests

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the EditText and Button views
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        Button registerButton = findViewById(R.id.register_button);

        // Handle the register button click
        registerButton.setOnClickListener(v -> {
            // Retrieve text from EditTexts
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            // Check if the fields are not empty
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Send registration data to the server
                registerUser(firstName, lastName, email, password);
            }
        });
    }

    private void registerUser(String firstName, String lastName, String email, String password) {
        String url = "http://192.168.177.2:8000/signup";  // Replace with your API URL
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Create JSON body with user data
        String jsonBody = "{\"first_name\":\"" + firstName + "\","
                + "\"last_name\":\"" + lastName + "\","
                + "\"email\":\"" + email + "\","
                + "\"password\":\"" + password + "\"}";
        RequestBody body = RequestBody.create(JSON, jsonBody);

        // Create request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Make asynchronous network call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Log the error to check for issues
                runOnUiThread(() -> System.out.println("Registration Failed: " + e.getMessage()));
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Registration successful
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(RegisterActivity.this, OccupancyActivity.class);
                        startActivity(loginIntent);
                        finish();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Registration Failed: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
