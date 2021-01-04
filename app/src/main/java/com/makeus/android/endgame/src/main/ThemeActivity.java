package com.makeus.android.endgame.src.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.FishActivity;
import com.makeus.android.endgame.src.game.games.KoreanMbtiActivity;
import com.makeus.android.endgame.src.game.games.RamenActivity;
import com.makeus.android.endgame.src.game.games.ValuesActivity;
import com.makeus.android.endgame.src.game.games.WashActivity;
import com.makeus.android.endgame.src.game.report.ReportActivity;
import com.makeus.android.endgame.src.game.what_color_game.ColorActivity;

public class ThemeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        mBaseToolbar = findViewById(R.id.main_toolbar);
        setMainToolbar(mBaseToolbar, true);

    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.theme_korea_mbti:
                startActivity(new Intent(ThemeActivity.this, KoreanMbtiActivity.class));
                break;
            case R.id.theme_fishbread:
                startActivity(new Intent(ThemeActivity.this, FishActivity.class));
                break;
            case R.id.theme_wash:
                startActivity(new Intent(ThemeActivity.this, WashActivity.class));
                break;
            case R.id.theme_ramen:
                startActivity(new Intent(ThemeActivity.this, RamenActivity.class));
                break;
            case R.id.theme_values:
                startActivity(new Intent(ThemeActivity.this, ValuesActivity.class));
                break;
            case R.id.theme_color:
                startActivity(new Intent(ThemeActivity.this, ColorActivity.class));
                break;
            case R.id.theme_add:
                startActivity(new Intent(ThemeActivity.this, ReportActivity.class));
                break;
            default:
                break;
        }
    }
}
