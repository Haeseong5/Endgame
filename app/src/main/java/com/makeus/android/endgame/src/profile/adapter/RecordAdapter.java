package com.makeus.android.endgame.src.profile.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.RAMEN_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;


public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<RecordResult> recordDataList;
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;
    public RecordAdapter(Context context, ArrayList<RecordResult> recordDataList){
        this.context = context;
        this.recordDataList = recordDataList;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_layout
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record, null);

        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvGameName.setText(recordDataList.get(position).getGameName());
        holder.tvMyResult.setText(recordDataList.get(position).getChoice());
        holder.tvRatio.setText(recordDataList.get(position).getRatio()+"%");
        Drawable background;
        int textColor;

        switch (recordDataList.get(position).getGameIdx()){
            case KMBTI_GAME_INDEX:
                background = context.getDrawable(R.drawable.img_mypage_kmbti_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorYellowKmbti);
                break;
            case FISH_GAME_INDEX:
                background = context.getDrawable(R.drawable.img_mypage_fishbread_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorGreenFish);
                break;
            case RAMEN_GAME_INDEX:
                background = context.getDrawable(R.drawable.img_mypage_ramen_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorRedRamen);
                break;
            case WASH_GAME_INDEX:
                background = context.getDrawable(R.drawable.img_mypage_wash_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorPurpleWash);
                break;
            case VALUES_GAME_INDEX:
                background = context.getDrawable(R.drawable.img_mypage_values_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorBlueValues);
                break;
            case 12: //무슨색이게?-나이키세트 ver1
                background = context.getDrawable(R.drawable.img_mypage_color_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorLightPurpleColor);
                holder.ivVersion.setImageDrawable(context.getDrawable(R.drawable.ic_ver1));
                holder.ivVersion.setVisibility(View.VISIBLE);
                break;
            case 13: //무슨색이게?-아디다스 ver2
                background = context.getDrawable(R.drawable.img_mypage_color_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorLightPurpleColor);
                holder.ivVersion.setImageDrawable(context.getDrawable(R.drawable.ic_ver2));
                holder.ivVersion.setVisibility(View.VISIBLE);
                break;
            default: //default color: 무슨색이게? 색깔
                background = context.getDrawable(R.drawable.img_mypage_color_result_bg);
                textColor = ContextCompat.getColor(context, R.color.colorLightPurpleColor);
                break;
        }
        holder.ivBackground.setImageDrawable(background);
        holder.tvGameName.setTextColor(textColor);
        holder.tvMyResult.setTextColor(textColor);

    }

    @Override
    public int getItemCount() {
        if(recordDataList != null)
            return recordDataList.size();
        else
            return 0;
    }

    // inner static class
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvGameName;
        TextView tvMyResult;
        TextView tvRatio;
        ImageView ivVersion, ivBackground;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvGameName = itemLayoutView.findViewById(R.id.item_record_tv_game_name);
            tvMyResult = itemLayoutView.findViewById(R.id.item_record_tv_result);
            ivVersion = itemLayoutView.findViewById(R.id.item_record_iv_color_version);
            ivBackground = itemLayoutView.findViewById(R.id.item_record_iv_background);
            tvRatio = itemLayoutView.findViewById(R.id.item_record_ratio);
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
