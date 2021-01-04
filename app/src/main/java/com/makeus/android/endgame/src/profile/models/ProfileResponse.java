package com.makeus.android.endgame.src.profile.models;

import com.google.gson.annotations.SerializedName;
import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

public class ProfileResponse {
    @SerializedName("isSuccess")
    private Boolean isSuccess;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private Profile result;

    public class Profile{
        @SerializedName("userInfo")
        private UserInfo userInfo;

        @SerializedName("records")
        private ArrayList<RecordResult> records;

        @SerializedName("comments")
        private ArrayList<CommentResult> comments;

        public class UserInfo{
            @SerializedName("email")
            private String email;

            @SerializedName("nickname")
            private String nickname;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public ArrayList<RecordResult> getRecords() {
            return records;
        }

        public void setRecords(ArrayList<RecordResult> records) {
            this.records = records;
        }

        public ArrayList<CommentResult> getComments() {
            return comments;
        }

        public void setComments(ArrayList<CommentResult> comments) {
            this.comments = comments;
        }
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public Profile getResult() {
        return result;
    }

    public void setResult(Profile result) {
        this.result = result;
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

}
