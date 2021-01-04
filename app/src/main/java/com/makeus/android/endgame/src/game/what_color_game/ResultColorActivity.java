package com.makeus.android.endgame.src.game.what_color_game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.rank.RankService;
import com.makeus.android.endgame.src.game.rank.interfaces.RankActivityView;
import com.makeus.android.endgame.src.game.result.CommentService;
import com.makeus.android.endgame.src.game.result.adapter.ResultCommentAdapter;
import com.makeus.android.endgame.src.game.result.interfaces.CommentActivityView;
import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

import java.util.ArrayList;

public class ResultColorActivity extends BaseActivity implements CommentActivityView, ResultCommentAdapter.OnItemClickListener, RankActivityView {
    private final String TAG = "ResultColorActivity";
    private ImageView mIvColorImage, mIvFinish;
    private CheckBox mCbLeftButton, mCbRightButton;
    private TextView mTvColorLeft, mTvColorRight, mTvColorLeftRatio, mTvColorRightRatio, mTvCommentTotal;
    private EditText mEtInputComment;
    private ArrayList<CheckBox> mCheckBoxList;
    private RecyclerView mRecyclerView;
    private ArrayList<CommentResult> mCommentDataList;
    private ResultCommentAdapter mCommentAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ColorResult mColorResult;
    private RecordResult mRecordResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_color);

        Intent intent = getIntent();
        if (intent != null) {
            mColorResult = intent.getParcelableExtra("colorResult");
            mRecordResult = intent.getParcelableExtra("recordResult");
        }

        mCommentDataList = new ArrayList<>();
        initView();
        setGameResultToolbar(mBaseToolbar, getString(R.string.result_title), R.color.colorLightPurpleColor);

        tryGetRanks(mColorResult.getGameIdx());
        tryGetComments();
        if (mRecordResult == null) {
            if (mColorResult.getSelect() == 1) {
                //1 -> 왼쪽 체크표시
                mCbLeftButton.setChecked(true);
            } else if (mColorResult.getSelect() == 2) {
                //2 -> 오른쪽 체크표시
                mCbRightButton.setChecked(true);
            }
        } else { //마이프로필에서 넘어왔을 때.

            if (mRecordResult.getChoice().equals(mCbLeftButton.getTag())) {
                mCbLeftButton.setChecked(true);
            } else {
                mCbRightButton.setChecked(true);
            }
        }

        mCheckBoxList = new ArrayList<>();
        mCheckBoxList.add(mCbLeftButton);
        mCheckBoxList.add(mCbRightButton);

