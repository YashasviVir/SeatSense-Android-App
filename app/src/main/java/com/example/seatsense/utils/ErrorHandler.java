package com.example.seatsense.utils;

import android.util.Log;
import androidx.annotation.NonNull;
import retrofit2.Response;


public class ErrorHandler {
    public static interface ErrorCallback {
        void onError(int errorCode, String errorMessage);
    }

    public static void processError(@NonNull Response<?> response, @NonNull ErrorCallback callback, String tag) {
        tag = tag != null ? tag : "Error";
        try {
            if (response.errorBody() != null) {
                String errorBody = response.errorBody().string();  // Might throw IOException
                Log.e(tag, "Error response: " + errorBody);

                // Parse the error body into your ErrorResponse model
                NetworkUtils.ErrorResponse errorResponse = RetrofitClient.getGson().fromJson(errorBody, NetworkUtils.ErrorResponse.class);
                callback.onError(response.code(), errorResponse.getDetail());
            } else {
                callback.onError(response.code(), "No error body returned from server.");
            }
        } catch (Exception e) {
            Log.e(tag, "Error parsing error response", e);
            callback.onError(response.code(), "An error occurred while parsing error response: " + e.getMessage());
        }
    }
}
