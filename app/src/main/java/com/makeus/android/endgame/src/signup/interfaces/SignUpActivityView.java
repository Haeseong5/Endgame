package com.makeus.android.endgame.src.signup.interfaces;

public interface SignUpActivityView {
    void validateSuccess(boolean isSuccess, String message, int code);

    void validateFailure(String message);
}
