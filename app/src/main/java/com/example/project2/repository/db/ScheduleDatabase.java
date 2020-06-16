package com.example.project2.repository.db;

import android.content.Context;

import com.example.project2.repository.db.dao.ScheduleDao;
import com.example.project2.repository.db.entity.ScheduleItemEntity;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database( entities = {ScheduleItemEntity.class},version = 1,exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {

    private static volatile  ScheduleDatabase DATABASE;

    private static final String DATABASE_NAME = "schedule_database";

    public abstract ScheduleDao getScheduleDao();

    public static ScheduleDatabase getdb(Context context) {
        if (DATABASE == null) {
            synchronized (ScheduleDatabase.class) {
                if (DATABASE == null){
                    DATABASE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ScheduleDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return  DATABASE;
    }
}
