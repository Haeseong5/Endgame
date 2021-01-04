package com.makeus.android.endgame.src.game.report;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.report.interfaces.ReportActivityView;
import com.makeus.android.endgame.src.main.ThemeActivity;

public class ReportActivity extends BaseActivity implements ReportActivityView {
    EditText mEtKakaoId, mEtGameName, mEtGameDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        initAppBar();

        mEtKakaoId = findViewById(R.id.report_et_kakao_id);
        mEtGameName = findViewById(R.id.report_et_game_name);
        mEtGameDesc = findViewById(R.id.report_et_game_desc);

    }

    private void initAppBar() {
        ImageView ivBackButton = findViewById(R.id.report_appbar_back_button);
        TextView tvTitle = findViewById(R.id.report_appbar_title);

        tvTitle.setText(getText(R.string.report_appbar_title));
        tvTitle.setTextColor(getResources().getColor(R.color.colorWhite));

        ivBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void tryPostReport() {
        String kakaoId = mEtKakaoId.getText().toString();
        String gameName = mEtGameName.getText().toString();
        String gameDesc = mEtGameDesc.getText().toString();

        if (kakaoId.length() == 0 || gameName.length() == 0 || gameDesc.length() == 0) {
            showCustomToast(getText(R.string.report_empty_value) + "");
        } else {
            showProgressDialog();
            ReportService reportService = new ReportService(this);
            reportService.postReport(kakaoId, gameName, gameDesc);
        }

    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess) {
            showCustomToast(getText(R.string.report_success) + "");
            finish();
        } else {
            showCustomToast(getText(R.string.network_error) + " : " + message);
        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(getText(R.string.network_error) + "");
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.report_iv_btn_send:
                tryPostReport();
                break;
            case R.id.report_ll_activity:
                hideKeyboard(mEtKakaoId);
                hideKeyboard(mEtGameName);
                hideKeyboard(mEtGameDesc);
                break;
            default:
                break;
        }
    }
}
