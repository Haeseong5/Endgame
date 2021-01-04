package com.makeus.android.endgame.src.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.games.CustomDialog;
import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.profile.adapter.CommentAdapter;
import com.makeus.android.endgame.src.profile.adapter.TabLayoutAdapter;
import com.makeus.android.endgame.src.profile.interfaces.ProfileActivityView;
import com.makeus.android.endgame.src.profile.models.ProfileResponse;
import com.makeus.android.endgame.src.setting.SettingActivity;

import java.util.ArrayList;

public class ProfileActivity extends BaseActivity implements ProfileActivityView {
    private ViewPager mViewPager;
    public TabLayout mTabLayout;
    private TextView mTvNickname, mTvEmail;
    private ArrayList<CommentResult> mCommentList;
    private ArrayList<RecordResult> mRecordList;
    private TabLayoutAdapter mTapLayoutAdapter;
    private CustomDialog mCustomDialog;
    private int mCommentIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mCommentList = new ArrayList<>();
        mRecordList = new ArrayList<>();

        initView();
        setGameResultToolbar(mBaseToolbar, getString(R.string.profile_title), R.color.colorWhite);
        tryGetMyPage();
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        ImageView ivSetting = findViewById(R.id.game_toolbar_setting);
        ivSetting.setVisibility(View.VISIBLE);
        mTvNickname = findViewById(R.id.profile_tv_name);
        mTvEmail = findViewById(R.id.profile_tv_email);

        mTabLayout = findViewById(R.id.profile_tab_layout);

        mTabLayout.addTab(mTabLayout.newTab().setText(mRecordList.size() + "\n기록"), 0);
        mTabLayout.addTab(mTabLayout.newTab().setText(mCommentList.size() + "\n댓글"), 1);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = findViewById(R.id.profile_view_pager);

        mTapLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), mRecordList, mCommentList);
//        mViewPager.setAdapter(mTapLayoutAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        // Set TabSelectedListener
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void tryGetMyPage() {
        showProgressDialog();
        ProfileService profileService = new ProfileService(this);
        profileService.getMyPage();
    }

    @Override
    public void validateSuccess(boolean isSuccess, int code, String message, ProfileResponse.Profile profile) {
        hideProgressDialog();
        if (isSuccess) {
            ProfileResponse.Profile.UserInfo userInfo = profile.getUserInfo();
            mRecordList = profile.getRecords();
            mCommentList = profile.getComments();

            mTabLayout.getTabAt(0).setText(mRecordList.size() + "\n기록");
            mTabLayout.getTabAt(1).setText(mCommentList.size() + "\n댓글");

            mTapLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), mRecordList, mCommentList);
            mViewPager.setAdapter(mTapLayoutAdapter);
            mTapLayoutAdapter.notifyDataSetChanged();

            mTvNickname.setText(userInfo.getNickname() + "");
            mTvEmail.setText(userInfo.getEmail() + "");
        }
    }

    @Override
    public void validateSuccessDeleteComment(boolean isSuccess, int code, String text, int commentIdx) {

    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(message + "");
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.game_toolbar_setting:
                Intent intent = new Intent(ProfileActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
