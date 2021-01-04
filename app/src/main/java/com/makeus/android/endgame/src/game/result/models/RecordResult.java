package com.makeus.android.endgame.src.game.result.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class RecordResult implements Parcelable {
    @SerializedName("gameIdx")
    private int gameIdx;

    @SerializedName("gameName")
    private String gameName;

    @SerializedName("choice")
    private String choice;

    @SerializedName("ratio")
    private String ratio;

    public RecordResult(){}
    public RecordResult(int gameIdx, String gameName, String choice, String ratio) {
        this.gameIdx = gameIdx;
        this.gameName = gameName;
        this.choice = choice;
        this.ratio = ratio;
    }

    protected RecordResult(Parcel in) {
        gameIdx = in.readInt();
        gameName = in.readString();
        choice = in.readString();
        ratio = in.readString();
    }

    public static final Creator<RecordResult> CREATOR = new Creator<RecordResult>() {
        @Override
        public RecordResult createFromParcel(Parcel in) {
            return new RecordResult(in);
        }

        @Override
        public RecordResult[] newArray(int size) {
            return new RecordResult[size];
        }
    };

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getGameIdx() {
        return gameIdx;
    }

    public void setGameIdx(int gameIdx) {
        this.gameIdx = gameIdx;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gameIdx);
        dest.writeString(gameName);
        dest.writeString(choice);
        dest.writeString(ratio);
    }

    @Override
    public String toString() {
        return "RecordResult{" +
                "gameIdx=" + gameIdx +
                ", gameName='" + gameName + '\'' +
                ", choice='" + choice + '\'' +
                ", ratio='" + ratio + '\'' +
                '}';
    }
}
