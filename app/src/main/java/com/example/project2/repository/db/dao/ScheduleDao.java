package com.example.project2.repository.db.dao;

import com.example.project2.repository.db.entity.ScheduleItemEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScheduleItems(List<ScheduleItemEntity> scheduleItemEntities);

    @Query("SELECT * FROM  scheduleItems")
    LiveData<List<ScheduleItemEntity>> getAllScheduleItems();

    @Query("SELECT * FROM scheduleItems WHERE (upper(name) LIKE :classNameOrProfessor OR upper(professor) LIKE :classNameOrProfessor) AND upper(day) LIKE :day AND upper(groups) LIKE :group")
    LiveData<List<ScheduleItemEntity>> getFilteredScheduleItems(String classNameOrProfessor,String day,String group);

    @Query("SELECT * FROM scheduleItems WHERE id LIKE :id")
    LiveData<ScheduleItemEntity> getScheduleItemById(String id);

    @Query("DELETE FROM scheduleItems")
    void deleteAll();

    //TODO add query for list item listeners
}
