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
import com.makeus.android.endgame.src.game.result.ResultRamenActivity;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.RAMEN_GAME_INDEX;

public class RamenActivity extends BaseActivity implements GameActivityView {

    public static final String REQUEST_RESULT_CODE = "result";
    private ArrayList<String> resultList; //사용자의 선택 결과를 저장할 리스트
    private ImageView mIvFinishButton;
    private CheckBox mCbSoup, mCbFlake, mCbNoodle;
    private TextView mTvSoupNum, mTvFlakeNum, mTvNoodle;
    private StringBuilder mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ramen);
        initView();
        setGameToolbar(mBaseToolbar, getString(R.string.ramen_game_title), R.color.colorRedRamen);
        resultList = new ArrayList<>();
    }

    void initView(){
        mBaseToolbar = findViewById(R.id.game_toolbar);
        mIvFinishButton = findViewById(R.id.ramen_iv_finish_button);
        mCbSoup = findViewById(R.id.ramen_cb_soup);
        mCbFlake = findViewById(R.id.ramen_cb_flake);
        mCbNoodle = findViewById(R.id.ramen_cb_noodle);
        mTvFlakeNum = findViewById(R.id.ramen_tv_flake);
        mTvSoupNum = findViewById(R.id.ramen_tv_soup);
        mTvNoodle = findViewById(R.id.ramen_tv_noodle);

        mCbSoup.setTag("스"); //스프
        mCbFlake.setTag("후"); //후레이크
        mCbNoodle.setTag("면"); //면


        mCbSoup.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(mCbSoup, mCbFlake, mCbNoodle, mTvSoupNum, mTvFlakeNum, mTvNoodle);
            }
        });
        mCbFlake.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(mCbFlake, mCbSoup, mCbNoodle, mTvFlakeNum, mTvSoupNum, mTvNoodle);
            }
        });
        mCbNoodle.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeView(mCbNoodle, mCbSoup, mCbFlake, mTvNoodle, mTvSoupNum, mTvFlakeNum);

            }
        });

        mIvFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = resultList.size();
                if (result != 3 ){
                    printToast(getString(R.string.notify_no_selected));
                }else{
                    mResult = new StringBuilder();
                    stopTimerTask(null);
                    for(int i=0; i<resultList.size(); i++){
                        mResult.append(resultList.get(i));
                    }
                    if (mResult.length() == 3){
                        tryPostResult(mResult.toString());
                     }
                }
            }
        });
    }

    private void changeView(CheckBox cbPivot, CheckBox cb1, CheckBox cb2,
                            TextView tvPivot, TextView tv1, TextView tv2){
        if (cbPivot.isChecked()) {
            resultList.add(cbPivot.getTag().toString());
            Drawable drawable = getResources().getDrawable(R.drawable.num_selected_background);
            drawable.setColorFilter(Color.parseColor("#e61f3d"), PorterDuff.Mode.SRC_IN);
            tvPivot.setBackground(drawable);
            tvPivot.setText(String.valueOf(resultList.indexOf(cbPivot.getTag().toString())+1));
        }else{ //선택 해제하면 (false)
            resultList.remove(cbPivot.getTag().toString()); //리스트에서 클릭한 아이템을 제거한다.

            tvPivot.setBackgroundResource(R.drawable.num_default_background); //디폴트 이미지로 변경한다.
            tvPivot.setText(""); //숫자 없애기.
            //나머지 뷰 숫자 변경
            tv1.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv2.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv1.setText(String.valueOf(resultList.indexOf(cb1.getTag().toString())+1));
            tv2.setText(String.valueOf(resultList.indexOf(cb2.getTag().toString())+1));
        }
    }
    public void tryPostResult(String choice){
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(RAMEN_GAME_INDEX, choice);
    }
    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess) {
            recordResult.setGameIdx(RAMEN_GAME_INDEX);
            Intent resultIntent = new Intent(RamenActivity.this, ResultRamenActivity.class);
            resultIntent.putExtra("record", recordResult);
            resultIntent.putExtra(REQUEST_RESULT_CODE, mResult.toString());
            startActivity(resultIntent);
            finish();
        }else{
            showCustomToast("결과저장 실패: "+message);
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
    }
}
