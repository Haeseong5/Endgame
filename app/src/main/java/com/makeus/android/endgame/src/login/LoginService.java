package com.makeus.android.endgame.src.login;


import android.content.SharedPreferences;
import android.util.Log;

import com.makeus.android.endgame.src.login.interfaces.LoginActivityView;
import com.makeus.android.endgame.src.login.interfaces.LoginRetrofitInterface;
import com.makeus.android.endgame.src.login.models.DefaultResponse;
import com.makeus.android.endgame.src.login.models.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;
import static com.makeus.android.endgame.src.ApplicationClass.sSharedPreferences;

public class LoginService {
    private final LoginActivityView mLoginActivityView;

    public LoginService(final LoginActivityView loginActivityView) {
        this.mLoginActivityView = loginActivityView;
    }

    void postLogin(String email, String pw) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("pw", pw);

        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);
        loginRetrofitInterface.postLogin(hashMap).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                final LoginResponse loginResponse = response.body();
                if (loginResponse == null) {
                    mLoginActivityView.validateFailure(null);
                    return;
                }
                String jwt = loginResponse.getJwt() + "";
                if (jwt != null && jwt.length() != 0) {
                    SharedPreferences.Editor editor = sSharedPreferences.edit();
                    editor.putString(X_ACCESS_TOKEN, loginResponse.getJwt()).apply();
                }
                mLoginActivityView.validateSuccess(loginResponse.isSuccess(), loginResponse.getCode(), loginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mLoginActivityView.validateFailure(null);
            }
        });
    }

    void postValidateGoogleLogin(String token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", token);

        final LoginRetrofitInterface loginRetrofitInterface = getRetrofit().create(LoginRetrofitInterface.class);
        loginRetrofitInterface.postValidateGoogle(hashMap).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                final LoginResponse loginResponse = response.body();
                if (loginResponse == null) {
                    mLoginActivityView.validateFailure(null);
                    return;
                }
                String jwt = loginResponse.getJwt() + "";
                if (jwt != null && jwt.length() != 0) {
                    SharedPreferences.Editor editor = sSharedPreferences.edit();
                    editor.putString(X_ACCESS_TOKEN, loginResponse.getJwt()).apply();
                }
                mLoginActivityView.validateSuccess(loginResponse.isSuccess(), loginResponse.getCode(), loginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mLoginActivityView.validateFailure(null);
            }
        });
    }

}
