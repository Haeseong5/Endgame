package com.makeus.android.endgame.src.game.what_color_game.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.what_color_game.SelectColorActivity;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder> {
    private final String TAG = ColorAdapter.class.getName();
    private Context mContext;
    private List<ColorResult> colorDataList;
    private ColorAdapter.OnItemClickListener mListener = null ;

    public ColorAdapter(Context mContext, List<ColorResult> colorDataList){
        this.colorDataList = colorDataList;
        this.mContext = mContext;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public void setOnItemClickListener(ColorAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    @NonNull
    @Override
    public ColorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, null);

        ColorAdapter.MyViewHolder vh = new ColorAdapter.MyViewHolder(itemLayoutView);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.MyViewHolder holder, final int position) {
        holder.ivColorImage.setImageResource(colorDataList.get(position).getThumbnailImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SelectColorActivity.class);
                intent.putExtra("colorResult", colorDataList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(colorDataList != null)
            return colorDataList.size();
        else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivColorImage;
        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ivColorImage = itemLayoutView.findViewById(R.id.item_color_iv_image);

            // 아이템 클릭 이벤트 처리.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });
        }
    }
}
