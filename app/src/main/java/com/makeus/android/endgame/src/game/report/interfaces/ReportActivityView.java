package com.makeus.android.endgame.src.game.report.interfaces;

public interface ReportActivityView {

    void validateSuccess(boolean isSuccess, int code, String message);

    void validateFailure(String message);
}
