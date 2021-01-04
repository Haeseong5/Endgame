package com.makeus.android.endgame.src;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.gun0912.tedpermission.PermissionListener;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.games.CustomDialog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    public ProgressDialog mProgressDialog;
    public Toolbar mBaseToolbar;
    CustomDialog mCustomDialog;

    public int mCount;
    LottieAnimationView mLav;
    TextView mTvTimer;
    TimerTask mTimerTask;
    public Timer mTimer = new Timer();

//    InterstitialAd mInterstitialAd;


    public void showCustomToast(final String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 3초가 지나면 다이얼로그 닫기
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                showCustomToast(getString(R.string.network_error));
                            }
                        });
                    }
                };

                Timer timer = new Timer();
                timer.schedule(task, 10000);
            }
        });
        if (mProgressDialog.isShowing()) {
            thread.start();
        }


        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideKeyboard(EditText editText) {
        InputMethodManager inputManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void setGameResultToolbar(Toolbar toolbar, String title, int lineColor) {
        if (toolbar == null && title == null) {
            LayerDrawable layerDrawable = (LayerDrawable) getResources()
                    .getDrawable(R.drawable.bg_toolbar_title_line);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.item_tb_title_line);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

            gradientDrawable.setStroke(px, getResources().getColor(lineColor));
        } else {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
                actionBar.setHomeAsUpIndicator(R.drawable.navi_btn_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
            }

            LinearLayout titleLine = findViewById(R.id.game_toolbar_result_layout_line);
            LayerDrawable layerDrawable = (LayerDrawable) getResources()
                    .getDrawable(R.drawable.bg_toolbar_title_line);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.item_tb_title_line);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

            gradientDrawable.setStroke(px, getResources().getColor(lineColor));
            titleLine.setBackground(layerDrawable);
            TextView tvTitle = findViewById(R.id.game_toolbar_result_tv_title);
            tvTitle.setText(title);
//        tvTitle.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    public void setGameToolbar(Toolbar toolbar, String title, int lineColor) {
        if (toolbar == null && title == null) {
            LayerDrawable layerDrawable = (LayerDrawable) getResources()
                    .getDrawable(R.drawable.bg_toolbar_title_line);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.item_tb_title_line);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

            gradientDrawable.setStroke(px, getResources().getColor(lineColor));
        } else {

            mTvTimer = findViewById(R.id.game_toolbar_tv_timer);
            mLav = findViewById(R.id.game_toolbar_lav_clock);
            startTimerTask(mTvTimer, this);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
                actionBar.setHomeAsUpIndicator(R.drawable.navi_btn_back); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
            }

            LinearLayout titleLine = findViewById(R.id.game_toolbar_layout_line);
            LayerDrawable layerDrawable = (LayerDrawable) getResources()
                    .getDrawable(R.drawable.bg_toolbar_title_line);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.item_tb_title_line);
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

            gradientDrawable.setStroke(px, getResources().getColor(lineColor));
            titleLine.setBackground(layerDrawable);
            TextView tvTitle = findViewById(R.id.game_toolbar_tv_title);
            tvTitle.setText(title);
        }
    }

    public void setMainToolbar(Toolbar toolbar, boolean setBackButtonEnabled) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true); //custom toolbar
            actionBar.setHomeAsUpIndicator(R.drawable.navi_btn_back); //back button custom
            actionBar.setDisplayShowTitleEnabled(false); //no title
            actionBar.setDisplayHomeAsUpEnabled(setBackButtonEnabled); // back button
        }

    }

    public void setRankToolbar(Toolbar toolbar, boolean setBackButtonEnabled) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true); //custom toolbar
            actionBar.setHomeAsUpIndicator(R.drawable.navi_btn_back); //back button custom
            actionBar.setDisplayShowTitleEnabled(false); //no title
            actionBar.setDisplayHomeAsUpEnabled(setBackButtonEnabled); // back button
        }

    }

    private void startTimerTask(final TextView textView, final Context context) {
        mCount = 3000;
        mLav.playAnimation();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mCount--;
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCount <= 0) {
                            mLav.pauseAnimation();
                            stopTimerTask(mTvTimer);
//                            if (mInterstitialAd.isLoaded()) {
//                                mInterstitialAd.show();
//                            } else {
//                                showProgressDialog();
//                            }

                            mCustomDialog = new CustomDialog(context, positiveListener, negativeListener, getString(R.string.dialog_time_out_title), getString(R.string.dialog_time_out_contents));
                            mCustomDialog.show();

                            mCount = 10;
                        } else {
                            if (Integer.toString(mCount).length() == 4) {
                                textView.setText(Integer.toString(mCount).substring(0, 2) + ":" + Integer.toString(mCount).substring(2));
                            } else if (Integer.toString(mCount).length() == 3) {
                                textView.setText("0" + Integer.toString(mCount).substring(0, 1) + ":" + Integer.toString(mCount).substring(1));
                            } else {
                                if (mCount < 10) {
                                    textView.setText("00:" + "0" + mCount);
                                } else {
                                    textView.setText("00:" + mCount);

                                }
                            }
                        }
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, 0, 10);
    }

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            mCustomDialog.dismiss();
            finish();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
            finish();
        }
    };

    protected void stopTimerTask(TextView textView) {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            if (textView != null)
                textView.setText("30:00");
            mTimerTask = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        setGameResultToolbar(null, null, R.color.colorWhite);
        setGameToolbar(null, null, R.color.colorWhite);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();

    }

    public void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void printLog(String TAG, String log) {
        Log.d(TAG, log);
    }


    public class BackPressCloseHandler {

        private long backKeyPressedTime = 0;
        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showCustomToast(getString(R.string.backPressed));
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getString(R.string.ad_id_ad));
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
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
//
//                // Code to be executed when the interstitial ad is closed.
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // Toolbar의 백버튼 클릭시 호출.
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
//                Toast.makeText(Activity1.this, "권한 허가", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {

        }
    };
}
