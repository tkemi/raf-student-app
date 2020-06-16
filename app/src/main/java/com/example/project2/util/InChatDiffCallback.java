package com.example.project2.util;

import com.example.project2.model.Message;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class InChatDiffCallback extends DiffUtil.Callback {

    private List<Message> oldList;
    private List<Message> newList;

    public InChatDiffCallback(List<Message> oldList, List<Message> newList) {
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
