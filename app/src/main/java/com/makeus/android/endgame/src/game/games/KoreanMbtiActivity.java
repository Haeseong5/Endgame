package com.makeus.android.endgame.src.game.games;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.interfaces.GameActivityView;
import com.makeus.android.endgame.src.game.result.ResultKmbtiActivity;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;

public class KoreanMbtiActivity extends BaseActivity implements GameActivityView {
    private RadioButton mRbZZam, mRbZZa, mRbZZic, mRbBoo, mRbMil, mRbSSal, mRbMul, mRbBi;
    private RadioGroup mRgZZamZZa, mRgZZicBoo, mRgMillSSal, mRgMulBi;
    private Toolbar mToolbar;
    private String mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_korean_mbti);
        initView();
        setGameToolbar(mToolbar, getString(R.string.korea_mbti_game_title), R.color.colorYellowKmbti);
    }

    void initView() {
        mToolbar = findViewById(R.id.game_toolbar);
        mRbZZa = findViewById(R.id.kmbti_zza);
        mRbZZam = findViewById(R.id.kmbti_zzam);
        mRbZZic = findViewById(R.id.kmbti_zzic);
        mRbBoo = findViewById(R.id.kmbti_boo);
        mRbSSal = findViewById(R.id.kmbti_ssal);
        mRbMil = findViewById(R.id.kmbti_mil);
        mRbMul = findViewById(R.id.kmbti_mul);
        mRbBi = findViewById(R.id.kmbti_be);

        mRbZZa.setTag("짜"); //짜장
        mRbZZam.setTag("짬"); //짬뽕
        mRbZZic.setTag("찍"); //찍먹
        mRbBoo.setTag("부"); //부먹
        mRbSSal.setTag("쌀"); //쌀떡
        mRbMil.setTag("밀"); //밀떡
        mRbMul.setTag("물"); //물냉
        mRbBi.setTag("비"); ///비냉

        mRgMulBi = findViewById(R.id.radio_group_mulbi);
        mRgMillSSal = findViewById(R.id.radio_group_ssalmil);
        mRgZZamZZa = findViewById(R.id.radio_group_zzamzza);
        mRgZZicBoo = findViewById(R.id.radio_group_zzicbu);
    }

    public void parseResult() {
        mResult = "";
        mResult += getSelectedItem((RadioButton) findViewById(mRgZZamZZa.getCheckedRadioButtonId()));
        mResult += getSelectedItem((RadioButton) findViewById(mRgZZicBoo.getCheckedRadioButtonId()));
        mResult += getSelectedItem((RadioButton) findViewById(mRgMulBi.getCheckedRadioButtonId()));
        mResult += getSelectedItem((RadioButton) findViewById(mRgMillSSal.getCheckedRadioButtonId()));

        if (mResult.length() == 4) {
//            printToast(result);
            tryPostResult(mResult);
        } else {
            printToast(getString(R.string.notify_no_selected));
        }
    }

    public String getSelectedItem(RadioButton radioButton) {
        String word;
        if (radioButton != null) {
            word = radioButton.getTag().toString();
        } else {
            word = "";
        }
        return word;
    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.kmbti_finish_button:
                parseResult();
                stopTimerTask(null);
                break;
        }
    }

    public void tryPostResult(String choice) {
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(KMBTI_GAME_INDEX, choice);
    }


    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess) {
            Intent resultIntent = new Intent(KoreanMbtiActivity.this, ResultKmbtiActivity.class);
            resultIntent.putExtra("record", recordResult);
            startActivity(resultIntent);
            finish();
        } else {
            showCustomToast("결과저장 실패: " + message);
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);

    }
}
