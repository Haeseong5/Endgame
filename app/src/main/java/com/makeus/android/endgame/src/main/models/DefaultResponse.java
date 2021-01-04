package com.makeus.android.endgame.src.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DefaultResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private ArrayList<DefaultResultList> result = null;

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

    public ArrayList<DefaultResultList> getResult() {
        return result;
    }

    public void setResult(ArrayList<DefaultResultList> result) {
        this.result = result;
    }
}