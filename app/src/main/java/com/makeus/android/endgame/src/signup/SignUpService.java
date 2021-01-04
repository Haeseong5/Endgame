package com.makeus.android.endgame.src.signup;

import android.content.SharedPreferences;

import com.makeus.android.endgame.src.login.interfaces.LoginActivityView;
import com.makeus.android.endgame.src.login.interfaces.LoginRetrofitInterface;
import com.makeus.android.endgame.src.login.models.LoginResponse;
import com.makeus.android.endgame.src.signup.interfaces.SignUpActivityView;
import com.makeus.android.endgame.src.signup.interfaces.SignUpRetrofitInterface;
import com.makeus.android.endgame.src.signup.models.SignUpResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;
import static com.makeus.android.endgame.src.ApplicationClass.sSharedPreferences;

public class SignUpService {
    private final SignUpActivityView mSignUpActivityView;

    public SignUpService(final SignUpActivityView signUpActivityView) {
        this.mSignUpActivityView = signUpActivityView;
    }

    void postSignUp(String nickname, String email, String pw) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("pw", pw);
        hashMap.put("nickname", nickname);

        final SignUpRetrofitInterface signUpRetrofitInterface = getRetrofit().create(SignUpRetrofitInterface.class);
        signUpRetrofitInterface.postSignUp(hashMap).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                final SignUpResponse signUpResponse = response.body();
                if (signUpResponse == null) {
                    mSignUpActivityView.validateFailure("fail");
                    return;
                }
                String jwt = signUpResponse.getJwt() + "";
                if (jwt != null && jwt.length() != 0) {
                    SharedPreferences.Editor editor = sSharedPreferences.edit();
                    editor.putString(X_ACCESS_TOKEN, signUpResponse.getJwt()).apply();
                }
                mSignUpActivityView.validateSuccess(signUpResponse.isSuccess(), signUpResponse.getMessage(), signUpResponse.getCode());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                mSignUpActivityView.validateFailure(null);
            }
        });
    }

    void postSignUpGoogle(String nickname, String email) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("nickname", nickname);

        final SignUpRetrofitInterface signUpRetrofitInterface = getRetrofit().create(SignUpRetrofitInterface.class);
        signUpRetrofitInterface.postSignUpGoogle(hashMap).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                final SignUpResponse signUpResponse = response.body();
                if (signUpResponse == null) {
                    mSignUpActivityView.validateFailure("fail");
                    return;
                }
                String jwt = signUpResponse.getJwt() + "";
                if (jwt != null && jwt.length() != 0) {
                    SharedPreferences.Editor editor = sSharedPreferences.edit();
                    editor.putString(X_ACCESS_TOKEN, signUpResponse.getJwt()).apply();
                }
                mSignUpActivityView.validateSuccess(signUpResponse.isSuccess(), signUpResponse.getMessage(), signUpResponse.getCode());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                mSignUpActivityView.validateFailure(null);
            }
        });
    }
}
