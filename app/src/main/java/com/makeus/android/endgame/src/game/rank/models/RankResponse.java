package com.makeus.android.endgame.src.game.rank.models;

import com.google.gson.annotations.SerializedName;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

public class RankResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<RecordResult> result;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
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

    public ArrayList<RecordResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<RecordResult> result) {
        this.result = result;
    }
}
