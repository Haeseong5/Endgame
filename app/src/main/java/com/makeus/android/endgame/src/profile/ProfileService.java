package com.makeus.android.endgame.src.profile;


import com.makeus.android.endgame.src.main.models.DefaultResponse;
import com.makeus.android.endgame.src.profile.interfaces.ProfileActivityView;
import com.makeus.android.endgame.src.profile.interfaces.ProfileRetrofitInterface;
import com.makeus.android.endgame.src.profile.models.ProfileResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class ProfileService {
    private final ProfileActivityView mProfileActivityView;

    public ProfileService(final ProfileActivityView mainActivityView) {
        this.mProfileActivityView = mainActivityView;
    }

    void getMyPage() {
        final ProfileRetrofitInterface profileRetrofitInterface = getRetrofit().create(ProfileRetrofitInterface.class);
        profileRetrofitInterface.getMyPage().enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                final ProfileResponse profileResponse = response.body();
                if (profileResponse == null) {
                    mProfileActivityView.validateFailure(null);
                    return;
                }

                mProfileActivityView.validateSuccess(profileResponse.getSuccess(), profileResponse.getCode(), profileResponse.getMessage(), profileResponse.getResult());
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mProfileActivityView.validateFailure(null);
            }
        });
    }

    public void deleteComment(final int commentIdx) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commentIdx", commentIdx);

        final ProfileRetrofitInterface profileRetrofitInterface = getRetrofit().create(ProfileRetrofitInterface.class);
        profileRetrofitInterface.deleteComment(hashMap).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                final DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mProfileActivityView.validateFailure(null);
                    return;
                }

                mProfileActivityView.validateSuccessDeleteComment(defaultResponse.getIsSuccess(), defaultResponse.getCode(), defaultResponse.getMessage(), commentIdx);
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mProfileActivityView.validateFailure(null);
            }
        });
    }
}
