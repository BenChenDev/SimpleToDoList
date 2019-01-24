package com.example.benjamin.simpletodolist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TasksDao {

    @Insert
    void insert(Task_Table_Entity task);

    @Query("select * from Tasks order by id desc")
    LiveData<List<Task_Table_Entity>> getTasks();

    @Query("select count(id) from Tasks")
    int getCount();

    @Delete
    int delete(Task_Table_Entity task);

    @Update
    void update(Task_Table_Entity task);

    @Query("delete from Tasks")
    void deleteAllTasks();
}
