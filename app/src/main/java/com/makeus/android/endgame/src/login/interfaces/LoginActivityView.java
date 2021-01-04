package com.makeus.android.endgame.src.login.interfaces;

public interface LoginActivityView {

    void validateSuccess(boolean isSuccess, int code, String message);

    void validateFailure(String message);
}
