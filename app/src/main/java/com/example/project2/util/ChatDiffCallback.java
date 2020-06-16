package com.example.project2.util;

import com.example.project2.model.User;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ChatDiffCallback extends DiffUtil.Callback {

    private List<User> oldList;
    private List<User> newList;

    public ChatDiffCallback(List<User> oldList,List<User> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        boolean same = false;

        User oldUser = this.oldList.get(oldItemPosition);
        User newUser = this.newList.get(newItemPosition);

        if(oldUser.getIndexId().equals(newUser.getIndexId())){
            same = true;
        }

        return same;

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        boolean same = true;

        User oldUser = this.oldList.get(oldItemPosition);
        User newUser = this.newList.get(newItemPosition);

        if(oldUser.getName().equals(newUser.getName())){
            same = true;
        }

        return same;

    }
}
