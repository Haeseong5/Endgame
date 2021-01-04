package com.makeus.android.endgame.src.signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.login.LoginActivity;
import com.makeus.android.endgame.src.main.HomeActivity;
import com.makeus.android.endgame.src.signup.interfaces.SignUpActivityView;

import java.util.regex.Pattern;

public class SignUpActivity extends BaseActivity implements SignUpActivityView {
    TextView mTvValidateNickname, mTvValidateEmail, mTvValidatePw, mTvValidatePwConfirm, mTvBtnLogin;
    EditText mEtNickname, mEtEmail, mEtPw, mEtPwConfirm;
    ImageView mIvBtnStartApp, mIvNicknameClose, mIvEmailClose, mIvPwClose, mIvPwConfirmClose;
    LinearLayout mLlNickname, mLlEmail, mLlPw, mLlPwConfirm;

    boolean mFlagNickname, mFlagEmail, mFlagPw, mFlagPwConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mTvValidateNickname = findViewById(R.id.sign_up_tv_validate_nickname);
        mTvValidateEmail = findViewById(R.id.sign_up_tv_validate_email);
        mTvValidatePw = findViewById(R.id.sign_up_tv_validate_pw);
        mTvValidatePwConfirm = findViewById(R.id.sign_up_tv_validate_pw_confirm);
        mTvBtnLogin = findViewById(R.id.sign_up_tv_login);

        mEtNickname = findViewById(R.id.sign_up_et_nickname);
        mEtEmail = findViewById(R.id.sign_up_et_email);
        mEtPw = findViewById(R.id.sign_up_et_pw);
        mEtPwConfirm = findViewById(R.id.sign_up_et_pw_confirm);

        mIvBtnStartApp = findViewById(R.id.sign_up_iv_start_app);
        mIvNicknameClose = findViewById(R.id.sign_up_iv_nickname_close);
        mIvEmailClose = findViewById(R.id.sign_up_iv_email_close);
        mIvPwClose = findViewById(R.id.sign_up_iv_pw_close);
        mIvPwConfirmClose = findViewById(R.id.sign_up_iv_pw_confirm_close);

        mLlNickname = findViewById(R.id.sign_up_ll_nickname);
        mLlEmail = findViewById(R.id.sign_up_ll_email);
        mLlPw = findViewById(R.id.sign_up_ll_pw);
        mLlPwConfirm = findViewById(R.id.sign_up_ll_pw_confirm);

        mEtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = mEtNickname.getText().toString();
                if(nickname.length()==0){
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateNickname.setText(getString(R.string.signup_tv_empty_nickname));
                    mTvValidateNickname.setVisibility(View.VISIBLE);
                    mFlagNickname = true;
                }else if(!Pattern.matches("^[가-힣A-Za-z0-9]{1,20}$", nickname)){
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateNickname.setText(getString(R.string.signup_tv_match_nickname));
                    mTvValidateNickname.setVisibility(View.VISIBLE);
                    mFlagNickname = true;
                }else{
                    mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_white);
                    mTvValidateNickname.setVisibility(View.INVISIBLE);
                    mFlagNickname = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = mEtEmail.getText().toString();
                if(email.length()==0){
                    mLlEmail.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateEmail.setText(getString(R.string.signup_tv_empty_email));
                    mTvValidateEmail.setVisibility(View.VISIBLE);
                    mFlagEmail = true;
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mLlEmail.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidateEmail.setText(getString(R.string.signup_tv_match_email));
                    mTvValidateEmail.setVisibility(View.VISIBLE);
                    mFlagEmail = true;
                }else{
                    mLlEmail.setBackgroundResource(R.drawable.bg_border_bottom_white);
                    mTvValidateEmail.setVisibility(View.INVISIBLE);
                    mFlagEmail = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = mEtPw.getText().toString();
                if(pw.length()==0){
                    mLlPw.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidatePw.setText(getString(R.string.signup_tv_empty_pw));
                    mTvValidatePw.setVisibility(View.VISIBLE);
                    mFlagPw = true;
                }else if(!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,20}$", pw)){
                    mLlPw.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidatePw.setText(getString(R.string.signup_tv_match_pw));
                    mTvValidatePw.setVisibility(View.VISIBLE);
                    mFlagPw = true;
                }else{
                    mLlPw.setBackgroundResource(R.drawable.bg_border_bottom_white);
                    mTvValidatePw.setVisibility(View.INVISIBLE);
                    mFlagPw = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPwConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pw = mEtPw.getText().toString();
                String pwConfirm = mEtPwConfirm.getText().toString();
                if(pwConfirm.length()==0){
                    mLlPwConfirm.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidatePwConfirm.setText(getString(R.string.signup_tv_empty_pw_confirm));
                    mTvValidatePwConfirm.setVisibility(View.VISIBLE);
                    mFlagPwConfirm = true;
                }else if(!pw.equals(pwConfirm)){
                    mLlPwConfirm.setBackgroundResource(R.drawable.bg_border_bottom_red);
                    mTvValidatePwConfirm.setText(getString(R.string.signup_tv_incorrect_pw));
                    mTvValidatePwConfirm.setVisibility(View.VISIBLE);
                    mFlagPwConfirm = true;
                }else{
                    mLlPwConfirm.setBackgroundResource(R.drawable.bg_border_bottom_white);
                    mTvValidatePwConfirm.setVisibility(View.INVISIBLE);
                    mFlagPwConfirm = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //flag 만들어서 모든 조건이 맞을 때만 이 메소드 쏠 수 있도록,,,수정,,,,,,
    private void trySignUp(){
        if(mFlagNickname||mFlagEmail||mFlagPw||mFlagPwConfirm){
            showCustomToast(getString(R.string.signup_flag_can_signup));
        }else{
            String nickname = mEtNickname.getText().toString();
            String email = mEtEmail.getText().toString();
            String pw = mEtPw.getText().toString();

            showProgressDialog();
            SignUpService signUpService = new SignUpService(this);
            signUpService.postSignUp(nickname, email, pw);
        }
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_iv_start_app:
                trySignUp();
                break;
            case R.id.sign_up_tv_login:
                Intent signUpIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signUpIntent);
                break;
            case R.id.sign_up_iv_nickname_close:
                mEtNickname.setText("");
                break;
            case R.id.sign_up_iv_email_close:
                mEtEmail.setText("");
                break;
            case R.id.sign_up_iv_pw_close:
                mEtPw.setText("");
                break;
            case R.id.sign_up_iv_pw_confirm_close:
                mEtPwConfirm.setText("");
                break;
            case R.id.sign_up_ll_activity:
                hideKeyboard(mEtNickname);
                hideKeyboard(mEtEmail);
                hideKeyboard(mEtPw);
                hideKeyboard(mEtPwConfirm);
                break;
            default:
                break;
        }
    }

    @Override
    public void validateSuccess(boolean isSuccess, String message, int code) {
        hideProgressDialog();
        if(code==306){
            mTvValidateEmail.setText(getString(R.string.signup_tv_duplicate_email));
            mLlEmail.setBackgroundResource(R.drawable.bg_border_bottom_red);
            mTvValidateEmail.setVisibility(View.VISIBLE);
            showCustomToast(getString(R.string.signup_tv_duplicate_email));
        }else if(code==307){
            mTvValidateNickname.setText(getString(R.string.signup_tv_duplicate_nickname));
            mLlNickname.setBackgroundResource(R.drawable.bg_border_bottom_red);
            mTvValidateNickname.setVisibility(View.VISIBLE);
            showCustomToast(getString(R.string.signup_tv_duplicate_nickname));
        }else if(code==200){
            Intent signUpIntent = new Intent(SignUpActivity.this, HomeActivity.class);
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            signUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signUpIntent);
        }else{
            showCustomToast(message);
        }
    }

    @Override
    public void validateFailure(String message) {
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }
}
