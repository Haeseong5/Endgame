package com.makeus.android.endgame.src.game.rank.interfaces;

import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

public interface RankActivityView {

    void validateGetRankListSuccess(boolean isSuccess, int code, String message, ArrayList<RecordResult> recordResults);

    void validateSetChoiceSuccess(int position);

    void validateFailure(String message);
}
