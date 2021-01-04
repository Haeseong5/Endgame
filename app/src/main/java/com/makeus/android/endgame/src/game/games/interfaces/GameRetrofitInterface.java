package com.makeus.android.endgame.src.game.games.interfaces;

import com.makeus.android.endgame.src.game.games.models.GameResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GameRetrofitInterface {
//    @GET("/banner")
//    Call<DefaultResponse> getTest();
//
//    @GET("/jwt")
//    Call<SplashResponse> getValidatedJwt();
//
//    @GET("/test/{number}")
//    Call<SplashResponse> getTestPathAndQuery(
//            @Path("number") int number,
//            @Query("content") final String content
//    );
//
    @POST("/endgame/game/result")
    Call<GameResponse> postResult(@Body HashMap<String, Object> hashMap);
}
