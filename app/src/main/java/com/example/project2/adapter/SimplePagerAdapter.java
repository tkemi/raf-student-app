package com.example.project2.adapter;

import android.content.Context;

import com.example.project2.fragment.ChatFragment;
import com.example.project2.fragment.ScheduleFragment;
import com.example.project2.fragment.WallFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SimplePagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT=3;
    public static final int FRAGMENT_SCHEDULE=0;
    public static final int FRAGMENT_CHAT=1;
    public static final int FRAGMENT_WALL=2;
    private List<String> mTitles;

    public SimplePagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        initTitles();
    }

    private void initTitles(){
        mTitles = new ArrayList<>();
        mTitles.add("ScheduleItem");
        mTitles.add("Chat");
        mTitles.add("Wall");
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case FRAGMENT_SCHEDULE:
                return ScheduleFragment.newInstance();
            case FRAGMENT_CHAT:
                return ChatFragment.newInstance();
            case FRAGMENT_WALL:
                return WallFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
