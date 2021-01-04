package com.makeus.android.endgame;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
https://black-jin0427.tistory.com/102?category=727620
 */
public class ListItemDecoration extends RecyclerView.ItemDecoration {
    private int size10;
    private int size5;

    public ListItemDecoration(Context context) {

        size10 = dpToPx(context, 10); //dp -> pixel 로 변환.
        size5 = dpToPx(context, 5);
        //코드를 통해 view 사이즈에 변화를 주거나 여백을 설정해 줄 때는 위와 같이 Pixel 단위로 변환해서 작업을 해줘야 됨.
    }

    // dp -> pixel 단위로 변경
    private int dpToPx(Context context, int dp) {

        return (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); //position : 각 아이템의 포지션을 받아옵니다
        int itemCount = state.getItemCount(); //itemCount : 전체 아이템 개수를 받아옵니다.

        //상하 설정
        if(position == 0 || position == 1) {
            // 첫번 째 줄 아이템
            outRect.top = size10;
            outRect.bottom = size10;
        } else {
            outRect.bottom = size10;
        }

        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = lp.getSpanIndex();

        if(spanIndex == 0) {
            //왼쪽 아이템
            outRect.left = size10;
            outRect.right = size5;

        } else if(spanIndex == 1) {
            //오른쪽 아이템
            outRect.left = size5;
            outRect.right = size10;
        }

    }

}