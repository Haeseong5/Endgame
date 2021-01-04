package com.makeus.android.endgame.src.game.rank.interfaces;

import com.makeus.android.endgame.src.game.rank.models.RankResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RankRetrofitInterface {
    @GET("/endgame/game/result/{gameIdx}")
    Call<RankResponse> getRankList(
            @Path("gameIdx") int gameIdx
    );
}
