package com.example.seatsense;

import static com.example.seatsense.utils.NetworkUtils.BASE_WEBSOCKET_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.Response;
import okio.ByteString;

public class OccupancyActivity extends AppCompatActivity {

    private GridLayout seatGridLayout;
    private WebSocket webSocket;
    private static final String TOKEN_KEY = "auth_token";
    private static final String PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupancy);

        seatGridLayout = findViewById(R.id.seatGridLayout);
        seatGridLayout.setColumnCount(10);  // Set the grid layout to 10 columns

        startWebSocketConnection();
    }

    private void startWebSocketConnection() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)  // No timeout for WebSocket
                .build();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String token = preferences.getString(TOKEN_KEY, null);

        Request request = new Request.Builder()
                .url(BASE_WEBSOCKET_URL + "/ws?token=" + token)  // Replace with your WebSocket URL
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                Log.d("WebSocket", "Connection opened");
//                webSocket.send("Hello, Server!");  // Example of sending a message
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d("WebSocket", "Received message: " + text);
                runOnUiThread(() -> updateSeatLayout(text));  // Update UI on the main thread
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                Log.d("WebSocket", "Received binary message");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("WebSocket", "Closing: " + reason);
                webSocket.close(1000, null);
                finish();
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                Log.e("WebSocket", "Connection failed", t);
                finish();
            }
        });

        client.dispatcher().executorService().shutdown();
    }

//    JSONObject jsonObject = new JSONObject(jsonData);

    private void updateSeatLayout(String jsonData) {
        try {
            JSONObject occupancyData = new JSONObject(jsonData);

            seatGridLayout.removeAllViews();

            // Create a map to group seats by row
            Map<String, List<String>> seatMap = new HashMap<>();

            // Find all seat identifiers in the JSON
            for (Iterator<String> it = occupancyData.keys(); it.hasNext(); ) {
                String seatId = it.next();
                String row = seatId.replaceAll("\\d+", "");  // Extract the row letter (e.g., A, B, C)

                if (!seatMap.containsKey(row)) {
                    seatMap.put(row, new ArrayList<>());  // Initialize the list for a new row
                }
                seatMap.get(row).add(seatId);  // Add seat to its row
            }

            // Determine the maximum number of seats in any row
            int maxSeats = 0;
            for (List<String> rowSeats : seatMap.values()) {
                maxSeats = Math.max(maxSeats, rowSeats.size());
            }

            // Set the column count based on the maxSeats
            seatGridLayout.setColumnCount(maxSeats);

            // Loop through the rows and add buttons dynamically
            for (String row : seatMap.keySet()) {
                List<String> seatsInRow = seatMap.get(row);

                // Add buttons for each seat in the row
                for (String seatId : seatsInRow) {
                    int isOccupied = occupancyData.getInt(seatId);

                    Button seatButton = new Button(this);
                    seatButton.setText(seatId);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
//                    params.width = (int) (getResources().getDimension(R.dimen.button_width) / 2);  // Adjust button width
                    params.width = 250;
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT;

//                    seatButton.setWidth(100);
                    seatButton.setLayoutParams(params);

                    if (isOccupied == 1) {
                        seatButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));  // Occupied
                    } else {
                        seatButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));  // Available
                    }

                    seatGridLayout.addView(seatButton);
                }

                // Fill up any empty seats in the row to align with maxSeats
                for (int i = seatsInRow.size(); i < maxSeats; i++) {
                    Button emptyButton = new Button(this);
                    emptyButton.setVisibility(View.INVISIBLE);
                    seatGridLayout.addView(emptyButton);
                }
            }
        } catch (JSONException e) {
            Log.e("Occupancy", e.getMessage());
            Toast.makeText(this, "Error parsing JSON data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webSocket.close(1000, null);
    }
}
