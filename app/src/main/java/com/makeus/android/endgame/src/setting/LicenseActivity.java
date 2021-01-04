package com.makeus.android.endgame.src.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;

public class LicenseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        mBaseToolbar = findViewById(R.id.main_toolbar);
        setMainToolbar(mBaseToolbar,true);
        mBaseToolbar.setTitle("Open source licenses");
        ImageView ivToolbarLogo = findViewById(R.id.toolbar_main_iv_logo);
        ivToolbarLogo.setVisibility(View.GONE);

    }
}
