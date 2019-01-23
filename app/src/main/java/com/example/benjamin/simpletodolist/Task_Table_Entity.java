package com.example.benjamin.simpletodolist;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Tasks")
public class Task_Table_Entity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "due_day")
    private String due_day;

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDue_day() {
        return due_day;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDue_day(String due_day) {
        this.due_day = due_day;
    }
}
