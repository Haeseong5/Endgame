package com.makeus.android.endgame.src.game.rank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.what_color_game.ResultColorActivity;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.RAMEN_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;

public class RankChoiceActivity extends BaseActivity {
    public ArrayList<ColorResult> mColorDataList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_choice);
        initColorData();

    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.rank_choice_bt_kmbti:
                updateUI(KMBTI_GAME_INDEX, getString(R.string.korea_mbti_game_title));
                break;
            case R.id.rank_choice_bt_fish:
                updateUI(FISH_GAME_INDEX, getString(R.string.fish_game_title));
                break;
            case R.id.rank_choice_bt_wash:
                updateUI(WASH_GAME_INDEX, getString(R.string.wash_game_title));
                break;
            case R.id.rank_choice_bt_ramen:
                updateUI(RAMEN_GAME_INDEX, getString(R.string.ramen_game_title));
                break;
            case R.id.rank_choice_bt_value:
                updateUI(VALUES_GAME_INDEX, getString(R.string.value_game_title));
                break;
            case R.id.rank_choice_bt_color_blue_white:
                updateUI_ColorResult(mColorDataList.get(0));
                break;
            case R.id.rank_choice_bt_color_bluewhite_greengold:
                updateUI_ColorResult(mColorDataList.get(1));
                break;
            case R.id.rank_choice_bt_color_grey_yellow:
                updateUI_ColorResult(mColorDataList.get(2));
                break;
            case R.id.rank_choice_bt_color_mg_pw_ver1:
                updateUI_ColorResult(mColorDataList.get(3));
                break;
            case R.id.rank_choice_bt_color_mg_pw_ver2:
                updateUI_ColorResult(mColorDataList.get(4));
                break;
            case R.id.rank_choice_bt_color_whiteggold_blueblack:
                updateUI_ColorResult(mColorDataList.get(5));
                break;
            case R.id.rank_choice_btn_close :
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.slide_up);
                break;
            default:
                break;
        }
    }

    private void updateUI(int gameIdx, String title) {
        Intent rankIntent = new Intent(RankChoiceActivity.this, RankActivity.class);
        rankIntent.putExtra("gameIdx", gameIdx);
        rankIntent.putExtra("title", title);
        startActivity(rankIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.slide_up);

    }

    private void updateUI_ColorResult(ColorResult colorResult) {
        Intent resultIntent = new Intent(RankChoiceActivity.this, ResultColorActivity.class);
//        resultIntent.putExtra("recordResult", recordResult);
        resultIntent.putExtra("colorResult", colorResult);
        startActivity(resultIntent);
        overridePendingTransition(R.anim.fade_in, R.anim.slide_up);
    }

    void initColorData() {
        mColorDataList = new ArrayList<>();
        mColorDataList.add(new ColorResult(9, R.drawable.btn_thumbnail_bl_vs_wh, "검정색", "흰색", R.drawable.img_bl_vs_wh));
        mColorDataList.add(new ColorResult(10, R.drawable.btn_thumbnail_bw_vs_gg, "파흰", "초금", R.drawable.img_bw_vs_gg));
        mColorDataList.add(new ColorResult(11, R.drawable.btn_thumbnail_gr_vs_ye, "회색", "노란색", R.drawable.img_gr_vs_ye));
        mColorDataList.add(new ColorResult(12, R.drawable.btn_thumbnail_mg_vs_pw_1, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_1));
        mColorDataList.add(new ColorResult(13, R.drawable.btn_thumbnail_mg_vs_pw_2, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_2));
        mColorDataList.add(new ColorResult(14, R.drawable.btn_thumbnail_wg_vs_bb, "흰금", "파검", R.drawable.img_wg_vs_bb));
    }
}
