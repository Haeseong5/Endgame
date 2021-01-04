package com.makeus.android.endgame.src.game.games.models;

import com.google.gson.annotations.SerializedName;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

public class GameResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private RecordResult result;

    public Boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RecordResult getResult() {
        return result;
    }

    public void setResult(RecordResult result) {
        this.result = result;
    }
}
