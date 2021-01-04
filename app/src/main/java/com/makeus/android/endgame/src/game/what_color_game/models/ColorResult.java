package com.makeus.android.endgame.src.game.what_color_game.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ColorResult implements Parcelable {
//    @SerializedName("thumbnailImage")
    private int gameIdx;
    private int thumbnailImage;
    private int image;
//    @SerializedName("color1")
    private String color1;
//    @SerializedName("color2")
    private String color2;
    private int select;

    public ColorResult(){}

//    public ColorResult(int gameIdx, int thumbnailImage) {
//        this.thumbnailImage = thumbnailImage;
//    }

    public ColorResult(int gameIdx, int thumbnailImage, String color1, String color2, int image, int select) {
        this.gameIdx = gameIdx;
        this.thumbnailImage = thumbnailImage;
        this.color1 = color1;
        this.color2 = color2;
        this.image = image;
        this.select = select;
    }

    public ColorResult(int gameIdx, int thumbnailImage, String color1, String color2, int image) {
        this.gameIdx = gameIdx;
        this.thumbnailImage = thumbnailImage;
        this.image = image;
        this.color1 = color1;
        this.color2 = color2;
    }

    protected ColorResult(Parcel in) {
        gameIdx = in.readInt();
        thumbnailImage = in.readInt();
        image = in.readInt();
        color1 = in.readString();
        color2 = in.readString();
        select = in.readInt();
    }

    public static final Creator<ColorResult> CREATOR = new Creator<ColorResult>() {
        @Override
        public ColorResult createFromParcel(Parcel in) {
            return new ColorResult(in);
        }

        @Override
        public ColorResult[] newArray(int size) {
            return new ColorResult[size];
        }
    };

    public int getGameIdx() {
        return gameIdx;
    }

    public void setGameIdx(int gameIdx) {
        this.gameIdx = gameIdx;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public int getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(int thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gameIdx);
        dest.writeInt(thumbnailImage);
        dest.writeInt(image);
        dest.writeString(color1);
        dest.writeString(color2);
        dest.writeInt(select);
    }
}
