package com.makeus.android.endgame.src.game.result;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.gun0912.tedpermission.TedPermission;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.rank.RankActivity;
import com.makeus.android.endgame.src.game.result.interfaces.ResultActivityView;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;

/**
 * 결과 창에 단어가 4개인 경우
 * 가치관 / 샤워
 */
public class ResultFourActivity extends BaseActivity implements ResultActivityView {
    private String mGAME, mChoice;
    private int mGameIdx;
    private ArrayList<TextView> mResultTvList;
    private ArrayList<ImageView> mIconViewList;
    private TextView mTvResultText1, mTvResultText2, mTvResultText3, mTvResultText4, mTvResultRatio;
    private ImageView mIvIcon1, mIvIcon2, mIvIcon3, mIvIcon4, mIvBackground;

    private ImageView mIvKakaoShare, mIvSaveGallery;
    private FrameLayout mRootView;
//    private InterstitialAd mInterstitialAd;
    private boolean isClickedAd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_four);
        initView();
        checkPermission();
        if (getIntent() != null) {
            Intent intent = getIntent();
            RecordResult recordResult = intent.getParcelableExtra("record");
            if (recordResult != null) {
                mChoice = recordResult.getChoice() + "";
                String ratio = recordResult.getRatio();
                mGAME = getIntent().getStringExtra("activity");
                if (mGAME != null) {
                    switch (mGAME) {
                        case "value":
                            setGameResultToolbar(mBaseToolbar, getString(R.string.result_title), R.color.colorBlueValues);
                            mIvBackground.setImageResource(R.drawable.img_values_bg_gradient);
                            for (int i = 0; i < mChoice.length(); i++) {
                                mResultTvList.get(i).setText(String.valueOf(mChoice.charAt(i)));
                                setValueIcon(i, String.valueOf(mChoice.charAt(i)));
                            }
                            mGameIdx = VALUES_GAME_INDEX;
                            break;
                        case "wash":
                            setGameResultToolbar(mBaseToolbar, getString(R.string.result_title), R.color.colorPurpleWash);
                            mIvBackground.setImageResource(R.drawable.img_wash_bg_gradient);
                            for (int i = 0; i < mChoice.length(); i++) {
                                mResultTvList.get(i).setText(String.valueOf(mChoice.charAt(i)));
                                setShowerIcon(i, String.valueOf(mChoice.charAt(i)));
                            }
                            mGameIdx = WASH_GAME_INDEX;
                            break;
                        default:
                            break;
                    }
                }
                if (ratio != null && !ratio.equals("")) {
                    mTvResultRatio.setText(Math.round(Double.parseDouble(ratio)) + "");
                }

            }

//            mInterstitialAd = new InterstitialAd(this);
//            mInterstitialAd.setAdUnitId(getString(R.string.ad_id_ad));
//            mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//            mInterstitialAd.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    if (isClickedAd) {
//                        hideProgressDialog();
//                        mInterstitialAd.show();
//                        isClickedAd = false;
//                    }
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                }
//
//                @Override
//                public void onAdOpened() {
//                    hideProgressDialog();
//                    if (mTimer != null) {
//                        mTimer.cancel();
//                    }
//                    // Code to be executed when the ad is displayed.
//                }
//
//                @Override
//                public void onAdClicked() {
//                    // Code to be executed when the user clicks on an ad.
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    // Code to be executed when the user has left the app.
//                }
//
//                @Override
//                public void onAdClosed() {
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                    Intent intent = new Intent(getApplicationContext(), RankActivity.class);
//                    intent.putExtra("gameIdx", mGameIdx);
//                    intent.putExtra("choice", mChoice);
//                    startActivity(intent);
//                    // Code to be executed when the interstitial ad is closed.
//                }
//            });


        }
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        mTvResultText1 = findViewById(R.id.result_four_tv_1);
        mTvResultText2 = findViewById(R.id.result_four_tv_2);
        mTvResultText3 = findViewById(R.id.result_four_tv_3);
        mTvResultText4 = findViewById(R.id.result_four_tv_4);
        mTvResultRatio = findViewById(R.id.result_four_tv_ratio);
        mIvIcon1 = findViewById(R.id.result_four_iv_1);
        mIvIcon2 = findViewById(R.id.result_four_iv_2);
        mIvIcon3 = findViewById(R.id.result_four_iv_3);
        mIvIcon4 = findViewById(R.id.result_four_iv_4);
        mIvBackground = findViewById(R.id.result_four_iv_background);
        mRootView = findViewById(R.id.result_four_layout);

        mResultTvList = new ArrayList<>();
        mIconViewList = new ArrayList<>();

        mResultTvList.add(mTvResultText1);
        mResultTvList.add(mTvResultText2);
        mResultTvList.add(mTvResultText3);
        mResultTvList.add(mTvResultText4);
        mIconViewList.add(mIvIcon1);
        mIconViewList.add(mIvIcon2);
        mIconViewList.add(mIvIcon3);
        mIconViewList.add(mIvIcon4);
    }

    private void setShowerIcon(int index, String word) {

        if (word.equals("샤")) {
            mIconViewList.get(index).setImageResource(R.drawable.ic_shower);
        } else if (word.equals("세")) {
            mIconViewList.get(index).setImageResource(R.drawable.ic_face);
        } else if (word.equals("양")) {
            mIconViewList.get(index).setImageResource(R.drawable.ic_tooth);
        } else if (word.equals("머")) {
            mIconViewList.get(index).setImageResource(R.drawable.ic_hair);
        }


//        switch (word){
//            case "샤":
//                mIconViewList.get(index).setImageResource(R.drawable.ic_shower);
//                break;
//            case "세":
//                mIconViewList.get(index).setImageResource(R.drawable.ic_face);
//                break;
//            case "양":
//                mIconViewList.get(index).setImageResource(R.drawable.ic_tooth);
//                break;
//            case "머":
//                mIconViewList.get(index).setImageResource(R.drawable.ic_hair);
//                break;
//            default:
//                break;
//        }
    }

    private void setValueIcon(int index, String word) {
        switch (word) {
            case "돈":
                mIconViewList.get(index).setImageResource(R.drawable.ic_money);
                break;
            case "명":
                mIconViewList.get(index).setImageResource(R.drawable.ic_honor);
                break;
            case "친":
                mIconViewList.get(index).setImageResource(R.drawable.ic_friends);
                break;
            case "가":
                mIconViewList.get(index).setImageResource(R.drawable.ic_family);
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("결과를 저장 하기 위해서는 외부저장소 접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    void tryShareKakaoLink() {
        ResultService resultService = new ResultService(this, getApplicationContext());
        resultService.uploadImageKakaoLink(mRootView, mGameIdx, mChoice, Integer.parseInt(mTvResultRatio.getText().toString()));
    }

    void trySaveImage() {
        ResultService resultService = new ResultService(this, getApplicationContext());
        resultService.saveImage(mRootView);
    }

    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.result_iv_kakao_share:
                tryShareKakaoLink();
                break;
            case R.id.result_iv_save_gallery:
                trySaveImage();
                break;
            case R.id.result_four_tv_ranking:
                isClickedAd = true;
//                if (mInterstitialAd.isLoaded()) {
//                    hideProgressDialog();
//                    mInterstitialAd.show();
//                    isClickedAd = false;
//                } else {
//                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
//                    showProgressDialog();
//                }
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                intent.putExtra("gameIdx", mGameIdx);
                intent.putExtra("choice", mChoice);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccessKakakolink(String message) {
    }

    @Override
    public void validateFailureKakakolink(String message) {
        showCustomToast(getString(R.string.network_error));
    }


    @Override
    public void validateSuccessSaveImage(String message, String path, SimpleDateFormat day, Date date) {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/EndGame" + day.format(date) + ".JPEG")));
        printToast(message);
    }

    @Override
    public void validateFailureSaveImage(String message) {
        printToast(message);
    }
}
