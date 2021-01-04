package com.makeus.android.endgame.src.game.result.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CommentResult implements Parcelable {
    @SerializedName("commentIdx")
    int commentIdx;
    @SerializedName("gameIdx")
    int gameIdx;
    @SerializedName("gameName")
    String gameName;
    @SerializedName("content")
    String content;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("countLike")
    int countLike;
    @SerializedName("alreadyLike")
    int alreadyLike;


    public CommentResult(){}
    protected CommentResult(Parcel in) {
        commentIdx = in.readInt();
        gameIdx = in.readInt();
        gameName = in.readString();
        content = in.readString();
        createdAt = in.readString();
        nickname = in.readString();
        countLike = in.readInt();
        alreadyLike = in.readInt();
    }

    public static final Creator<CommentResult> CREATOR = new Creator<CommentResult>() {
        @Override
        public CommentResult createFromParcel(Parcel in) {
            return new CommentResult(in);
        }

        @Override
        public CommentResult[] newArray(int size) {
            return new CommentResult[size];
        }
    };

    public int getCommentIdx() {
        return commentIdx;
    }

    public void setCommentIdx(int commentIdx) {
        this.commentIdx = commentIdx;
    }

    public int getGameIdx() {
        return gameIdx;
    }

    public void setGameIdx(int gameIdx) {
        this.gameIdx = gameIdx;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCountLike() {
        return countLike;
    }

    public void setCountLike(int countLike) {
        this.countLike = countLike;
    }

    public int getAlreadyLike() {
        return alreadyLike;
    }

    public void setAlreadyLike(int alreadyLike) {
        this.alreadyLike = alreadyLike;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(commentIdx);
        dest.writeInt(gameIdx);
        dest.writeString(gameName);
        dest.writeString(content);
        dest.writeString(createdAt);
        dest.writeString(nickname);
        dest.writeInt(countLike);
        dest.writeInt(alreadyLike);
    }
}
