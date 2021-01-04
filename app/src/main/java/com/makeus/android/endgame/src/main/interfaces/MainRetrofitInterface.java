package com.makeus.android.endgame.src.main.interfaces;

import com.makeus.android.endgame.src.main.models.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MainRetrofitInterface {
    @GET("/test")
    Call<DefaultResponse> getTest();
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
