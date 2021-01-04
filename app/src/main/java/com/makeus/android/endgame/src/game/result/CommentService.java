package com.makeus.android.endgame.src.game.result;

import com.makeus.android.endgame.src.game.result.interfaces.CommentActivityView;
import com.makeus.android.endgame.src.game.result.interfaces.ResultRetrofitInterface;
import com.makeus.android.endgame.src.game.result.models.CommentResponse;
import com.makeus.android.endgame.src.game.result.models.DefaultResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class CommentService {
    private final CommentActivityView mCommentActivityView;

    public CommentService(final CommentActivityView commentActivityView) {
        this.mCommentActivityView = commentActivityView;
    }

    public void getComments(int gameIdx) {
        final ResultRetrofitInterface mainRetrofitInterface = getRetrofit().create(ResultRetrofitInterface.class);
        mainRetrofitInterface.getComments(gameIdx).enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                final CommentResponse commentResponse = response.body();
                if (commentResponse == null) {
                    mCommentActivityView.validateFailure(null);
                    return;
                }

                mCommentActivityView.validateSuccessGetComments(commentResponse.getIsSuccess(), commentResponse.getCode(), commentResponse.getMessage(), commentResponse.getResult());
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                mCommentActivityView.validateFailure(null);
            }
        });
    }

    public void postComment(int gameIdx, final String content) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("gameIdx", gameIdx);
        hashMap.put("content", content);

        final ResultRetrofitInterface mainRetrofitInterface = getRetrofit().create(ResultRetrofitInterface.class);
        mainRetrofitInterface.postComment(hashMap).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                final DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mCommentActivityView.validateFailure(null);
                    return;
                }

                mCommentActivityView.validateSuccessPostComment(defaultResponse.getIsSuccess(), defaultResponse.getCode(), defaultResponse.getMessage());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mCommentActivityView.validateFailure(null);
            }
        });
    }

    public void likeComment(int commentIdx) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commentIdx", commentIdx);

        final ResultRetrofitInterface mainRetrofitInterface = getRetrofit().create(ResultRetrofitInterface.class);
        mainRetrofitInterface.likeComment(hashMap).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                final DefaultResponse defaultResponse = response.body();
                if (defaultResponse == null) {
                    mCommentActivityView.validateFailure(null);
                    return;
                }

                mCommentActivityView.validateSuccessLikeComment(defaultResponse.getIsSuccess(), defaultResponse.getCode(), defaultResponse.getMessage());
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                mCommentActivityView.validateFailure(null);
            }
        });
    }

}
