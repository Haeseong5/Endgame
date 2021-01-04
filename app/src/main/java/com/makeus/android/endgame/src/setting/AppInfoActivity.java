package com.makeus.android.endgame.src.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;

public class AppInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        mBaseToolbar = findViewById(R.id.main_toolbar);
        setMainToolbar(mBaseToolbar,true);
        mBaseToolbar.setTitle("앱 설명");
        ImageView ivToolbarLogo = findViewById(R.id.toolbar_main_iv_logo);


        ivToolbarLogo.setVisibility(View.GONE);

//        TextView tvToolbarTitle = findViewById(R.id.toolbar_main_tv_title);
//        tvToolbarTitle.setText("앱 설명");
//        tvToolbarTitle.setVisibility(View.VISIBLE);
    }
}
