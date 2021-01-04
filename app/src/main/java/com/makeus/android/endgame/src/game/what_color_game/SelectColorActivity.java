package com.makeus.android.endgame.src.game.what_color_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.GameService;
import com.makeus.android.endgame.src.game.games.interfaces.GameActivityView;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

import java.util.ArrayList;

public class SelectColorActivity extends BaseActivity implements GameActivityView {
    private final String TAG = "SelectColorActivity";
    private ImageView mIvColorImage, mIvFinish;
    private CheckBox mCbLeftButton, mCbRightButton;
    private ArrayList<CheckBox> mCheckBoxList;
    private TextView mTvColorLeft, mTvColorRight;
    private RecordResult mRecordResult;

    //    private InterstitialAd mInterstitialAd;
    private boolean isClickedAd = false;

    ColorResult mColorResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select_color);

        Intent intent = getIntent();
        mColorResult = intent.getParcelableExtra("colorResult");

        initView();
        setGameToolbar(mBaseToolbar, getString(R.string.what_color_game_title), R.color.colorLightPurpleColor);

        mCheckBoxList = new ArrayList<>();
        mCheckBoxList.add(mCbLeftButton);
        mCheckBoxList.add(mCbRightButton);
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.ad_id_ad));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                if (isClickedAd) {
//                    hideProgressDialog();
//                    mInterstitialAd.show();
//                    isClickedAd = false;
//                }
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//            }
//
//            @Override
//            public void onAdOpened() {
//                hideProgressDialog();
//                if (mTimer != null) {
//                    mTimer.cancel();
//                }
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                showCustomToast(getString(R.string.post_result_success));
//                Intent resultIntent = new Intent(SelectColorActivity.this, ResultColorActivity.class);
//                resultIntent.putExtra("recordResult", mRecordResult);
//                resultIntent.putExtra("colorResult", mColorResult);
//                startActivity(resultIntent);
//                finish();
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });

    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar);
        mIvColorImage = findViewById(R.id.select_color_iv_image);
        mCbLeftButton = findViewById(R.id.select_color_cb_left);
        mCbRightButton = findViewById(R.id.select_color_cb_right);
        mIvFinish = findViewById(R.id.select_color_iv_finish);
        mTvColorLeft = findViewById(R.id.select_color_tv_left);
        mTvColorRight = findViewById(R.id.select_color_tv_right);

        mCbLeftButton.setTag(mColorResult.getColor1());
        mCbRightButton.setTag(mColorResult.getColor2());
        mIvColorImage.setImageResource(mColorResult.getImage());
        mTvColorLeft.setText(mColorResult.getColor1() + "");
        mTvColorRight.setText(mColorResult.getColor2() + "");
    }

    private void tryPostResult(String choice) {
        showProgressDialog();
        GameService gameService = new GameService(this);
        gameService.postResult(mColorResult.getGameIdx(), choice + "");
    }

    private void selectCheckBoxOnlyOne(View view) {
        //CheckBox 2개 중 1개만 선택할 수 있도록 하기.
        if (((CheckBox) view).isChecked()) {
            for (int i = 0; i < mCheckBoxList.size(); i++) {
                if (mCheckBoxList.get(i) != view) {
                    mCheckBoxList.get(i).setChecked(false);
                }
            }
        }
    }

    public void checkBoxOnClick(View view) {
        switch (view.getId()) {
            case R.id.select_color_cb_left:
                mColorResult.setSelect(1);
                selectCheckBoxOnlyOne(view);
                break;
            case R.id.select_color_cb_right:
                mColorResult.setSelect(2);
                selectCheckBoxOnlyOne(view);
                break;
            case R.id.select_color_iv_finish:
                if (mCbLeftButton.isChecked()) {
                    stopTimerTask(null);
                    tryPostResult(mCbLeftButton.getTag().toString());
                    break;
                } else if (mCbRightButton.isChecked()) {
                    stopTimerTask(null);
                    tryPostResult(mCbRightButton.getTag().toString());
                    break;
                } else {
                    showCustomToast(getString(R.string.notify_color_no_select));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, RecordResult recordResult) {
        hideProgressDialog();
        if (isSuccess) {
            mRecordResult = recordResult;
            isClickedAd = true;
//            if (mInterstitialAd.isLoaded()) {
//                hideProgressDialog();
//                mInterstitialAd.show();
//                isClickedAd = false;
//            } else {
//                showProgressDialog();
//            }
//            showCustomToast(getString(R.string.post_result_success));
            Intent resultIntent = new Intent(SelectColorActivity.this, ResultColorActivity.class);
            resultIntent.putExtra("recordResult", mRecordResult);
            resultIntent.putExtra("colorResult", mColorResult);
            startActivity(resultIntent);
            finish();
        } else {
            showCustomToast(getString(R.string.post_result_fail));
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(getString(R.string.post_result_fail));
    }
}
