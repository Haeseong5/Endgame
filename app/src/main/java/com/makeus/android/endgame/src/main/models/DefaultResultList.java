package com.makeus.android.endgame.src.main.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResultList {
    @SerializedName("Image")
    String Image;
    @SerializedName("Text")
    String Text;
    @SerializedName("Sub")
    String Sub;

    public DefaultResultList(String image, String text, String sub) {
        Image = image;
        Text = text;
        Sub = sub;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getSub() {
        return Sub;
    }

    public void setSub(String sub) {
        Sub = sub;
    }
}
