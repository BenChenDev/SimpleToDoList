package com.example.benjamin.simpletodolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TasksDao {

    @Insert
    long Add_a_task(Task_Table_Entity task);

    @Query("select * from Tasks")
    List<Task_Table_Entity> getTasks();

    @Query("select count(id) from Tasks")
    int getCount();

    @Delete
    int delete_task(Task_Table_Entity task);
}
