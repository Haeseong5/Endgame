package com.makeus.android.endgame.src.game.what_color_game.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ColorResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<ColorResult> result = null;

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

    public ArrayList<ColorResult> getResult() {
        return result;
    }

    public void setResult(ArrayList<ColorResult> result) {
        this.result = result;
    }
}