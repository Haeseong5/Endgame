package com.makeus.android.endgame.src.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.login.LoginActivity;

import static com.makeus.android.endgame.src.ApplicationClass.X_ACCESS_TOKEN;
import static com.makeus.android.endgame.src.ApplicationClass.sSharedPreferences;

public class SettingActivity extends BaseActivity {

    GoogleApiClient mGoogleApiClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        setGameResultToolbar(mBaseToolbar, "설정", R.color.colorWhite);

        // GoogleSignInOptions 개체 구성
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                    }

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();

    }


    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.setting_cl_sing_out:
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                editor.remove(X_ACCESS_TOKEN);
                editor.apply();
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    signOut();
                }
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.setting_cl_lisence:
                Intent intent1 = new Intent(SettingActivity.this, LicenseActivity.class);
                startActivity(intent1);
                break;
            case R.id.setting_cl_info:
                Intent intent2 = new Intent(SettingActivity.this, AppInfoActivity.class);
                startActivity(intent2);
                break;

            case R.id.setting_cl_request:
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("plain/Text");
                String[] address = {getString(R.string.email)};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " " + getString(R.string.request) + ">");
                startActivity(email);
            default:
                break;
        }
    }

    public void signOut() {

        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mAuth.signOut();
                if (mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {

                        @Override
                        public void onResult(@NonNull Status status) {
                            hideProgressDialog();
                            finish();
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                hideProgressDialog();
                finish();
            }
        });

        SharedPreferences.Editor editor = sSharedPreferences.edit();
        editor.remove(X_ACCESS_TOKEN).apply();

    }
}