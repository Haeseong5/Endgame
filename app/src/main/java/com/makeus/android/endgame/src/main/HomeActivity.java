package com.makeus.android.endgame.src.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.rank.RankThemeActivity;
import com.makeus.android.endgame.src.game.result.ResultFishActivity;
import com.makeus.android.endgame.src.game.result.ResultFourActivity;
import com.makeus.android.endgame.src.game.result.ResultKmbtiActivity;
import com.makeus.android.endgame.src.game.result.ResultRamenActivity;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.login.LoginActivity;
import com.makeus.android.endgame.src.profile.ProfileActivity;
import com.makeus.android.endgame.src.setting.SettingActivity;

import java.security.MessageDigest;

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.RAMEN_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.android.endgame.src.ApplicationClass.sSharedPreferences;

public class HomeActivity extends BaseActivity {

    BackPressCloseHandler mBackPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        MobileAds.initialize(this, getString(R.string.app_id_ad)); // 광고 초기화

        mBaseToolbar = findViewById(R.id.main_toolbar);
        setMainToolbar(mBaseToolbar, false);
        mBackPressCloseHandler = new BackPressCloseHandler(this);

        //카카오링크 클릭 시 앱 실행 안되어있을 때 로그아웃되어있는 유저면 로그인화면으로 이동.
        final String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
        if (jwtToken == null){
            updateUI_login();
        }
    }

    @Override
    public void onBackPressed() {
        mBackPressCloseHandler.onBackPressed();
    }

    public void homeOnClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv_theme:
                startActivity(new Intent(HomeActivity.this, ThemeActivity.class));
                break;
            case R.id.home_iv_ranking:
                startActivity(new Intent(HomeActivity.this, RankThemeActivity.class));
                break;
            case R.id.home_iv_my_page:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                break;
            case R.id.home_iv_exit:
                finish();
                break;
            default:
                break;
        }
    }

    //카카오링크 클릭 시 앱이 실행되어 있는 경우 onNewIntent()호출됨.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        //로그인 여부 체크
        final String jwtToken = sSharedPreferences.getString(X_ACCESS_TOKEN, null);
        if (jwtToken != null){
            if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {// 커스텀 url일때
                Uri uri = getIntent().getData();
                if (uri != null){
                    if (uri.getQuery()!=null){
                        String query = uri.getQuery();
                        //parse query ->  ex) gameIdx=2&choice=꼬머몸&ratio=33
                        int gameIdx = Integer.parseInt(query.split("&")[0].split("=")[1]);
                        String choice = query.split("&")[1].split("=")[1];
                        String ratio = query.split("&")[2].split("=")[1];
                        updateUI(gameIdx,choice,ratio);
                    }
                }
            }
        }else{//로그인되어있찌 않은 사용자가 앱으로 들어오면
            updateUI_login();
        }
    }
    private void updateUI(int gameIdx, String choice, String ratio){
        Intent intent;
        RecordResult recordResult = new RecordResult();
        recordResult.setRatio(ratio);
        recordResult.setChoice(choice);
        recordResult.setGameIdx(gameIdx);
        switch (gameIdx){
            case KMBTI_GAME_INDEX:
                intent = new Intent(HomeActivity.this, ResultKmbtiActivity.class);
                intent.putExtra("record", recordResult);
                startActivity(intent);
                break;
            case RAMEN_GAME_INDEX:
                intent = new Intent(HomeActivity.this, ResultRamenActivity.class);
                intent.putExtra("record", recordResult);
                startActivity(intent);
                break;
            case WASH_GAME_INDEX:
                intent = new Intent(HomeActivity.this, ResultFourActivity.class);
                intent.putExtra("record", recordResult);
                intent.putExtra("activity", "wash");
                startActivity(intent);
                break;
            case VALUES_GAME_INDEX:
                intent = new Intent(HomeActivity.this, ResultFourActivity.class);
                intent.putExtra("record", recordResult);
                intent.putExtra("activity", "value");
                startActivity(intent);
                break;
            case FISH_GAME_INDEX:
                intent = new Intent(HomeActivity.this, ResultFishActivity.class);
                intent.putExtra("record", recordResult);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private void updateUI_login(){
        Intent intent1 = new Intent(HomeActivity.this, LoginActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);
    }
}
