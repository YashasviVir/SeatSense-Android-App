package com.example.seatsense.services;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.seatsense.routermodels.Login;
import com.example.seatsense.routermodels.SignUp;
import com.example.seatsense.utils.ErrorHandler;
import com.example.seatsense.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class Auth {
    public interface AuthService {
        @POST("login")
        Call<Login.Response> login(@Body Login.Request loginRequest);
        
        @POST("signup")
        Call<SignUp.Response> signup(@Body SignUp.Request signUpRequest);
    }

    private static final AuthService authService = RetrofitClient.getClient().create(AuthService.class);

    public static void makeLoginRequest(String email, String password, final LoginCallback callback) {
        Login.Request loginRequest = new Login.Request(email, password);

        Call<Login.Response> call = authService.login(loginRequest);

        call.enqueue(new Callback<Login.Response>() {
            @Override
            public void onResponse(@NonNull Call<Login.Response> call, @NonNull Response<Login.Response> response) {
                if (!response.isSuccessful()) {
                    ErrorHandler.processError(response, callback, "Auth");
                } else {
                    Login.Response body = response.body();
                    if (body != null) {
                        String token = body.getToken();
                        callback.onSuccess(token);
                    } else {
                        callback.onError(-1, "Login body is null");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login.Response> call, @NonNull Throwable t) {
                callback.onError(-1, t.getMessage());
                Log.e("Auth", "Login request failed", t);
            }
        });
    }

    public static void makeSignUpRequest(String firstName, String lastName, String email, String password, final SignUpCallback callback) {
        SignUp.Request signUpRequest = new SignUp.Request(firstName, lastName, email, password);

        Call<SignUp.Response> call = authService.signup(signUpRequest);

        call.enqueue(new Callback<SignUp.Response>() {
            @Override
            public void onResponse(@NonNull Call<SignUp.Response> call, @NonNull Response<SignUp.Response> response) {
                if (!response.isSuccessful()) {
                    ErrorHandler.processError(response, callback, "Auth");
                } else {
                    SignUp.Response body = response.body();
                    if (body != null) {
                        String token = body.getToken();
                        callback.onSuccess(token);
                    } else {
                        callback.onError(-1, "Signup body is null");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUp.Response> call, @NonNull Throwable t) {
                callback.onError(-1, t.getMessage());
                Log.e("Auth", "Signup request failed", t);
            }
        });
    }

    public interface LoginCallback extends ErrorHandler.ErrorCallback {
        void onSuccess(String token);
    }

    public interface SignUpCallback extends ErrorHandler.ErrorCallback {
        void onSuccess(String token);
    }
}
