package com.example.seatsense.utils;

public class NetworkUtils {

    public static final String BASE_URL = "http://192.168.237.2:8000";

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