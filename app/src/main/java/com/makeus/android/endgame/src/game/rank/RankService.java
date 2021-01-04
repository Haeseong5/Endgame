package com.makeus.android.endgame.src.game.rank;

import com.makeus.android.endgame.src.game.rank.interfaces.RankActivityView;
import com.makeus.android.endgame.src.game.rank.interfaces.RankRetrofitInterface;
import com.makeus.android.endgame.src.game.rank.models.RankResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class RankService {
    private final RankActivityView mRankActivityView;

    public RankService(final RankActivityView rankActivityView) {
        this.mRankActivityView = rankActivityView;
    }

    public void getRankList(int gameIdx) {
        final RankRetrofitInterface rankRetrofitInterface = getRetrofit().create(RankRetrofitInterface.class);
        rankRetrofitInterface.getRankList(gameIdx).enqueue(new Callback<RankResponse>() {
            @Override
            public void onResponse(Call<RankResponse> call, Response<RankResponse> response) {
                final RankResponse rankResponse = response.body();
                if (rankResponse == null) {
                    mRankActivityView.validateFailure(null);
                    return;
                }

                mRankActivityView.validateGetRankListSuccess(rankResponse.getSuccess(), rankResponse.getCode(), rankResponse.getMessage(), rankResponse.getResult());
            }

            @Override
            public void onFailure(Call<RankResponse> call, Throwable t) {
                mRankActivityView.validateFailure(null);
            }
        });
    }
}
