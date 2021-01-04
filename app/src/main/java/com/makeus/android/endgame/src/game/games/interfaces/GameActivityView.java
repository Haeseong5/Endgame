package com.makeus.android.endgame.src.game.games.interfaces;

import com.makeus.android.endgame.src.game.result.models.RecordResult;

public interface GameActivityView {

    void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult);

    void validateFailure(String message);
}
