package com.makeus.android.endgame.src.game.result.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecordResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<RecordResult> result;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
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

    public ArrayList<RecordResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<RecordResult> result) {
        this.result = result;
    }
}
