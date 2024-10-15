package com.example.seatsense.routermodels;

import com.google.gson.annotations.SerializedName;

public class Login {
    public static class Request {
        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        public Request(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class Response {
        @SerializedName("access_token")
        private String token;

        public String getToken() {
            return token;
        }
    }
}
