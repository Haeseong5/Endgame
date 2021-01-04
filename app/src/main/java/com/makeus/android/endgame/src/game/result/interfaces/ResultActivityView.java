package com.makeus.android.endgame.src.game.result.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface ResultActivityView {

    void validateSuccessKakakolink(String message);

    void validateFailureKakakolink(String message);

    void validateSuccessSaveImage(String message, String path, SimpleDateFormat day, Date date);

    void validateFailureSaveImage(String message);

}
