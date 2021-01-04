package com.makeus.android.endgame.src.game.games;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.interfaces.GameActivityView;
import com.makeus.android.endgame.src.game.result.ResultFourActivity;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;

public class WashActivity extends BaseActivity implements GameActivityView {
    private CheckBox cbFace, cbShower, cbHair, cbTooth;
    private TextView tvNumFace, tvNumShower, tvNumHair, tvNumTooth;
    private ArrayList<String> resultList; //사용자의 선택 결과를 저장할 리스트
    private ImageView btFinish;
    private StringBuilder mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_wash);
        resultList = new ArrayList<>();
        initView();
        setGameToolbar(mBaseToolbar, getString(R.string.wash_game_title), R.color.colorPurpleWash);
    }

    void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar);
        cbFace = findViewById(R.id.wash_face_cb);
        cbHair = findViewById(R.id.wash_hair_cb);
        cbShower = findViewById(R.id.wash_shower_cb);
        cbTooth = findViewById(R.id.wash_tooth_cb);
        tvNumFace = findViewById(R.id.wash_face_num);
        tvNumHair = findViewById(R.id.wash_hair_num);
        tvNumShower = findViewById(R.id.wash_shower_num);
        tvNumTooth = findViewById(R.id.wash_tooth_num);
        btFinish = findViewById(R.id.wash_finish_button);
        cbFace.setTag("세"); //세수
        cbTooth.setTag("양"); //양치
        cbShower.setTag("샤"); //샤워
        cbHair.setTag("머"); //머리


        btFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = resultList.size();
                if (result != 4) {
                    showCustomToast(getString(R.string.notify_no_selected));
                } else {
                    stopTimerTask(null);
                    mResult = new StringBuilder();
                    for (int i = 0; i < resultList.size(); i++) {
                        mResult.append(resultList.get(i));
                    }
                    tryPostResult(mResult.toString());
                }
            }
        });
    }

    private void changeView(CheckBox cbPivot, CheckBox cb1, CheckBox cb2, CheckBox cb3,
                            TextView tvPivot, TextView tv1, TextView tv2, TextView tv3) {
        if (cbPivot.isChecked()) {
            resultList.add(cbPivot.getTag().toString());
            Drawable drawable = getResources().getDrawable(R.drawable.num_selected_background);
            drawable.setColorFilter(Color.parseColor("#7639b6"), PorterDuff.Mode.SRC_IN);
            tvPivot.setBackground(drawable);
            tvPivot.setText(String.valueOf(resultList.indexOf(cbPivot.getTag().toString()) + 1));
        } else { //선택 해제하면 (false)
            resultList.remove(cbPivot.getTag().toString()); //리스트에서 클릭한 아이템을 제거한다.

            tvPivot.setBackgroundResource(R.drawable.num_default_background); //디폴트 이미지로 변경한다.
            tvPivot.setText(""); //숫자 없애기.
            //나머지 뷰 숫자 변경
            tv1.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv2.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv3.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv1.setText(String.valueOf(resultList.indexOf(cb1.getTag().toString()) + 1));
            tv2.setText(String.valueOf(resultList.indexOf(cb2.getTag().toString()) + 1));
            tv3.setText(String.valueOf(resultList.indexOf(cb3.getTag().toString()) + 1));
        }
    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.wash_face_cb:
                changeView(cbFace, cbHair, cbShower, cbTooth, tvNumFace, tvNumHair, tvNumShower, tvNumTooth);
                break;
            case R.id.wash_hair_cb:
                changeView(cbHair, cbFace, cbShower, cbTooth, tvNumHair, tvNumFace, tvNumShower, tvNumTooth);
                break;
            case R.id.wash_shower_cb:
                changeView(cbShower, cbFace, cbHair, cbTooth, tvNumShower, tvNumFace, tvNumHair, tvNumTooth);
                break;
            case R.id.wash_tooth_cb:
                changeView(cbTooth, cbFace, cbShower, cbHair, tvNumTooth, tvNumFace, tvNumShower, tvNumHair);
        }
    }

    public void tryPostResult(String choice) {
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(WASH_GAME_INDEX, choice);
    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess && mResult.length() == 4) {
            recordResult.setGameIdx(WASH_GAME_INDEX);
            Intent resultIntent = new Intent(WashActivity.this, ResultFourActivity.class);
            resultIntent.putExtra("record", recordResult);
            resultIntent.putExtra("activity", "wash");
            startActivity(resultIntent);
            finish();
        } else {
            showCustomToast("결과저장 실패: " + message);
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
    }
}
