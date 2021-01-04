package com.makeus.android.endgame.src.game.rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.rank.adpater.RankAdapter;
import com.makeus.android.endgame.src.game.rank.interfaces.RankActivityView;
import com.makeus.android.endgame.src.game.result.CommentService;
import com.makeus.android.endgame.src.game.result.adapter.ResultCommentAdapter;
import com.makeus.android.endgame.src.game.result.interfaces.CommentActivityView;
import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.main.HomeActivity;

import java.util.ArrayList;

public class RankActivity extends BaseActivity implements RankActivityView, CommentActivityView, ResultCommentAdapter.OnItemClickListener {
    private TextView mTvTitle, mTvCommentNumber;
    private EditText mEtInputComment;
    private RecyclerView mRvComment, mRvRanking; //Comment
    private ResultCommentAdapter mCommentAdapter;
    private RankAdapter mRankAdapter;
    private ArrayList<RecordResult> mRanks;
    private ArrayList<CommentResult> mCommentDataList;
    private int mGameIdx, mTitleBackground, mSetChoicePosition;
    private RecyclerView.LayoutManager mLayoutManager;
    private NestedScrollView mNsv;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsApplyComment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        mRanks = new ArrayList<>();
        mCommentDataList = new ArrayList<>();

        Intent intent = getIntent();
        mGameIdx = intent.getIntExtra("gameIdx", 0);
        String choice = intent.getStringExtra("choice"); //게임으로 넘어오면 choice
        String title = intent.getStringExtra("title");  //마이프로필, 테마에서 넘어오면 title
        int gameColor;

        switch (mGameIdx) {
            case 1:
                gameColor = R.color.colorYellowKmbti;
                mTitleBackground = R.drawable.bg_rank_title_under_line_kmbti;
                break;
            case 2:
                gameColor = R.color.colorGreenFish;
                mTitleBackground = R.drawable.bg_rank_title_under_line_fish;
                break;
            case 3:
                gameColor = R.color.colorPurpleWash;
                mTitleBackground = R.drawable.bg_rank_title_under_line_wash;
                break;
            case 4:
                gameColor = R.color.colorRedRamen;
                mTitleBackground = R.drawable.bg_rank_title_under_line_ramen;
                break;
            case 5:
                gameColor = R.color.colorBlueValues;
                mTitleBackground = R.drawable.bg_rank_title_under_line_values;
                break;
            default:
                gameColor = R.color.colorLightPurpleColor;
                mTitleBackground = R.drawable.bg_rank_title_under_line_color;
                break;
        }

        initView();

        setRankToolbar(mBaseToolbar, true);

        if (choice != null) {
            mTvTitle.setText(choice);
            mRankAdapter.setChoice(choice, getResources().getColor(gameColor));
            mRankAdapter.notifyDataSetChanged();
        } else {
            mTvTitle.setText(title);
        }
        mTvTitle.setBackgroundResource(mTitleBackground);

        mTvCommentNumber.setTextColor(getResources().getColor(gameColor));

        tryGetComments();
        tryGetRanks();
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.rank_toolbar);
        mTvTitle = findViewById(R.id.rank_tv_select_result);
        mTvCommentNumber = findViewById(R.id.result_comment_tv_comment_total);
        mRvComment = findViewById(R.id.result_comment_recycler_view);
        mRvRanking = findViewById(R.id.rank_recycler_view);
        mEtInputComment = findViewById(R.id.rank_et_comment);
        mSwipeRefreshLayout = findViewById(R.id.rank_swipe_layout);
        mNsv = findViewById(R.id.rank_nested_scroll_view);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mRvComment.setLayoutManager(mLayoutManager);
        mCommentAdapter = new ResultCommentAdapter(mCommentDataList, this);
        mRvComment.setAdapter(mCommentAdapter);
        mCommentAdapter.notifyDataSetChanged();

        mRvRanking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRankAdapter = new RankAdapter(mRanks, mTitleBackground, this);
        mRvRanking.setAdapter(mRankAdapter);
        mRankAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tryGetComments();
                tryGetRanks();
            }
        });

    }

    void tryGetRanks() {
        showProgressDialog();
        RankService rankService = new RankService(this);
        rankService.getRankList(mGameIdx);
    }

    void tryGetComments() {
        CommentService commentService = new CommentService(this);
        commentService.getComments(mGameIdx);
    }

    void tryPostComment(String comments) {
        showProgressDialog();
        CommentService commentService = new CommentService(this);
        commentService.postComment(mGameIdx, comments + "");
    }

    public void tryLikeComment(int commentIdx) {
        CommentService commentService = new CommentService(this);
        commentService.likeComment(commentIdx);
    }

    public void customOnClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_rank_list:
                Intent themeIntent = new Intent(RankActivity.this, RankChoiceActivity.class);
                themeIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(themeIntent);
                overridePendingTransition(R.anim.slide_down, R.anim.fade_out);
                break;
            case R.id.toolbar_rank_home:
                Intent homeIntent = new Intent(RankActivity.this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                break;
            case R.id.rank_btn_send:
                String comment = mEtInputComment.getText().toString();
                if (comment.length() > 0) {
                    tryPostComment(comment);
                } else {
                    showCustomToast("한 글자 이상 입력해주세요!");
                }
            default:
                break;
        }
    }

    @Override
    public void validateGetRankListSuccess(boolean isSuccess, int code, String message, ArrayList<RecordResult> recordResults) {
        hideProgressDialog();
        if (isSuccess) {
            mRanks.clear();
            mRanks.addAll(recordResults);
            mRankAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void validateSetChoiceSuccess(int position) {
        mSetChoicePosition = position;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRvRanking.scrollToPosition(mSetChoicePosition);
            }
        }, 100);
    }

    @Override
    public void validateSuccessGetComments(boolean isSuccess, int code, String message, ArrayList<CommentResult> comments) {
        if (isSuccess) {
            mCommentDataList.clear();
            mCommentDataList.addAll(comments);
            mCommentAdapter.notifyDataSetChanged();
            mTvCommentNumber.setText(comments.size() + "");
            if (mIsApplyComment){
                mNsv.post(new Runnable() {
                    @Override
                    public void run() {
                        mNsv.fullScroll(View.FOCUS_DOWN);
                    }
                });
                mIsApplyComment  = false;
            }

        }
    }

    @Override
    public void validateSuccessPostComment(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess) {
            mEtInputComment.getText().clear();
            mIsApplyComment = true;
            tryGetComments();
            hideKeyboard(mEtInputComment);
        }
    }

    @Override
    public void validateSuccessLikeComment(boolean isSuccess, int code, String message) {
        if (isSuccess) {
//            tryGetComments();

        }
    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(getString(R.string.network_error));
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
