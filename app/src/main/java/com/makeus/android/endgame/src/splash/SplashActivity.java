package com.makeus.android.endgame.src.splash;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.login.LoginActivity;
import com.makeus.android.endgame.src.main.HomeActivity;
import com.makeus.android.endgame.src.splash.interfaces.SplashActivityView;


public class SplashActivity extends BaseActivity implements SplashActivityView {

    private final int MY_REQUEST_CODE = 100;
    private AppUpdateManager mAppUpdateManager;
    Task<AppUpdateInfo> mAppUpdateInfoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler handler = new Handler();

        mAppUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        // Returns an intent object that you use to check for an update.
        mAppUpdateInfoTask = mAppUpdateManager.getAppUpdateInfo();

        mAppUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo,
                                // 유연한 업데이트 사용 시 (AppUpdateType.FLEXIBLE) 사용
                                AppUpdateType.IMMEDIATE,
                                // 현재 Activity
                                SplashActivity.this,
                                // 전역변수로 선언해준 Code
                                MY_REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                    // 업데이트가 사용 가능한 상태 (업데이트 있음) -> 이곳에서 업데이트를 요청해주자
                } else {
                    // 업데이트가 사용 가능하지 않은 상태(업데이트 없음) -> 다음 액티비티로 넘어가도록
                    tryGetAutoLogin();

                }
            }
        });

        mAppUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                tryGetAutoLogin();
            }
        });


    }


    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }

    void tryGetAutoLogin() {
        SplashService splashService = new SplashService(this);
        splashService.getAutoLogin();
    }

    @Override
    public void validateGetAutoLoginSuccess(boolean isSuccess, String message) {
        if (isSuccess) {
            Intent homeIntent = new Intent(SplashActivity.this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                showCustomToast("논란종결을 사용하기 위해서는 업데이트가 필요해요");  //사용자에게 알려주기
                finishAffinity(); // 앱 종료
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(
                new OnSuccessListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo appUpdateInfo) {
                        if (appUpdateInfo.updateAvailability()
                                == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // 인 앱 업데이트가 이미 실행중이었다면 계속해서 진행하도록
                            try {
                                mAppUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, SplashActivity.this, MY_REQUEST_CODE);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void validateFailure(String message) {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}