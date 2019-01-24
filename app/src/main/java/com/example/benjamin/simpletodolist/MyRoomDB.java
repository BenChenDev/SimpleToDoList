package com.example.benjamin.simpletodolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task_Table_Entity.class}, version = 1)
public abstract class MyRoomDB extends RoomDatabase{
    public abstract TasksDao tasksDao();
}
