package com.makeus.android.endgame.src.profile.interfaces;

import com.makeus.android.endgame.src.profile.models.ProfileResponse;

public interface ProfileActivityView {

    void validateSuccess(boolean isSuccess, int code, String text, ProfileResponse.Profile profile);

    void validateSuccessDeleteComment(boolean isSuccess, int code, String text, int commentIdx);

    void validateFailure(String message);
}
