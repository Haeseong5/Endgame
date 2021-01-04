package com.makeus.android.endgame.src.profile.interfaces;

import com.makeus.android.endgame.src.main.models.DefaultResponse;
import com.makeus.android.endgame.src.profile.models.ProfileResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface ProfileRetrofitInterface {
    @GET("/endgame/mypage")
    Call<ProfileResponse> getMyPage();

    @PATCH("/endgame/comment")
    Call<DefaultResponse> deleteComment(@Body HashMap<String, Object> hashMap);
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
