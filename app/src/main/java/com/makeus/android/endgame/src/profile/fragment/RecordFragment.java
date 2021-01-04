package com.makeus.android.endgame.src.profile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.android.endgame.ListItemDecoration;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;
import com.makeus.android.endgame.src.game.rank.RankActivity;
import com.makeus.android.endgame.src.game.what_color_game.ResultColorActivity;
import com.makeus.android.endgame.src.profile.adapter.RecordAdapter;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.ApplicationClass.FISH_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.KMBTI_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.RAMEN_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.VALUES_GAME_INDEX;
import static com.makeus.android.endgame.src.ApplicationClass.WASH_GAME_INDEX;

public class RecordFragment extends BaseFragment {
    public static final String MY_RECORD_PAGE = "MY_RECORD_PAGE";
    private RecyclerView mRecyclerView;
    private ArrayList<RecordResult> mRecordDataList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mRecordDataList = getArguments().getParcelableArrayList(MY_RECORD_PAGE);
            if (mRecordDataList != null){
                //data preprocessing 글자수 줄이기
                for(int i=0; i<mRecordDataList.size(); i++){
                    switch (mRecordDataList.get(i).getGameIdx()){
                        case FISH_GAME_INDEX:
                            mRecordDataList.get(i).setGameName("붕어빵");
                            break;
                        case RAMEN_GAME_INDEX:
                            mRecordDataList.get(i).setGameName("라면");
                            break;
                        case KMBTI_GAME_INDEX:
                            break;
                        case WASH_GAME_INDEX:
                            break;
                        case VALUES_GAME_INDEX:
                            break;
                        default: //COLOR
                            if (mRecordDataList.get(i).getGameName().contains("무슨색이게?")){
                                mRecordDataList.get(i).setGameName("무슨 색이게?");
                            }
                            break;
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_record, container, false);
        mRecyclerView = rootView.findViewById(R.id.record_recycler_view);
        initColorData();
        // layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.addItemDecoration(new ListItemDecoration(getActivity())); //set margin in recycler view item
        mRecyclerView.setLayoutManager(layoutManager);

        // adapter
        RecordAdapter adapter = new RecordAdapter(getActivity(), mRecordDataList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int gameIdx = mRecordDataList.get(position).getGameIdx();
                if (gameIdx <= 5){
                    Intent intent = new Intent(getActivity(), RankActivity.class);
                    intent.putExtra("gameIdx", gameIdx);
                    intent.putExtra("choice", mRecordDataList.get(position).getChoice());
                    startActivity(intent);
                }else{ // gameIdx 6부터 color 게임
                    RecordResult recordResult = new RecordResult();
                    recordResult.setGameIdx(mRecordDataList.get(position).getGameIdx());
                    recordResult.setGameName(mRecordDataList.get(position).getGameName());
                    recordResult.setChoice(mRecordDataList.get(position).getChoice());
                    recordResult.setRatio(mRecordDataList.get(position).getRatio());

                    Intent intent = new Intent(getActivity(), ResultColorActivity.class);
                    intent.putExtra("recordResult", recordResult);
                    intent.putExtra("colorResult", mColorDataList.get(gameIdx));
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }
}
