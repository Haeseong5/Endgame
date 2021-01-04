package com.makeus.android.endgame.src.profile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.games.CustomDialog;
import com.makeus.android.endgame.src.game.rank.RankActivity;
import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.game.result.models.RecordResult;
import com.makeus.android.endgame.src.game.what_color_game.ResultColorActivity;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;
import com.makeus.android.endgame.src.profile.ProfileActivity;
import com.makeus.android.endgame.src.profile.ProfileService;
import com.makeus.android.endgame.src.profile.adapter.CommentAdapter;
import com.makeus.android.endgame.src.profile.interfaces.ProfileActivityView;
import com.makeus.android.endgame.src.profile.models.ProfileResponse;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.profile.fragment.RecordFragment.MY_RECORD_PAGE;

public class CommentFragment extends BaseFragment implements ProfileActivityView, CommentAdapter.OnItemLongClickListener {
    public static final String MY_COMMENT_PAGE = "MY_COMMENT_PAGE";
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private ArrayList<CommentResult> mCommentDataList;
    private ArrayList<RecordResult> mRecordDataList;
    private CustomDialog mCustomDialog;
    private int mCommentIdx, mPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCommentDataList = getArguments().getParcelableArrayList(MY_COMMENT_PAGE);
            mRecordDataList = getArguments().getParcelableArrayList(MY_RECORD_PAGE);
        }
        initColorData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_comment, container, false);
        mRecyclerView = rootView.findViewById(R.id.comment_recycler_view);

        // layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        // adapter
        mAdapter = new CommentAdapter(mCommentDataList, this);
        mRecyclerView.setAdapter(mAdapter);
        commentItemOnClick();
        mAdapter.notifyDataSetChanged();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        return rootView;
    }


    private ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // 삭제되는 아이템의 포지션을 가져온다
//            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            mCommentIdx = mCommentDataList.get(viewHolder.getLayoutPosition()).getCommentIdx();
            mPosition = viewHolder.getLayoutPosition();
            mCustomDialog = new CustomDialog(getActivity(), positiveListener, negativeListener, getString(R.string.dialog_custom), getString(R.string.dialog_custom_contents));
            mCustomDialog.show();
        }
    };

    private View.OnClickListener positiveListener = new View.OnClickListener() {
        public void onClick(View v) {
            tryDeleteComment(mCommentIdx);
            mCustomDialog.dismiss();
        }
    };

    private View.OnClickListener negativeListener = new View.OnClickListener() {
        public void onClick(View v) {
            mCustomDialog.dismiss();
        }
    };

    private void tryDeleteComment(int commentIdx) {
        ProfileService profileService = new ProfileService(this);
        profileService.deleteComment(commentIdx);
    }


    private void commentItemOnClick() {
        mAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int gameIdx = mCommentDataList.get(position).getGameIdx();
                if (gameIdx <= 5) {
                    updateUI(gameIdx, mCommentDataList.get(position).getGameName());
                } else { // gameIdx 6부터 color 게임

                    updateUI_ColorResult(mColorDataList.get(gameIdx));
                }
            }
        });
    }

    private void updateUI(int gameIdx, String title) {
        Intent rankIntent = new Intent(getActivity(), RankActivity.class);
        rankIntent.putExtra("gameIdx", gameIdx);
        rankIntent.putExtra("title", title);
        startActivity(rankIntent);
    }

    private void updateUI_ColorResult(ColorResult colorResult) {
        Intent resultIntent = new Intent(getActivity(), ResultColorActivity.class);
//        resultIntent.putExtra("recordResult", recordResult);
        resultIntent.putExtra("colorResult", colorResult);
        startActivity(resultIntent);
    }


    @Override
    public void validateSuccess(boolean isSuccess, int code, String text, ProfileResponse.Profile profile) {

    }

    @Override
    public void validateSuccessDeleteComment(boolean isSuccess, int code, String text, int commentIdx) {
        if (isSuccess) {
            mCommentDataList.remove(mPosition);
            ((ProfileActivity)getActivity()).mTabLayout.getTabAt(1).setText(mCommentDataList.size() + "\n댓글");
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void validateFailure(String message) {
        hideProgressDialog();
        showCustomToast(message == null || message.isEmpty() ? getString(R.string.network_error) : message);
    }

    @Override
    public void onItemLongClick(int commentIdx) {
        mCommentIdx = commentIdx;
        mCustomDialog = new CustomDialog(getActivity(), positiveListener, negativeListener, getString(R.string.dialog_custom), getString(R.string.dialog_custom_contents));
        mCustomDialog.show();
    }
}
