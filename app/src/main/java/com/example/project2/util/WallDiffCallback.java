package com.example.project2.util;

import com.example.project2.model.WallMessage;


import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class WallDiffCallback extends DiffUtil.Callback {

    private List<WallMessage> oldList;
    private List<WallMessage> newList;

    public WallDiffCallback(List<WallMessage> oldList, List<WallMessage> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean same = false;

        if(oldList.get(oldItemPosition).getDate().equals(newList.get(newItemPosition).getDate())){
            same = true;
        }
        return same;

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }
}
