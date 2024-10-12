package com.example.seatsense;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class OccupancyActivity extends AppCompatActivity {

    private GridLayout seatGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupancy);

        seatGridLayout = findViewById(R.id.seatGridLayout);
        seatGridLayout.setColumnCount(10);  // Set the grid layout to 10 columns

        // Dummy JSON data
        String jsonData = "{\"a1\":\"false\", \"a2\":\"true\", \"a3\":\"true\", \"a4\":\"false\", \"a5\":\"true\", " +
                "\"a6\":\"false\", \"a7\":\"true\", \"a8\":\"false\", \"a9\":\"true\", \"a10\":\"false\", " +
                "\"b1\":\"false\", \"b2\":\"true\", \"b3\":\"false\", \"b4\":\"true\", \"b5\":\"false\", " +
                "\"b6\":\"true\", \"b7\":\"false\", \"b8\":\"true\", \"b9\":\"false\", \"b10\":\"true\", " +
                "\"c1\":\"true\", \"c2\":\"false\", \"c3\":\"true\", \"c4\":\"false\", \"c5\":\"true\", " +
                "\"c6\":\"false\", \"c7\":\"true\", \"c8\":\"false\", \"c9\":\"true\", \"c10\":\"false\", " +
                "\"d1\":\"false\", \"d2\":\"true\", \"d3\":\"false\", \"d4\":\"true\", \"d5\":\"false\", " +
                "\"d6\":\"true\", \"d7\":\"false\", \"d8\":\"true\", \"d9\":\"false\", \"d10\":\"true\", " +
                "\"e1\":\"true\", \"e2\":\"false\", \"e3\":\"true\", \"e4\":\"false\", \"e5\":\"true\", " +
                "\"e6\":\"false\", \"e7\":\"true\", \"e8\":\"false\", \"e9\":\"true\", \"e10\":\"false\", " +
                "\"f1\":\"false\", \"f2\":\"true\", \"f3\":\"false\", \"f4\":\"true\", \"f5\":\"false\", " +
                "\"f6\":\"true\", \"f7\":\"false\", \"f8\":\"true\", \"f9\":\"false\", \"f10\":\"true\", " +
                "\"g1\":\"true\", \"g2\":\"false\", \"g3\":\"true\", \"g4\":\"false\", \"g5\":\"true\", " +
                "\"g6\":\"false\", \"g7\":\"true\", \"g8\":\"false\", \"g9\":\"true\", \"g10\":\"false\", " +
                "\"h1\":\"false\", \"h2\":\"true\", \"h3\":\"false\", \"h4\":\"true\", \"h5\":\"false\", " +
                "\"h6\":\"true\", \"h7\":\"false\", \"h8\":\"true\", \"h9\":\"false\", \"h10\":\"true\", " +
                "\"i1\":\"true\", \"i2\":\"false\", \"i3\":\"true\", \"i4\":\"false\", \"i5\":\"true\", " +
                "\"i6\":\"false\", \"i7\":\"true\", \"i8\":\"false\", \"i9\":\"true\", \"i10\":\"false\", " +
                "\"j1\":\"false\", \"j2\":\"true\", \"j3\":\"false\", \"j4\":\"true\", \"j5\":\"false\", " +
                "\"j6\":\"true\", \"j7\":\"false\", \"j8\":\"true\", \"j9\":\"false\", \"j10\":\"true\"}";

        // Simulate parsing the JSON and creating the seat layout
        createSeatLayout(jsonData);
    }

    private void createSeatLayout(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);

            // Loop from row 'j' to 'a' (reverse order)
            for (char row = 'j'; row >= 'a'; row--) {
                for (int seatNumber = 1; seatNumber <= 10; seatNumber++) {
                    String seatId = String.valueOf(row) + seatNumber;
                    boolean isOccupied = jsonObject.getBoolean(seatId);

                    Button seatButton = new Button(this);
                    seatButton.setText(seatId);
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = (int) (getResources().getDimension(R.dimen.button_width) / 2); // Use half the width
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                    seatButton.setLayoutParams(params);

                    // Set different colors based on occupancy status
                    if (isOccupied) {
                        seatButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark)); // Occupied
                    } else {
                        seatButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light)); // Available
                    }

                    seatGridLayout.addView(seatButton);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
        }
    }
}
