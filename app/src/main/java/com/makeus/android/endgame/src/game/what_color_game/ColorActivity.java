package com.makeus.android.endgame.src.game.what_color_game;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.makeus.android.endgame.ListItemDecoration;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.BaseActivity;
import com.makeus.android.endgame.src.game.what_color_game.adapter.ColorAdapter;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

import java.util.ArrayList;

/**
 * 컬러 퀴즈 선택 창
 */
public class ColorActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<ColorResult> colorDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_color);

        mBaseToolbar = findViewById(R.id.game_toolbar_result);
        setGameResultToolbar(mBaseToolbar, getString(R.string.what_color_game_title), R.color.colorLightPurpleColor);

        mRecyclerView = findViewById(R.id.color_recycler_view);
        colorDataList = new ArrayList<>();

        // layout manager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.addItemDecoration(new ListItemDecoration(this)); //set margin in recycler view item
        mRecyclerView.setLayoutManager(layoutManager);

        // adapter
        ColorAdapter adapter = new ColorAdapter(this, colorDataList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ColorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(ColorActivity.this, SelectColorActivity.class);
                startActivity(intent);
            }
        });
        initData();
        adapter.notifyDataSetChanged();
    }

    void initData(){
        colorDataList.add(new ColorResult(9, R.drawable.btn_thumbnail_bl_vs_wh, "검정색", "흰색", R.drawable.img_bl_vs_wh));
        colorDataList.add(new ColorResult(10, R.drawable.btn_thumbnail_bw_vs_gg, "파흰", "초금", R.drawable.img_bw_vs_gg));
        colorDataList.add(new ColorResult(11, R.drawable.btn_thumbnail_gr_vs_ye, "회색", "노란색", R.drawable.img_gr_vs_ye));
        colorDataList.add(new ColorResult(12, R.drawable.btn_thumbnail_mg_vs_pw_1, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_1));
        colorDataList.add(new ColorResult(13, R.drawable.btn_thumbnail_mg_vs_pw_2, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_2));
        colorDataList.add(new ColorResult(14, R.drawable.btn_thumbnail_wg_vs_bb, "흰금", "파검", R.drawable.img_wg_vs_bb));
    }
}
