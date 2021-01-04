package com.makeus.android.endgame.src.splash;


import android.util.Log;

import com.makeus.android.endgame.src.splash.interfaces.SplashActivityView;
import com.makeus.android.endgame.src.splash.interfaces.SplashRetrofitInterface;
import com.makeus.android.endgame.src.splash.models.AutoLoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class SplashService {
    private final SplashActivityView mSplashActivityView;

    public SplashService(final SplashActivityView splashActivityView) {
        this.mSplashActivityView = splashActivityView;
    }

    public void getAutoLogin() {
        final SplashRetrofitInterface mainRetrofitInterface = getRetrofit().create(SplashRetrofitInterface.class);
        mainRetrofitInterface.getAutoLogin().enqueue(new Callback<AutoLoginResponse>() {
            @Override
            public void onResponse(Call<AutoLoginResponse> call, Response<AutoLoginResponse> response) {
                final AutoLoginResponse autoLoginResponse = response.body();
                if (autoLoginResponse == null) {
                    mSplashActivityView.validateFailure(null);
                    return;
                }

                mSplashActivityView.validateGetAutoLoginSuccess(autoLoginResponse.isSuccess(), autoLoginResponse.getMessage());
            }

            @Override
            public void onFailure(Call<AutoLoginResponse> call, Throwable t) {
                mSplashActivityView.validateFailure(null);
            }
        });
    }

}
