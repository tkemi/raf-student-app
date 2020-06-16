package com.example.project2.util;

import com.example.project2.model.ScheduleItem;
import com.example.project2.repository.db.entity.ScheduleItemEntity;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class ScheduleDiffCallback extends DiffUtil.Callback {

    private List<ScheduleItemEntity> oldList;
    private List<ScheduleItemEntity> newList;

    public ScheduleDiffCallback(List<ScheduleItemEntity> oldList, List<ScheduleItemEntity> newList) {
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
        return areContentsTheSame(oldItemPosition,newItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        ScheduleItemEntity oldItem = oldList.get(oldItemPosition);
        ScheduleItemEntity newItem = newList.get(newItemPosition);
        return oldItem.getClassName().equals(newItem.getClassName()) &&
                oldItem.getClassType().equals(newItem.getClassType()) &&
                oldItem.getClassroom().equals(newItem.getClassroom()) &&
                oldItem.getProfessor().equals(newItem.getProfessor()) &&
                oldItem.getDay().equals(newItem.getDay()) &&
                oldItem.getTime().equals(newItem.getTime()) &&
                oldItem.getGroups().equals(newItem.getGroups());
    }
}
