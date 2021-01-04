package com.makeus.android.endgame.src.game.games;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;

public class ValuesActivity extends BaseActivity implements GameActivityView {
    private CheckBox mCbHonor, mCbMoney, mCbFamily, mCbFriend;
    private TextView mTvHonorNum, mTvMoneyNum, mTvFamilyNum, mTvFriendNum;
    private ArrayList<String> resultList; //사용자의 선택 결과를 저장할 리스트
    private ImageView mIvFinishButton;
    StringBuilder mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_values);
        resultList = new ArrayList<>();
        initView();
        setGameToolbar(mBaseToolbar, getString(R.string.value_game_title), R.color.colorBlueValues);

    }

    void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar);
        mCbHonor = findViewById(R.id.values_cb_honors);
        mCbFamily = findViewById(R.id.values_cb_family);
        mCbMoney = findViewById(R.id.values_cb_money);
        mCbFriend = findViewById(R.id.values_cb_friend);
        mTvHonorNum = findViewById(R.id.values_tv_honors);
        mTvFamilyNum = findViewById(R.id.values_tv_family);
        mTvMoneyNum = findViewById(R.id.values_tv_money);
        mTvFriendNum = findViewById(R.id.values_tv_friend);
        mIvFinishButton = findViewById(R.id.values_finish_button);
        mCbHonor.setTag("명"); //명예
        mCbFriend.setTag("친"); //기족
        mCbMoney.setTag("돈"); //돈
        mCbFamily.setTag("가"); //친구

        mIvFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = resultList.size();
                if (result != 4) {
                    printToast(getString(R.string.notify_no_selected));
                } else {
                    //request server
                    mResult = new StringBuilder();
                    for (int i = 0; i < resultList.size(); i++) {
                        mResult.append(resultList.get(i));
                    }
                    stopTimerTask(null);
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
            drawable.setColorFilter(Color.parseColor("#1f5dca"), PorterDuff.Mode.SRC_IN);
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
            case R.id.values_cb_honors:
                changeView(mCbHonor, mCbFamily, mCbMoney, mCbFriend, mTvHonorNum, mTvFamilyNum, mTvMoneyNum, mTvFriendNum);
                break;
            case R.id.values_cb_family:
                changeView(mCbFamily, mCbHonor, mCbMoney, mCbFriend, mTvFamilyNum, mTvHonorNum, mTvMoneyNum, mTvFriendNum);
                break;
            case R.id.values_cb_money:
                changeView(mCbMoney, mCbHonor, mCbFamily, mCbFriend, mTvMoneyNum, mTvHonorNum, mTvFamilyNum, mTvFriendNum);
                break;
            case R.id.values_cb_friend:
                changeView(mCbFriend, mCbHonor, mCbMoney, mCbFamily, mTvFriendNum, mTvHonorNum, mTvMoneyNum, mTvFamilyNum);
                break;
        }
    }

    public void tryPostResult(String choice) {
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(VALUES_GAME_INDEX, choice);
    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess && mResult.length() == 4) {
            recordResult.setGameIdx(VALUES_GAME_INDEX);
            Intent resultIntent = new Intent(ValuesActivity.this, ResultFourActivity.class);
            resultIntent.putExtra("record", recordResult);
            resultIntent.putExtra("activity", "value");
            startActivity(resultIntent);
            finish();
        } else {
            showCustomToast(getString(R.string.network_error));
            finish();
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
    }
}
