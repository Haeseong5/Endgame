package com.makeus.android.endgame.src.game.report.interfaces;

import com.makeus.android.endgame.src.game.report.models.ReportResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReportRetrofitInterface {
    @POST("/endgame/report")
    Call<ReportResponse> postReport(@Body HashMap<String, Object> hashMap);
}
