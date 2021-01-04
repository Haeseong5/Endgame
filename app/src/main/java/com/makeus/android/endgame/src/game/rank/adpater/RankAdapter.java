package com.makeus.android.endgame.src.game.rank.adpater;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.rank.interfaces.RankActivityView;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.MyViewHolder> {
    private ArrayList<RecordResult> mRankResults;
    private int mBackGround;
    private String mChoice;
    private int mColor;
    private RankActivityView mRankActivityView;

    public RankAdapter(ArrayList<RecordResult> rankResults, int backGround, final RankActivityView rankActivityView) {
        this.mRankResults = rankResults;
        this.mBackGround = backGround;
        this.mRankActivityView = rankActivityView;
    }

    @NonNull
    @Override
    public RankAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank, null);
        // recyclerView match_parent 안되는 문제 해결 코드
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemLayoutView.setLayoutParams(lp);

        RankAdapter.MyViewHolder vh = new RankAdapter.MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RankAdapter.MyViewHolder holder, final int position) {
        holder.itemCl.setBackgroundResource(mBackGround);
        holder.itemTvNum.setText((position + 1) + "");
        holder.itemTvChoice.setText(mRankResults.get(position).getChoice() + "");
        holder.itemTvRatio.setText(mRankResults.get(position).getRatio() + "");
        if (mChoice != null) {
            if (mChoice.equals(mRankResults.get(position).getChoice())) {
                holder.itemTvChoice.setTextColor(mColor);
                mRankActivityView.validateSetChoiceSuccess(position);
            } else {
                holder.itemTvChoice.setTextColor(Color.WHITE);
            }
        }
    }

    public void setChoice(String choice, int color) {
        this.mColor = color;
        this.mChoice = choice;
    }

    @Override
    public int getItemCount() {
        if (mRankResults != null)
            return mRankResults.size();
        else
            return 0;
    }

    // inner static class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTvNum, itemTvChoice, itemTvRatio;
        LinearLayout itemCl;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            itemCl = itemLayoutView.findViewById(R.id.item_rank_cl);
            itemTvNum = itemLayoutView.findViewById(R.id.item_rank_tv_number);
            itemTvChoice = itemLayoutView.findViewById(R.id.item_rank_tv_result_choice);
            itemTvRatio = itemLayoutView.findViewById(R.id.item_rank_tv_ratio);

        }
    }
}
