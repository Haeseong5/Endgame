package com.makeus.android.endgame.src.profile.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.what_color_game.models.ColorResult;

@SuppressLint("Registered")
public class BaseFragment extends Fragment {
    public ProgressDialog mProgressDialog;
    public SparseArray<ColorResult> mColorDataList;


    public void initColorData() {
        mColorDataList = new SparseArray<>();
        mColorDataList.put(9, new ColorResult(9, R.drawable.btn_thumbnail_bl_vs_wh, "검정색", "흰색", R.drawable.img_bl_vs_wh));
        mColorDataList.put(10, new ColorResult(10, R.drawable.btn_thumbnail_bw_vs_gg, "파흰", "초금", R.drawable.img_bw_vs_gg));
        mColorDataList.put(11, new ColorResult(11, R.drawable.btn_thumbnail_gr_vs_ye, "회색", "노란색", R.drawable.img_gr_vs_ye));
        mColorDataList.put(12, new ColorResult(12, R.drawable.btn_thumbnail_mg_vs_pw_1, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_1));
        mColorDataList.put(13, new ColorResult(13, R.drawable.btn_thumbnail_mg_vs_pw_2, "민트회색", "핑크흰색", R.drawable.img_mg_vs_pw_2));
        mColorDataList.put(14, new ColorResult(14, R.drawable.btn_thumbnail_wg_vs_bb, "흰금", "파검", R.drawable.img_wg_vs_bb));
    }
    public void showCustomToast(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void printToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void printLog(String TAG, String log) {
        Log.d(TAG, log);
    }

}
