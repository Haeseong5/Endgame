package com.makeus.android.endgame.src.game.games;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.makeus.android.endgame.R;

public class CustomDialog extends Dialog {

    private TextView mPositiveButton, mNegativeButton, mTvTitle, mTvContents;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;
    private String mTitle, mContents;

    public CustomDialog(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener, String title, String contents) {
        super(context);
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;
        this.mTitle = title;
        this.mContents = contents;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_custom);
        setCancelable(false);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //셋팅
        mPositiveButton=findViewById(R.id.dialog_custom_tv_btn_ok);
        mNegativeButton=findViewById(R.id.dialog_custom_tv_btn_cancel);
        mTvTitle = findViewById(R.id.dialog_custom_tv_title);
        mTvContents = findViewById(R.id.dialog_custom_tv_contents);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(mPositiveListener);
        mNegativeButton.setOnClickListener(mNegativeListener);
        mTvTitle.setText(mTitle+"");
        mTvContents.setText(mContents+"");
    }
}
