package com.makeus.android.endgame.src.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.main.HomeActivity;
import com.makeus.android.endgame.src.signup.interfaces.SignUpActivityView;

import java.util.regex.Pattern;

public class SignUpGoogleActivity extends BaseActivity implements SignUpActivityView {
    EditText mEtSignUpGoogleNickname;
    String mEmail;
    boolean mFlagNickname;
    LinearLayout mLlNickname;
    TextView mTvValidateNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_google);

        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");

        mEtSignUpGoogleNickname = findViewById(R.id.sign_up_google_et_nickname);
        mLlNickname = findViewById(R.id.sign_up_google_ll_nickname);
        mTvValidateNickname = findViewById(R.id.sign_up_google_tv_validate_nickname);

        EditText etEmail = findViewById(R.id.sign_up_google_et_email);
        etEmail.setText(mEmail + "");
        etEmail.setEnabled(false);

        mEtSignUpGoogleNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = mEtSignUpGoogleNickname.getText().toString();
                if (nickname.length() == 0) {
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateNickname.setText(getString(R.string.signup_tv_empty_nickname));
                    mTvValidateNickname.setVisibility(View.VISIBLE);
                    mFlagNickname = true;
                } else if (!Pattern.matches("^[가-힣A-Za-z0-9]{1,20}$", nickname)) {
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateNickname.setText(getString(R.string.signup_tv_match_nickname));
                    mTvValidateNickname.setVisibility(View.VISIBLE);
                    mFlagNickname = true;
                } else {
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_white);
                    mTvValidateNickname.setVisibility(View.INVISIBLE);
                    mFlagNickname = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    void tryPostSignUp(String nickname) {
        if (mFlagNickname) {
            showCustomToast(getString(R.string.signup_flag_can_signup));
        } else {
            showProgressDialog();
            SignUpService signUpService = new SignUpService(this);
            signUpService.postSignUpGoogle(nickname, mEmail);
        }
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_google_iv_start_app:
                hideKeyboard(mEtSignUpGoogleNickname);
                String nickname = mEtSignUpGoogleNickname.getText() + "";
                tryPostSignUp(nickname);
                break;
            case R.id.sign_up_google_iv_nickname_close:
                mEtSignUpGoogleNickname.setText("");
                break;
            case R.id.sign_up_google_ll_activity:
                hideKeyboard(mEtSignUpGoogleNickname);
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccess(boolean isSuccess, String message, int code) {
        hideProgressDialog();
        if (code == 307) {
            mTvValidateNickname.setText(getString(R.string.signup_tv_duplicate_nickname));
            mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
            mTvValidateNickname.setVisibility(View.VISIBLE);
            showCustomToast(getString(R.string.signup_tv_duplicate_nickname));
        } else if (code == 200) {
            Intent signUpIntent = new Intent(SignUpGoogleActivity.this, HomeActivity.class);
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signUpIntent);
        } else {
            showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}
