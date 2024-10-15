package com.example.seatsense.utils;

public class NetworkUtils {
    public static final String SERVER_ADDRESS  = "192.168.237.2:8000";
    public static final String BASE_URL = "http://" + SERVER_ADDRESS;
    public static final String BASE_WEBSOCKET_URL = "ws://" + SERVER_ADDRESS;

    public static class ErrorResponse {
        private String detail;

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}