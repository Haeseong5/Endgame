package com.makeus.android.endgame.src.game.result.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.result.models.CommentResult;

import java.util.ArrayList;

public class ResultCommentAdapter extends RecyclerView.Adapter<ResultCommentAdapter.MyViewHolder> {

    private ArrayList<CommentResult> commentDataList;

    private OnItemClickListener mListener = null ;

    public ResultCommentAdapter(ArrayList<CommentResult> commentDataList, OnItemClickListener listener){
        this.commentDataList = commentDataList;
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int commentIdx, int isLike, int position) ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_comment, null);

        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.itemLike.setText(commentDataList.get(position).getCountLike()+"");
        holder.itemContent.setText(commentDataList.get(position).getContent()+"");
        holder.itemNickName.setText(commentDataList.get(position).getNickname()+"");

        if(commentDataList.get(position).getAlreadyLike()==0){
            holder.itemIvLike.setImageResource(R.drawable.btn_like);
        }else{
            holder.itemIvLike.setImageResource(R.drawable.btn_like_active);
        }

        holder.itemIvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mListener.onItemClick(holder.itemView, commentDataList.get(position).getCommentIdx(), commentDataList.get(position).getAlreadyLike(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(commentDataList != null)
            return commentDataList.size();
        else
            return 0;
    }

    // inner static class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemLike, itemContent, itemNickName;
        ImageView itemIvLike;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            itemContent = itemLayoutView.findViewById(R.id.item_result_comment_tv_content);
            itemNickName = itemLayoutView.findViewById(R.id.item_result_comment_tv_nickname);
            itemLike = itemLayoutView.findViewById(R.id.item_result_comment_tv_like);
            itemIvLike = itemLayoutView.findViewById(R.id.item_result_comment_iv_like);
            // 아이템 클릭 이벤트 처리.
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition() ;
//                    if (pos != RecyclerView.NO_POSITION) {
//                        // 리스너 객체의 메서드 호출.
//                        if (mListener != null) {
//                            mListener.onItemClick(v, pos) ;
//                        }
//                    }
//                }
//            });
        }
    }
}
