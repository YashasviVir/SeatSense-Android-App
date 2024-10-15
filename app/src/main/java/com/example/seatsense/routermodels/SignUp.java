package com.example.seatsense.routermodels;

import com.google.gson.annotations.SerializedName;

public class SignUp {
    public static class Request {
        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        public Request(String firstName, String lastName, String email, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
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