package com.makeus.android.endgame.src.game.result.interfaces;

import com.makeus.android.endgame.src.game.result.models.CommentResponse;
import com.makeus.android.endgame.src.game.result.models.DefaultResponse;
import com.makeus.android.endgame.src.game.result.models.RecordResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ResultRetrofitInterface {
//    @POST("/endgame/game/result")
//    Call<RecordResponse> postGame(
//            @Body HashMap<String, Object> hashMap
//    );

    @GET("/endgame/comment")
    Call<CommentResponse> getComments(@Query("gameIdx") int gameIdx);

    @POST("/endgame/comment")
    Call<DefaultResponse> postComment(
            @Body HashMap<String, Object> hashMap
    );

    @POST("/endgame/comment/like")
    Call<DefaultResponse> likeComment(
            @Body HashMap<String, Object> hashMap
    );
//
//    @GET("/jwt")
//    Call<ColorResponse> getTest();
//
//    @GET("/test/{number}")
//    Call<ColorResponse> getTestPathAndQuery(
//            @Path("number") int number,
//            @Query("content") final String content
//    );
//
//    @POST("/test")
//    Call<ColorResponse> postTest(@Body RequestBody params);
}
