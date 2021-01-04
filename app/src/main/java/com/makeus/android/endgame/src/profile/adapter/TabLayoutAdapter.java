package com.makeus.android.endgame.src.profile.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.makeus.android.endgame.src.game.result.models.CommentResult;
import com.makeus.android.endgame.src.profile.fragment.CommentFragment;
import com.makeus.android.endgame.src.profile.fragment.RecordFragment;
import com.makeus.android.endgame.src.game.result.models.RecordResult;

import java.util.ArrayList;

import static com.makeus.android.endgame.src.profile.fragment.CommentFragment.MY_COMMENT_PAGE;
import static com.makeus.android.endgame.src.profile.fragment.RecordFragment.MY_RECORD_PAGE;


public class TabLayoutAdapter extends FragmentStatePagerAdapter {
    // Count number of tabs
    private int tabCount;
    ArrayList<RecordResult> mRecords;
    ArrayList<CommentResult> mComments;

    public TabLayoutAdapter(FragmentManager fm, int tabCount, ArrayList<RecordResult> records, ArrayList<CommentResult> comments) {
        super(fm);
        this.tabCount = tabCount;
        this.mRecords = records;
        this.mComments = comments;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                RecordFragment recordFragment = new RecordFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(MY_RECORD_PAGE, mRecords); // Key, Value
                recordFragment.setArguments(bundle);
                return recordFragment;
            case 1:
                CommentFragment commentFragment = new CommentFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putParcelableArrayList(MY_COMMENT_PAGE, mComments); // Key, Value
                bundle2.putParcelableArrayList(MY_RECORD_PAGE, mRecords); // Key, Value
                commentFragment.setArguments(bundle2);
                return commentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}


