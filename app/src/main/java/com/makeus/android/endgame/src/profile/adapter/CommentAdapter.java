package com.makeus.android.endgame.src.profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.result.models.CommentResult;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private ArrayList<CommentResult> commentDataList;

    private OnItemLongClickListener mLongClickListener = null;
    private OnItemClickListener mClickListener = null;

    public CommentAdapter(){}
    public CommentAdapter(ArrayList<CommentResult> commentDataList, OnItemLongClickListener listener) {
        this.commentDataList = commentDataList;
        this.mLongClickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int commentIdx) ;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int commentIdx) ;
    }
    public void setOnItemClickListener(CommentAdapter.OnItemClickListener listener) {
        this.mClickListener = listener ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, null);

        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.itemTheme.setText(commentDataList.get(position).getGameName() + "");
        holder.itemContent.setText(commentDataList.get(position).getContent() + "");
        holder.itemDate.setText(commentDataList.get(position).getCreatedAt() + "");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLongClickListener.onItemLongClick(commentDataList.get(position).getCommentIdx());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (commentDataList != null)
            return commentDataList.size();
        else
            return 0;
    }

    // inner static class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTheme, itemContent, itemDate;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemTheme = itemLayoutView.findViewById(R.id.item_comment_tv_theme);
            itemContent = itemLayoutView.findViewById(R.id.item_comment_tv_content);
            itemDate = itemLayoutView.findViewById(R.id.item_comment_tv_date);
            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mClickListener != null) {
                            mClickListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }
    }
}
