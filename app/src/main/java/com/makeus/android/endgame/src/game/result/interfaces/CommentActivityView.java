package com.makeus.android.endgame.src.game.result.interfaces;

import com.makeus.android.endgame.src.game.result.models.CommentResult;

import java.util.ArrayList;

public interface CommentActivityView {
    void validateSuccessGetComments(boolean isSuccess, int code, String message, ArrayList<CommentResult> comments);

    void validateSuccessPostComment(boolean isSuccess, int code, String message);

    void validateSuccessLikeComment(boolean isSuccess, int code, String message);

    void validateFailure(String message);
}
