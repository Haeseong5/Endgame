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

import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;

public class ResultKmbtiActivity extends BaseActivity implements ResultActivityView {

    private ArrayList<TextView> mResultTvList;
    private TextView mTvResultText1, mTvResultText2, mTvResultText3, mTvResultText4, mTvResultRatio;
    private ImageView mIvKakaoShare, mIvSaveGallery, mIvResult;
    String mChoice;
    private FrameLayout mRootView; //캡쳐할영역(프레임레이아웃)
//    private InterstitialAd mInterstitialAd;
    boolean isClickedAd = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_kmbti);
        initView();
        setGameResultToolbar(mBaseToolbar, getString(R.string.result_title), R.color.colorYellowKmbti);
        checkPermission();
//        printToast("onCreate()");
        if (getIntent() != null) {
            Intent intent = getIntent();
            RecordResult recordResult = intent.getParcelableExtra("record");
            String ratio = null;
            if (recordResult != null) {
                mChoice = recordResult.getChoice() + "";
                ratio = recordResult.getRatio();
            }
            if (mChoice != null && !mChoice.equals("")) {
                for (int i = 0; i < mChoice.length(); i++) {
                    mResultTvList.get(i).setText(String.valueOf(mChoice.charAt(i)));
                }
                switch (mChoice) {
                    case "짜찍물밀":
                        mIvResult.setImageResource(R.drawable.img_zzazzicmulmil);
                        break;
                    case "짜찍물쌀":
                        mIvResult.setImageResource(R.drawable.img_zzazzicmulssal);
                        break;
                    case "짜찍비밀":
                        mIvResult.setImageResource(R.drawable.img_zzazzicbemil);
                        break;
                    case "짜찍비쌀":
                        mIvResult.setImageResource(R.drawable.img_zzazzicbessal);
                        break;
                    case "짜부물밀":
                        mIvResult.setImageResource(R.drawable.img_zzaboomulmil);
                        break;
                    case "짜부물쌀":
                        mIvResult.setImageResource(R.drawable.img_zzaboomulssal);
                        break;
                    case "짜부비밀":
                        mIvResult.setImageResource(R.drawable.img_zzaboobemil);
                        break;
                    case "짜부비쌀":
                        mIvResult.setImageResource(R.drawable.img_zzaboobessal);
                        break;
                    case "짬찍물밀":
                        mIvResult.setImageResource(R.drawable.img_zzamzzicmulmil);
                        break;
                    case "짬찍물쌀":
                        mIvResult.setImageResource(R.drawable.img_zzamzzicmulssal);
                        break;
                    case "짬찍비밀":
                        mIvResult.setImageResource(R.drawable.img_zzamzzicbemil);
                        break;
                    case "짬찍비쌀":
                        mIvResult.setImageResource(R.drawable.img_zzamzzicbessal);
                        break;
                    case "짬부물밀":
                        mIvResult.setImageResource(R.drawable.img_zzamboomulmil);
                        break;
                    case "짬부물쌀":
                        mIvResult.setImageResource(R.drawable.img_zzamboomulssal);
                        break;
                    case "짬부비밀":
                        mIvResult.setImageResource(R.drawable.img_zzamboobemil);
                        break;
                    case "짬부비쌀":
                        mIvResult.setImageResource(R.drawable.img_zzamboobessal);
                        break;
                }
            }
            if (ratio != null && !ratio.equals("")) {
                mTvResultRatio.setText(Math.round(Double.parseDouble(ratio)) + "");
            }
        }
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
//                Intent intent = new Intent(ResultKmbtiActivity.this, RankActivity.class);
//                intent.putExtra("gameIdx", 1);
//                intent.putExtra("choice", mChoice);
//                startActivity(intent);
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        mTvResultText1 = findViewById(R.id.result_kmbti_tv_1);
        mTvResultText2 = findViewById(R.id.result_kmbti_tv_2);
        mTvResultText3 = findViewById(R.id.result_kmbti_tv_3);
        mTvResultText4 = findViewById(R.id.result_kmbti_tv_4);
        mTvResultRatio = findViewById(R.id.result_kmbti_tv_ratio);
        mIvResult = findViewById(R.id.result_kmbti_iv_image);
        mRootView = findViewById(R.id.result_kmbti_layout);
        mResultTvList = new ArrayList<>();
        mResultTvList.add(mTvResultText1);
        mResultTvList.add(mTvResultText2);
        mResultTvList.add(mTvResultText3);
        mResultTvList.add(mTvResultText4);
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
        resultService.uploadImageKakaoLink(mRootView, KMBTI_GAME_INDEX, mChoice, Integer.parseInt(mTvResultRatio.getText().toString()));
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
            case R.id.result_kmbti_tv_rank:
//                isClickedAd = true;
//                if (mInterstitialAd.isLoaded()) {
//                    hideProgressDialog();
//                    mInterstitialAd.show();
//                    isClickedAd = false;
//                } else {
//                    showProgressDialog();
//                }
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                intent.putExtra("gameIdx", 1);
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
        showCustomToast(message);
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

