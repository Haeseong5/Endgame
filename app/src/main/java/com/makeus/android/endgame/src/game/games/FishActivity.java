package com.makeus.android.endgame.src.game.games;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.interfaces.GameActivityView;
import com.makeus.android.endgame.src.game.result.ResultFishActivity;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;

public class FishActivity extends BaseActivity implements GameActivityView {
    private ArrayList<String> mResultList; //사용자의 선택 결과를 저장할 리스트
    private ImageView mIvFinishButton;
    private CheckBox mCbHead, mCbBody, mCbTail;
    private TextView mTvHeadNum, mTvBodyNum, mTvTailNum;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_fish);
        initView();
        setGameToolbar(mToolbar, getString(R.string.fish_game_title), R.color.colorGreenFish);

        mResultList = new ArrayList<>();

    }

    void initView(){
        mToolbar = findViewById(R.id.game_toolbar);
        mIvFinishButton = findViewById(R.id.fish_finish_button);
        mCbHead = findViewById(R.id.fish_head_cb);
        mCbBody = findViewById(R.id.fish_body_cb);
        mCbTail = findViewById(R.id.fish_tail_cb);
        mTvBodyNum = findViewById(R.id.fish_body_num);
        mTvHeadNum = findViewById(R.id.fish_head_num);
        mTvTailNum = findViewById(R.id.fish_tail_num);

        mCbHead.setTag("머");
        mCbBody.setTag("몸");
        mCbTail.setTag("꼬");
    }

    private void changeView(CheckBox cbPivot, CheckBox cb1, CheckBox cb2,
                            TextView tvPivot, TextView tv1, TextView tv2){
        if (cbPivot.isChecked()) {
            mResultList.add(cbPivot.getTag().toString());
            Drawable drawable = getResources().getDrawable(R.drawable.num_selected_background);
            tvPivot.setBackground(drawable);
            tvPivot.setText(String.valueOf(mResultList.indexOf(cbPivot.getTag().toString())+1));
        }else{ //선택 해제하면 (false)
            mResultList.remove(cbPivot.getTag().toString()); //리스트에서 클릭한 아이템을 제거한다.

            tvPivot.setBackgroundResource(R.drawable.num_default_background); //디폴트 이미지로 변경한다.
            tvPivot.setText(""); //숫자 없애기.
            //나머지 뷰 숫자 변경
            tv1.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv2.setTextColor(getResources().getColor(R.color.colorDarkGrey));
            tv1.setText( String.valueOf(mResultList.indexOf(cb1.getTag().toString())+1));
            tv2.setText( String.valueOf(mResultList.indexOf(cb2.getTag().toString())+1));
        }
    }

    private void parseResult(){
        int result = mResultList.size();
        if (result != 3 ){
            printToast("모든 항목을 선택해주세요!");
        }else{
            //request to server
            StringBuilder stResult = new StringBuilder();
            for(int i = 0; i < result; i++){
                stResult.append(mResultList.get(i));
            }
            tryPostResult(stResult.toString());
//            printToast(stResult.toString());
        }
    }

    public void tryPostResult(String choice){
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(FISH_GAME_INDEX, choice);
    }

    public void onClickListener(View view){
        switch (view.getId()){
            case R.id.fish_head_cb:
                changeView(mCbHead, mCbBody, mCbTail, mTvHeadNum, mTvBodyNum, mTvTailNum);
                break;
            case R.id.fish_body_cb:
                changeView(mCbBody, mCbHead, mCbTail, mTvBodyNum, mTvHeadNum, mTvTailNum);
                break;
            case R.id.fish_tail_cb:
                changeView(mCbTail, mCbHead, mCbBody, mTvTailNum, mTvHeadNum, mTvBodyNum);
                break;
            case R.id.fish_finish_button:
                parseResult();
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess) {
            recordResult.setGameIdx(FISH_GAME_INDEX);
            Intent resultIntent = new Intent(FishActivity.this, ResultFishActivity.class);
            resultIntent.putExtra("record", recordResult);
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