//        selectCheckBoxOnlyOne();
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        mIvColorImage = findViewById(R.id.result_color_iv_image);
        mCbLeftButton = findViewById(R.id.result_color_cb_left);
        mCbRightButton = findViewById(R.id.result_color_cb_right);
        mTvColorLeft = findViewById(R.id.result_color_tv_left_color);
        mTvColorRight = findViewById(R.id.result_color_tv_right_color);
        mTvColorLeftRatio = findViewById(R.id.result_color_tv_left_ratio);
        mTvColorRightRatio = findViewById(R.id.result_color_tv_right_ratio);
        mTvCommentTotal = findViewById(R.id.result_comment_tv_comment_total);
        mEtInputComment = findViewById(R.id.rank_et_comment);
        mSwipeRefreshLayout = findViewById(R.id.result_color_swipe_layout);
        mCbLeftButton.setEnabled(false); //결과화면에서는 체크박스 비활성화.
        mCbRightButton.setEnabled(false);

        mRecyclerView = findViewById(R.id.result_comment_recycler_view);
        // layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ResultColorActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);

        // adapter
        mCommentAdapter = new ResultCommentAdapter(mCommentDataList, this);
        mRecyclerView.setAdapter(mCommentAdapter);
        mCommentAdapter.notifyDataSetChanged();

        mCbLeftButton.setTag(mColorResult.getColor1());
        mCbRightButton.setTag(mColorResult.getColor2());

        mIvColorImage.setImageResource(mColorResult.getImage());
        mTvColorLeft.setText(mColorResult.getColor1() + "");
        mTvColorRight.setText(mColorResult.getColor2() + "");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tryGetComments();
                tryGetRanks(mColorResult.getGameIdx());
            }
        });

    }

    void tryGetRanks(int gameIdx) {
        RankService rankService = new RankService(this);
        rankService.getRankList(gameIdx);
    }

    void tryGetComments() {
        CommentService commentService = new CommentService(this);
        commentService.getComments(mColorResult.getGameIdx());
    }

    void tryPostComment(String comments) {
        showProgressDialog();
        CommentService commentService = new CommentService(this);
        commentService.postComment(mColorResult.getGameIdx(), comments + "");
    }

    public void tryLikeComment(int commentIdx) {
        CommentService commentService = new CommentService(this);
        commentService.likeComment(commentIdx);
    }

    @Override
    public void validateSuccessGetComments(boolean isSuccess, int code, String message, ArrayList<CommentResult> comments) {
        if (isSuccess) {
            mCommentDataList.clear();
            mCommentDataList.addAll(comments);
            mCommentAdapter.notifyDataSetChanged();
            mTvCommentTotal.setText(comments.size() + "");
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void validateSuccessPostComment(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess) {
            mEtInputComment.getText().clear();
            tryGetComments();
            hideKeyboard(mEtInputComment);
        }
    }

    @Override
    public void validateSuccessLikeComment(boolean isSuccess, int code, String message) {
    }

    @Override
    public void validateGetRankListSuccess(boolean isSuccess, int code, String message, ArrayList<RecordResult> recordResults) {
        if (isSuccess) {
            if (mColorResult.getColor1().equals(recordResults.get(0).getChoice())) {
                if (recordResults.get(0).getRatio().equals("100")) {
                    mTvColorLeftRatio.setText(recordResults.get(0).getRatio() + "%");
                    mTvColorRightRatio.setText("0%");
                } else {
                    mTvColorLeftRatio.setText(recordResults.get(0).getRatio() + "%");
                    mTvColorRightRatio.setText(recordResults.get(1).getRatio() + "%");
                }
            } else {
                if (recordResults.get(0).getRatio().equals("100")) {
                    mTvColorLeftRatio.setText("0%");
                    mTvColorRightRatio.setText(recordResults.get(0).getRatio() + "%");
                } else {
                    mTvColorLeftRatio.setText(recordResults.get(1).getRatio() + "%");
                    mTvColorRightRatio.setText(recordResults.get(0).getRatio() + "%");
                }
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void validateSetChoiceSuccess(int position) {

    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(getString(R.string.network_error));
    }


    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.rank_btn_send:
                String comment = mEtInputComment.getText().toString();
                if (comment.length() > 0) {
                    tryPostComment(comment);
                } else {
                    showCustomToast("한 글자 이상 입력해주세요!");
                }
                break;
            case R.id.result_color:
                hideKeyboard(mEtInputComment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View v, int commentIdx, int isLike, int position) {
        ImageView ivItemLike = v.findViewById(R.id.item_result_comment_iv_like);
        TextView tvItemLikeCount = v.findViewById(R.id.item_result_comment_tv_like);
        int countLike = mCommentDataList.get(position).getCountLike();

        if (isLike == 0) {
            tvItemLikeCount.setText((countLike + 1) + "");
            ivItemLike.setImageResource(R.drawable.btn_like_active);
            mCommentDataList.get(position).setCountLike(countLike + 1);
            mCommentDataList.get(position).setAlreadyLike(1);
        } else {
            tvItemLikeCount.setText((countLike - 1) + "");
            ivItemLike.setImageResource(R.drawable.btn_like);
            mCommentDataList.get(position).setCountLike(countLike - 1);
            mCommentDataList.get(position).setAlreadyLike(0);
        }
        tryLikeComment(commentIdx);
    }
}
