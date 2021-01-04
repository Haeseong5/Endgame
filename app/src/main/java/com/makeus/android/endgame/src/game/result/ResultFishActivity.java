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

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;

public class ResultFishActivity extends BaseActivity implements ResultActivityView {
    private ArrayList<TextView> mResultTvList;
    private ArrayList<ImageView> mIconList;
    private TextView mTvResultText1, mTvResultText2, mTvResultText3, mTvResultRatio;
    private ImageView mIvIcon1, mIvIcon2, mIvIcon3;
    private ImageView mIvKakaoShare, mIvSaveGallery;
//    private InterstitialAd mInterstitialAd;
    private boolean isClickedAd = false;

    String mChoice;
    private FrameLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_fish);
        initView();
        setGameResultToolbar(mBaseToolbar, getString(R.string.result_title), R.color.colorGreenFish);
        checkPermission();

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
//                Intent intent = new Intent(ResultFishActivity.this, RankActivity.class);
//                intent.putExtra("gameIdx", 2);
//                intent.putExtra("choice", mChoice);
//                startActivity(intent);
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });


        if (getIntent() != null) {
            Intent intent = getIntent();
            RecordResult recordResult = intent.getParcelableExtra("record");
            if (recordResult != null) {
                mChoice = recordResult.getChoice() + "";
                String ratio = recordResult.getRatio();
                if (mChoice != null && !mChoice.equals("")) {
                    for (int i = 0; i < mChoice.length(); i++) {
                        mResultTvList.get(i).setText(String.valueOf(mChoice.charAt(i)));
                        setIcon(i, String.valueOf(mChoice.charAt(i)));
                    }
                }
                if (ratio != null && !ratio.equals("")) {
                    mTvResultRatio.setText(Math.round(Double.parseDouble(ratio)) + "");
                }
            }

        }
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        mTvResultText1 = findViewById(R.id.result_fish_tv_1);
        mTvResultText2 = findViewById(R.id.result_fish_tv_2);
        mTvResultText3 = findViewById(R.id.result_fish_tv_3);
        mTvResultRatio = findViewById(R.id.result_fish_tv_ratio);
        mIvIcon1 = findViewById(R.id.result_fish_iv_1);
        mIvIcon2 = findViewById(R.id.result_fish_iv_2);
        mIvIcon3 = findViewById(R.id.result_fish_iv_3);
        mRootView = findViewById(R.id.result_fish_layout);
        mResultTvList = new ArrayList<>();
        mIconList = new ArrayList<>();

        mResultTvList.add(mTvResultText1);
        mResultTvList.add(mTvResultText2);
        mResultTvList.add(mTvResultText3);
        mIconList.add(mIvIcon1);
        mIconList.add(mIvIcon2);
        mIconList.add(mIvIcon3);

    }

    private void setIcon(int index, String word) {
        switch (word) {
            case "머":
                mIconList.get(index).setImageResource(R.drawable.ic_head);
                break;
            case "몸":
                mIconList.get(index).setImageResource(R.drawable.ic_body);
                break;
            case "꼬":
                mIconList.get(index).setImageResource(R.drawable.ic_tail);
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
        resultService.uploadImageKakaoLink(mRootView, FISH_GAME_INDEX, mChoice, Integer.parseInt(mTvResultRatio.getText().toString()));
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
            case R.id.result_fish_tv_ranking:
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
                intent.putExtra("gameIdx", 2);
                intent.putExtra("choice", mChoice);
                startActivity(intent);

                break;
            default:
                break;
        }
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }

    @Override
    public void validateSuccessKakakolink(String message) {
    }

    @Override
    public void validateFailureKakakolink(String message) {
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
