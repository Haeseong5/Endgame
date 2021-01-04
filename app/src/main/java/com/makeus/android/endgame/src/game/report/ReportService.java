package com.makeus.android.endgame.src.game.report;

import com.makeus.android.endgame.src.game.report.interfaces.ReportActivityView;
import com.makeus.android.endgame.src.game.report.interfaces.ReportRetrofitInterface;
import com.makeus.android.endgame.src.game.report.models.ReportResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class ReportService {
    private final ReportActivityView mReportActivityView;

    ReportService(final ReportActivityView reportActivityView) {
        this.mReportActivityView = reportActivityView;
    }

    void postReport(String kakaoId, String gameName, String gameDesc) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("kakaoId", kakaoId);
        hashMap.put("gameName", gameName);
        hashMap.put("gameDesc", gameDesc);

        final ReportRetrofitInterface reportRetrofitInterface = getRetrofit().create(ReportRetrofitInterface.class);
        reportRetrofitInterface.postReport(hashMap).enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                final ReportResponse reportResponse = response.body();
                if (reportResponse == null) {
                    mReportActivityView.validateFailure(null);
                    return;
                }

                mReportActivityView.validateSuccess(reportResponse.getIsSuccess(), reportResponse.getCode(), reportResponse.getMessage());
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t) {
                mReportActivityView.validateFailure(null);
            }
        });
    }
}
