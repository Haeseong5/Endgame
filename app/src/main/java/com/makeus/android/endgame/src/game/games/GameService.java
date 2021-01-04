package com.makeus.android.endgame.src.game.games;


import android.util.Log;

import com.makeus.android.endgame.src.game.games.interfaces.GameActivityView;
import com.makeus.android.endgame.src.game.games.interfaces.GameRetrofitInterface;

import com.makeus.android.endgame.src.game.games.models.GameResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class GameService {
    private final GameActivityView mGameActivityView;

    public GameService(final GameActivityView gameActivityView) {
        this.mGameActivityView = gameActivityView;
    }

    public void postResult(int gameIdx, String choice) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("gameIdx", gameIdx);
        hashMap.put("choice", choice);

        final GameRetrofitInterface gameRetrofitInterface = getRetrofit().create(GameRetrofitInterface.class);
        gameRetrofitInterface.postResult(hashMap).enqueue(new Callback<GameResponse>() {
            @Override
            public void onResponse(Call<GameResponse> call, Response<GameResponse> response) {
                final GameResponse gameResponse = response.body(); //응답객체
                if (gameResponse == null) {
                    mGameActivityView.validateFailure(null);
                    return;
                }
                mGameActivityView.validateSuccess(gameResponse.isSuccess(), gameResponse.getCode(), gameResponse.getMessage(), gameResponse.getResult());
            }

            @Override
            public void onFailure(Call<GameResponse> call, Throwable t) {
                mGameActivityView.validateFailure("result fail");
            }
        });
    }

}
