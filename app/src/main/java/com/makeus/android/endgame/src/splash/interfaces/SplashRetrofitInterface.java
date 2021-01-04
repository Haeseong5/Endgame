package com.makeus.android.endgame.src.splash.interfaces;

import com.makeus.android.endgame.src.splash.models.AutoLoginResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SplashRetrofitInterface {

    @GET("/endgame/jwt")
    Call<AutoLoginResponse> getAutoLogin();
}
